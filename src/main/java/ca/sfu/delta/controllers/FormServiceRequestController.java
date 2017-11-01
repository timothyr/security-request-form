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

    public static final String formFromTokenURL = "/api/form/get/user/";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/updateform").setViewName("userupdateform.html");
        registry.addViewController("/securityview").setViewName("securityview.html");
    }


    @RequestMapping(value = "/api/form/getByRequestID/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody FormData getForm(@PathVariable("id") String id) {
        for (FormData f : formRepository.findAll()) {
        	if (f.getRequestID().equals(id)) {
        		System.out.println("found form with requestID = "+f.getRequestID());
        		System.out.println(f);
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
		    return ResponseEntity.ok(form);
	    }
	    else {
    		// could not find the form corresponding to the token
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
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
