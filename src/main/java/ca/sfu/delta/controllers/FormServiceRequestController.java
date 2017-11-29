package ca.sfu.delta.controllers;
import ca.sfu.delta.Utilities.GlobalConstants;
import ca.sfu.delta.models.*;
import ca.sfu.delta.repository.FormRepository;
import ca.sfu.delta.repository.RequestIDRepository;
import ca.sfu.delta.repository.URLTokenRepository;
import ca.sfu.delta.repository.DistributionEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


@Controller
public class FormServiceRequestController extends WebMvcConfigurerAdapter {
    @Autowired
    FormRepository formRepository;
    @Autowired
    RequestIDRepository requestIDRepository;
    @Autowired
    URLTokenRepository urlTokenRepository;
    @Autowired
    SendEmail sendEmail;
    @Autowired
    DistributionEmailRepository distributionEmailRepository;

    @Value("${server.baseUrl}")
    String baseUrl;

    private static final String formRequestURL = "/updateform?token=";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/updateform").setViewName("userupdateform");
    }

    @RequestMapping(value = "/api/form/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Map<String, Object> getForm(@PathVariable("id") Long id) {
        FormData form = formRepository.findOne(id);

        return form.jsonify();
    }

    @RequestMapping(value = "/api/form/getByRequestID/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody FormData getForm(@PathVariable("id") String id) {
        for (FormData f : formRepository.findAll()) {
        	if (f.getRequestID().equals(id)) {
        		return f;
			}
		}

        return null;
    }

    @RequestMapping(value = "/api/form/get/user/{token}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity getFormFromToken(@PathVariable("token") String token) {
    	if (urlTokenRepository.existsByToken(token)) {
		    URLToken urlToken = urlTokenRepository.getByToken(token);
		    FormData form = formRepository.findOne(urlToken.getFormDataID());
		    return ResponseEntity.ok(form.jsonify());
	    }
	    else {
    		// could not find the form corresponding to the token
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
    }

    @RequestMapping(value = "/api/form/search", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Map<String, Object>> search() {
        List<Map<String, Object>> forms = new ArrayList<Map<String, Object>>();

        for (FormData form : formRepository.findAll()) {
            forms.add(form.jsonify());
        }

        return forms;
    }

	@RequestMapping(value = "/api/form/saveSecurity", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity saveSecurity(@RequestBody @Valid FormData form,
                                                     BindingResult result,
                                                     @RequestParam(required = false) String additionalMessage)
            throws MessagingException {

        if(result.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: Form was invalid");
        }

		String userName = form.getRequesterName();
		String userEmailAddress = form.getEmailAddress();
		String authEmailAddress = form.getAuthorizerEmailAddress();
		String trackingID = form.getRequestID();
        List<String> distListIDs = form.getDistributionList();
        String token = createURLToken(form.getId());
        String requestURL = "https://" + baseUrl + formRequestURL + token;
        String eventName = form.getEventName();

        //Get the real emails from the repository, instead of the IDs like we have now.
        List<String> distList = new ArrayList<String>();
        if(distListIDs != null) {
            for(String id : distListIDs) {
                if(id != null) {
                 distList.add(distributionEmailRepository.findOne(Long.parseLong(id)).getEmail());
                }
            }
        }

		try {
			//send email to User
			if (userEmailAddress != null && form.getRequestStatus().equals(GlobalConstants.REJECTED)) {
				sendEmail.sendEventRequestRejection(userEmailAddress, userName,
						trackingID, additionalMessage);
			}
			else if (userEmailAddress != null && form.getRequestStatus().equals(GlobalConstants.ACCEPTED)) {
				sendEmail.sendEventRequestApproved(userEmailAddress, userName, trackingID);
			}

			//send email to Authorizer
			if (authEmailAddress != null && form.getRequestStatus().equals(GlobalConstants.REJECTED)) {
				sendEmail.sendEventRequestRejection(authEmailAddress, null,
						trackingID, additionalMessage);
			}
			else if (authEmailAddress != null && form.getRequestStatus().equals(GlobalConstants.ACCEPTED)) {
				sendEmail.sendEventRequestApproved(authEmailAddress, null, trackingID);
			}

            //if approved, send emails to distribution list
            if ( distList != null && form.getRequestStatus().equals(GlobalConstants.ACCEPTED)) {
                for(String email : distList) {
                    sendEmail.sendDistributionEmail(email, eventName, requestURL);
                }
            }

		} catch (Exception e) {
			System.out.print("Error sending the emails: " + e.getMessage());
		}

		formRepository.save(form);

		return ResponseEntity.ok("");
	}

    @RequestMapping(value = "/api/csv/form/{id}.csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public String getCSV(@PathVariable("id") String id) {
        for (FormData form : formRepository.findAll()) {

            if(form.getRequestID().equals(id))
            {
                String csvString = new String();
                csvString = form.getAsCSV(true);
                return csvString;
            }
        }
        return "Error: this form does not exist";
    }

    @RequestMapping(value = "/api/csv/guards/{id}-guards.csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public String getGuardCSV(@PathVariable("id") String id) {
        FormData correctForm = null;
        for (FormData form : formRepository.findAll()) {

            if(form.getRequestID().equals(id))
            {
                correctForm = form;
                break;
            }
        }

        if(correctForm == null) {
            return null;
        }

        List<Guard> correctFormGuards = correctForm.getGuards();

        Boolean first = true;
        StringWriter csvWriter = new StringWriter();
        for (Guard g : correctFormGuards) {
            if(first) {
                csvWriter.append(g.getAsCSV(true));
                first = false;
            } else {
                csvWriter.append(g.getAsCSV(false));
            }
        }
        return csvWriter.toString();
    }

    @RequestMapping(value = "/api/csv/form/all.csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public String getAllCSV() {
        boolean first = true;
        String thisForm;
        StringWriter csvWriter = new StringWriter();
        for (FormData form : formRepository.findAll()) {
            if(first) {
                thisForm = form.getAsCSV(true);
                first = false;
                csvWriter.append(thisForm);
            }
            else {
                thisForm = form.getAsCSV(false);
                csvWriter.append(thisForm);
            }
        }

        return csvWriter.toString();
    }

    @RequestMapping(value = "/api/csv/{selectedIDs}/selectedRequests.csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public String getAllCSV(@PathVariable("selectedIDs") String[] ids) {
        boolean first = true;
        String thisForm;
        int numberOfFormsAdded = 0;
        List<String> formIDs = new ArrayList<String>(Arrays.asList(ids));
        StringWriter csvWriter = new StringWriter();

        for (FormData form : formRepository.findAll()) {
            if(formIDs.contains(form.getRequestID()))
            {
                if(first) {
                    thisForm = form.getAsCSV(true);
                    first = false;
                    csvWriter.append(thisForm);
                    numberOfFormsAdded++;
                }
                else {
                    thisForm = form.getAsCSV(false);
                    csvWriter.append(thisForm);
                    numberOfFormsAdded++;
                }
            }

            //Don't want to iterate over all the requests if we don't need to
            if(numberOfFormsAdded == formIDs.size())
            {
                break;
            }
        }

        return csvWriter.toString();
    }

    @RequestMapping(value = "/api/invoice/{id}-invoice.pdf", method = RequestMethod.GET, produces =  "application/pdf")
    @ResponseBody
    public byte[] getDocument(@PathVariable("id") String id) {
        //Arbitrary number for initialization
        byte[] pdfBytes = new byte[8];
        for (FormData form : formRepository.findAll()) {
            if(form.getRequestID().equals(id))
            {
                pdfBytes = form.generateInvoicePDF();
            }
        }
        return pdfBytes;
    }

    // Reserve the next request ID in the sequence to ensure each form has a unique request ID
	private String reserveNextRequestID() {
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		Integer formDigit = requestIDRepository.getNextID(year);
		RequestID requestID = new RequestID();
		requestID.setYear(year);
		requestID.setDigits(formDigit);
		requestIDRepository.save(requestID);
        System.out.println("reserving requestID" + String.format("%02d", year) + "-" + String.format("%04d", formDigit) );
        return String.format("%02d", year) + "-" + String.format("%04d", formDigit);
	}

	private String createURLToken(Long formDataID) {
		URLToken urlToken = new URLToken(formDataID);
		String token = urlToken.getToken();

		int numberOfAttempts = 0;
		while (urlTokenRepository.existsByToken(token)) {
			if (numberOfAttempts >= GlobalConstants.MAX__REPEAT_CHECK_UNIQUE) {
				// TODO: Error: too many attempts at getting a unique token
				return "";
			}
			token = urlToken.generateToken();
			numberOfAttempts++;
		}

		urlToken.setToken(token);
		urlTokenRepository.save(urlToken);

		return token;
    }

    @PostMapping(value = "/api/form/save", produces = "text/plain")
    public @ResponseBody ResponseEntity addForm(
            @RequestBody @Valid FormData form,
            BindingResult result,
            Authentication user,
            HttpServletRequest request
    ) {

	    if (form == null) {
            System.out.println("A Null Form was not saved.");
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: Form is null");
	    }

	    if(result.hasErrors()) {
            System.out.println("An invalid Form was not saved. Here's why:");
            ObjectError errorToDisplay = null;
            int errorCount = 1;
	        for(ObjectError e : result.getAllErrors()) {
	            errorToDisplay = e;
	            System.out.println(errorCount + ") " + e.getDefaultMessage());
	            errorCount++;
            }

            if(errorToDisplay != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorToDisplay.getDefaultMessage());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: Form was invalid");
            }
        }

        //Set requestedOnDate to current date
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        form.setRequestedOnDate(dateFormat.format(date));

        boolean loggedOn = user != null;
        if (loggedOn) {
        	form.setRequestStatus(GlobalConstants.AUTHORIZED);
        	form.setAuthorizerID(user.getName());
        	form.setAuthorizerEmailAddress(user.getName() + "@sfu.ca");
        }
        else {
	        form.setRequestStatus(GlobalConstants.WAITING);
        }

        if (form.getRequestID() == null || form.getRequestID().isEmpty()) {
            // Need to reserve a request id for this form
            form.setRequestID(reserveNextRequestID());
        }

        // The form must be saved before the id can be obtained, because the id is the database key
	    form = formRepository.save(form);
	    String token = createURLToken(form.getId());

	    System.out.println("Successfully saved Form with requestID = " + form.getId() + " and token = " + token);

        String requestURL = "https://" + baseUrl + formRequestURL + token;
        if (form.getRequestID() != null) {
	        if (form.getAuthorizerEmailAddress() != null && !form.getAuthorizerEmailAddress().isEmpty()) {
		        try {
			        // Send email to Authorizer requesting authorization
			        sendEmail.sendRequestAuthorizationEmail(form.getAuthorizerEmailAddress(), form.getRequestID(), requestURL);
		        } catch (Exception e) {
			        System.out.print("Error sending the emails: " + e.getMessage());
		        }
	        }

	        if (form.getEmailAddress() != null && !form.getEmailAddress().isEmpty()) {
		        try {
			        // Send Email to the User to confirm the request has been sent
			        sendEmail.sendEventRequestConfirmation(form.getEmailAddress(), form.getRequesterName(), form.getRequestID(), requestURL);
		        } catch (Exception e) {
			        System.out.print("Error sending the emails: " + e.getMessage());
		        }
	        }
        }
        else {
	        System.out.print("Could not find RequestID/TrackingID");
        }

	    return ResponseEntity.ok(token);
    }


    @RequestMapping(value = "/api/form/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<FormData> updateForm(@PathVariable("id") long id,
                                               @RequestBody @Valid FormData data,
                                               BindingResult result,
                                               @RequestParam boolean loggedOn) {
        FormData form = formRepository.findOne(id);
        if (form == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<FormData>(HttpStatus.NOT_FOUND);
        }

        if(result.hasErrors()) {
            return new ResponseEntity<FormData>(form, HttpStatus.BAD_REQUEST);
        }

        if (loggedOn && data.getRequestStatus().equals(GlobalConstants.WAITING)) {
        	data.setRequestStatus(GlobalConstants.AUTHORIZED);
        }

        formRepository.save(data);
        return new ResponseEntity<FormData>(form, HttpStatus.OK);
    }

    @RequestMapping(value = "/servicerequest", method = RequestMethod.GET)
    public String showForm(
            @RequestParam(value = "ticket", required = false) String ticket,
            @RequestParam(value = "gateway", required = false) String gateway,
            HttpServletRequest request) {
        return "form";
    }

    @GetMapping("/")
    public String formRequest() {
        return "form";
    }

    @PostMapping("/")
    public String checkFormRequest(@Valid FormData serviceRequestForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "landing.html";
        }

        saveFormToDatabase(serviceRequestForm);

        return "redirect:/requests.html";
    }

    private void saveFormToDatabase(FormData formData) {
        if (formData.getRequestID() == null || formData.getRequestID().isEmpty()) {
    		// Need to reserve a request id
		    formData.setRequestID(reserveNextRequestID());
	    }

        formRepository.save(formData);
    }

    @ModelAttribute("FormData")
    public FormData createModel() {
        return new FormData();
    }
}
