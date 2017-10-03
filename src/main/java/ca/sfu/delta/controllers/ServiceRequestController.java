package ca.sfu.delta.controllers;

import javax.validation.Valid;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ServiceRequestController extends WebMvcConfigurerAdapter {
    @Autowired
    FormRepository formRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @RequestMapping(value = "/api/form/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getForm(@PathVariable("id") Long id) {
        FormData form = formRepository.findOne(id);
        return form.jsonify();
    }

    @RequestMapping(value = "/api/form/search/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Map<String, Object>> search() {
        List<Map<String, Object>> forms = new ArrayList<Map<String, Object>>();

        for (FormData form : formRepository.findAll()) {
            forms.add(form.jsonify());
        }

        return forms;
    }

    @RequestMapping(value = "/api/form/save/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String addForm(
            @RequestParam String department,
            @RequestParam String requesterName,
            @RequestParam String phoneNumber,
            @RequestParam String requestedOnDate,
            @RequestParam String requesterID,
            @RequestParam String authorizationDate,
            @RequestParam String paymentAccountCode,
            @RequestParam String emailAddress,
            @RequestParam String times,
            @RequestParam String eventName,
            @RequestParam boolean isLicensed,
            @RequestParam int numAttendees,
            @RequestParam String authorizerId,
            @RequestParam String authorizerPhoneNumber,
            @RequestParam String serviceRequestNumber,
            @RequestParam String eventLocation,
            @RequestParam String authorizerName,
            @RequestParam String eventDates,
            @RequestParam String eventDetails,
            @RequestParam String faxNumber

            ) {
        FormData form = new FormData();
        form.setDepartment(department);
        form.setRequesterName(requesterName);
        form.setPhoneNumber(phoneNumber);
        form.setRequestedOnDate(requestedOnDate);
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


        form = formRepository.save(form);

        if (form != null) {
            return String.valueOf(form.getId());
        } else {
            return "ERROR: form didn't save";
        }
    }

    @GetMapping("/")
    public String showForm(FormData serviceRequestForm) {
        return "form";
    }

    @PostMapping("/")
    public String checkFormRequest(@Valid FormData serviceRequestForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        saveFormToDatabase(serviceRequestForm);

        return "redirect:/results";
    }

    private void saveFormToDatabase(FormData formData) {
        System.out.printf("Form submitted: " + String.valueOf(formData));
    }

    @ModelAttribute("FormData")
	public FormData createModel() {
		return new FormData();
	}
}