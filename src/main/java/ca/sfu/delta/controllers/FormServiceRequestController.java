package ca.sfu.delta.controllers;
import javax.mail.MessagingException;
import javax.validation.Valid;

import ca.sfu.delta.Utilities.GlobalConstants;
import ca.sfu.delta.models.FormData;
import ca.sfu.delta.models.RequestID;
import ca.sfu.delta.models.URLToken;
import ca.sfu.delta.models.SendEmail;
import ca.sfu.delta.repository.FormRepository;
import ca.sfu.delta.repository.RequestIDRepository;
import ca.sfu.delta.repository.URLTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.StringWriter;


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

    private static final String formFromTokenURL = "/api/form/get/user/";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results.html");
        registry.addViewController("/requests").setViewName("requests.html");

        registry.addViewController("/admin").setViewName("admin.html");
        registry.addViewController("/securitylogin").setViewName("securitylogin.html");
        registry.addViewController("/updateform").setViewName("userupdateform.html");
        registry.addViewController("/securityview").setViewName("securityview.html");
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
        		System.out.println("found form with requestID = "+f.getRequestID());
        		System.out.println(f.jsonify());
        		return f;
			}
		}

        return null;
    }

    @RequestMapping(value = formFromTokenURL + "{token}", method = RequestMethod.GET, produces = "application/json")
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
	public @ResponseBody String saveSecurity(@RequestBody FormData form) {
		formRepository.save(form);
		for (FormData f : formRepository.findAll()) {
			System.out.println(f.toString());
		}

		return null;
	}

    @RequestMapping(value = "/api/csv/form/{id}.csv", method = RequestMethod.GET, produces = "text/csv")
    @ResponseBody
    public String getCSV(@PathVariable("id") String id) {
        String csvString = new String();
        for (FormData form : formRepository.findAll()) {

            if(form.getRequestID().equals(id))
            {
                csvString = form.getAsCSV(true);
            }
        }
        System.out.println(csvString);
        return csvString;
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

    // Reserve the next request ID in the sequence to ensure each form has a unique request ID
	private String reserveNextRequestID() {
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		Integer formDigit = requestIDRepository.getNextID(year);
		RequestID requestID = new RequestID();
		requestID.setYear(year);
		requestID.setDigits(formDigit);
		requestIDRepository.save(requestID);

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

    @RequestMapping(value = "/api/form/save", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String addForm(
            @RequestParam(required=false) String department,
            @RequestParam(required=false) String requesterName,
            @RequestParam(required=false) String phoneNumber,
            @RequestParam(required=false) String requestedOnDate,
            @RequestParam(required=false) String requesterID,
            @RequestParam(required=false) String authorizationDate,
            @RequestParam(required=false) String paymentAccountCode,
            @RequestParam(required=false) String emailAddress,
            @RequestParam(required=false) String times,
            @RequestParam(required=false) String eventName,
            @RequestParam(required=false) Boolean isLicensed,
            @RequestParam(required=false) Integer numAttendees,
            @RequestParam(required=false) String authorizerId,
            @RequestParam(required=false) String authorizerPhoneNumber,
            @RequestParam(required=false) String serviceRequestNumber,
            @RequestParam(required=false) String eventLocation,
            @RequestParam(required=false) String authorizerName,
            @RequestParam(required=false) String eventDates,
            @RequestParam(required=false) String eventDetails,
            @RequestParam(required=false) String faxNumber,
			@RequestParam(required=false) String requestID
            ) {

        FormData form = new FormData();
        form.setDepartment(department);
        form.setRequesterName(requesterName);
        form.setPhoneNumber(phoneNumber);
        form.setRequesterID(requesterID);
        form.setAuthorizationDate(authorizationDate);
        form.setPaymentAccountCode(paymentAccountCode);
        form.setEmailAddress(emailAddress);
        form.setTimes(times);
        form.setEventName(eventName);
        form.setIsLicensed(isLicensed);
        form.setNumAttendees(numAttendees);
        form.setAuthorizerID(authorizerId);
        form.setAuthorizerPhoneNumber(authorizerPhoneNumber);
        form.setServiceRequestNumber(serviceRequestNumber);
        form.setEventLocation(eventLocation);
        form.setAuthorizerName(authorizerName);
        form.setEventDates(eventDates);
        form.setEventDetails(eventDetails);
        form.setFaxNumber(faxNumber);

        //Set requestedOnDate to current date
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        form.setRequestedOnDate(dateFormat.format(date));

        //TODO: make this actually useful instead of this bandaid solution
        form.setRequestStatus("waiting");

        if (requestID != null && !requestID.isEmpty()) {
	        form.setRequestID(requestID);
        } else {
        	// Need to reserve a request id for this form
	        form.setRequestID(reserveNextRequestID());
        }

        form = formRepository.save(form);

        if (form != null) {

            System.out.println("saved");

	        String token = createURLToken(form.getId());
            // Send Email to the User to confirm the request has been sent
            String userEmailAddress = form.getEmailAddress();
            String userName = form.getRequesterName();
            String trackingID = form.getRequestID();
            String requestURL = GlobalConstants.SERVER_HOST_ADDRESS + formFromTokenURL + token;
            //Probably don't need to check here if email Address is null
            if(userEmailAddress != null && trackingID != null) {
                try {
                    sendEmail.sendTo(userEmailAddress, userName, trackingID, requestURL);
                } catch (MessagingException ex) {
                    System.out.println("Could not send the email. Error message: "+ ex.getMessage());
                    //e.printStackTrace();
                }
            } else {
                System.out.println("Could not send Email. Please ensure all the parameters are valid.");
            }
	        System.out.println("Successfully saved Form with requestID = " + form.getId() + " and token = " + token);
	        return String.valueOf(token);
        } else {
            System.out.println("Failed to save Form");
            return "ERROR: form didn't save";
        }
    }

    @RequestMapping(value = "/api/form/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<FormData> updateForm(@PathVariable("id") long id, @RequestBody FormData data) {
        FormData form = formRepository.findOne(id);
        if (form==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<FormData>(HttpStatus.NOT_FOUND);
        }
        form.setDepartment(data.getDepartment());
        form.setRequesterName(data.getRequesterName());
        form.setPhoneNumber(data.getPhoneNumber());
        form.setRequestedOnDate(data.getRequestedOnDate());
        form.setRequesterID(data.getRequesterID());
        form.setAuthorizationDate(data.getAuthorizationDate());
        form.setPaymentAccountCode(data.getPaymentAccountCode());
        form.setEmailAddress(data.getEmailAddress());
        form.setTimes(data.getTimes());
        form.setEventName(data.getEventName());
        form.setIsLicensed(data.getIsLicensed());
        form.setNumAttendees(data.getNumAttendees());
        form.setAuthorizerID(data.getAuthorizerID());
        form.setAuthorizerPhoneNumber(data.getAuthorizerPhoneNumber());
        form.setServiceRequestNumber(data.getServiceRequestNumber());
        form.setEventLocation(data.getEventLocation());
        form.setAuthorizerName(data.getAuthorizerName());
        form.setEventDates(data.getEventDates());
        form.setEventDetails(data.getEventDetails());
        form.setFaxNumber(data.getFaxNumber());

        formRepository.save(form);
        return new ResponseEntity<FormData>(form, HttpStatus.OK);
    }

    @GetMapping("/")
    public String showForm(FormData serviceRequestForm) {
        return "form.html";
    }

    @PostMapping("/")
    public String checkFormRequest(@Valid FormData serviceRequestForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form.html";
        }

        saveFormToDatabase(serviceRequestForm);

        return "redirect:/requests.html";
    }

    private void saveFormToDatabase(FormData formData) {
    	System.out.println("Saving");
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
