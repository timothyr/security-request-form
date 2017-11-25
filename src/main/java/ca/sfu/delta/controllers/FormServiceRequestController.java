package ca.sfu.delta.controllers;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ca.sfu.delta.Utilities.GlobalConstants;
import ca.sfu.delta.models.*;
import ca.sfu.delta.repository.FormRepository;
import ca.sfu.delta.repository.RequestIDRepository;
import ca.sfu.delta.repository.URLTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.StringWriter;
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

    private static final String formFromTokenURL = "/api/form/get/user/";
    private static final String formRequestURL = "/updateform?token=";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/securitylogin").setViewName("securitylogin.html");
        registry.addViewController("/updateform").setViewName("userupdateform");
        //registry.addViewController("/securityview").setViewName("request.html");
    }

    @RequestMapping(value = "/api/user/get", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getCurrentUser(Authentication principal) {
        if (principal == null) {
            Map<String, Object> map = new HashMap();
            map.put("authenticated", false);
            map.put("username", null);
            map.put("privileges", null);
            return map;
        } else {
            String name = principal.getName();

            String authorities = "";
            for (GrantedAuthority authority : principal.getAuthorities()) {
                authorities += authority.getAuthority() + " ";
            }
            authorities = authorities.trim();

            Map<String, Object> map = new HashMap();
            map.put("authenticated", true);
            map.put("username", name);
            map.put("privileges", authorities);

            return map;
        }
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
        		//System.out.println("found form with requestID = "+f.getRequestID());
        		//System.out.println(f.getGuards());
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
	public @ResponseBody String saveSecurity(@RequestBody FormData form,
	                                         @RequestParam(required = false) String additionalMessage)
								throws MessagingException {
		String userName = form.getRequesterName();
		String userEmailAddress = form.getEmailAddress();
		String authEmailAddress = form.getAuthorizerEmailAddress();
		String trackingID = form.getRequestID();

		try {
			//send email to User
			if (userEmailAddress != null && form.getRequestStatus().compareTo("REJECTED") == 0) {
				sendEmail.sendEventRequestRejection(userEmailAddress, userName,
						trackingID, additionalMessage);
			}
			else if (userEmailAddress != null && form.getRequestStatus().compareTo("ACCEPTED") == 0) {
				sendEmail.sendEventRequestApproved(userEmailAddress, userName, trackingID);
			}

			//send email to Authorizer
			if (authEmailAddress != null && form.getRequestStatus().compareTo("REJECTED") == 0) {
				sendEmail.sendEventRequestRejection(authEmailAddress, null,
						trackingID, additionalMessage);
			}
			else if (authEmailAddress != null && form.getRequestStatus().compareTo("ACCEPTED") == 0) {
				sendEmail.sendEventRequestApproved(authEmailAddress, null, trackingID);
			}
		} catch (Exception e) {
			System.out.print("Error sending the emails: " + e.getMessage());
		}

		formRepository.save(form);
		//for (FormData f : formRepository.findAll()) {
		//	System.out.println(f.toString());
		//}

		return "";
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
        String csvString = new String();
        FormData correctForm =  new FormData();
        for (FormData form : formRepository.findAll()) {

            if(form.getRequestID().equals(id))
            {
                correctForm = form;
                break;
            }
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
			@RequestParam(required=false) String requestID,
            @RequestParam(required=false) String authorizerEmailAddress,
            HttpServletRequest request
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
        form.setAuthorizerEmailAddress(authorizerEmailAddress);

        //Set requestedOnDate to current date
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        form.setRequestedOnDate(dateFormat.format(date));

        form.setRequestStatus("WAITING");

        if (requestID != null && !requestID.isEmpty()) {
            form.setRequestID(requestID);
        } else {
            // Need to reserve a request id for this form
            form.setRequestID(reserveNextRequestID());
        }

        form = formRepository.save(form);

        String token = createURLToken(form.getId());
        // Send Email to the User to confirm the request has been sent
        String userName = form.getRequesterName();
        String userEmailAddress = form.getEmailAddress();
        String authEmailAddress = form.getAuthorizerEmailAddress();
        String trackingID = form.getRequestID();
        String requestURL = request.getServerName() + ":" + request.getServerPort() + formRequestURL + token;

        if (form != null) {
            System.out.println("saved");

            //Probably don't need to check here if email Address is null
            if (trackingID != null) {
                //if auhorizer email address is null or is blank
                if (authorizerEmailAddress == null || authorizerEmailAddress.isEmpty()) {
                    try {
                        //Send email to User
                        sendEmail.sendEventRequestConfirmation(userEmailAddress, userName, trackingID, requestURL);
                    } catch (Exception e) {
                        System.out.print("Error sending the email: " + e.getMessage());
                    }
                    System.out.println("Successfully saved Form with requestID = " + form.getId() + " and token = " + token);
                    return token;
                }

                // Else if authorizer and user email exists
                if (userEmailAddress != null && authEmailAddress != null) {
                    try {
                        //Send email to User
                        sendEmail.sendEventRequestConfirmation(userEmailAddress, userName, trackingID, requestURL);

                        //send email to Authorizer
                        sendEmail.sendRequestAuthorizationEmail(authEmailAddress, trackingID, requestURL);
                    } catch (Exception e) {
                        System.out.print("Error sending the emails: " + e.getMessage());
                    }
                    System.out.println("Successfully saved Form with requestID = " + form.getId() + " and token = " + token);
                    return token;
                }
            } else {
                System.out.print("Could not find RequestID/TrackingID");
            }
        } else {
            System.out.println("Failed to save Form");
        }
        return "ERROR: form didn't save";
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

    @RequestMapping(value = "/servicerequest", method = RequestMethod.GET)
    public String showForm(
            @RequestParam(value = "ticket", required = false) String ticket,
            @RequestParam(value = "gateway", required = false) String gateway,
            HttpServletRequest request) {

        getUsernameFromTicket(request, ticket);

        return "form";
    }

    /* This CONSUMES the ticket because they are only single use */
    private String getUsernameFromTicket(HttpServletRequest request, String ticket) {
        if(ticket != null) {
            AuthController authController = new AuthController();
            String baseUrl = authController.getBaseUrl(request);
            String username = authController.getUsernameFromTicket(baseUrl +  request.getServletPath(), ticket);
            if(username == null) {
                System.out.println(request.getServletPath() + " - Invalid ticket - User is not logged in.");
                return null;
            } else {
                System.out.println(request.getServletPath() + " - User is logged in - Username = " + username);
                return username;
            }
        }
        System.out.println(request.getServletPath() + " - User is not logged in.");
        return null;
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
