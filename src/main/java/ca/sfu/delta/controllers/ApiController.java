package ca.sfu.delta.controllers;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.models.Guard;
import ca.sfu.delta.models.RequestID;
import ca.sfu.delta.repository.FormRepository;
import ca.sfu.delta.repository.GuardRepository;
import ca.sfu.delta.repository.RequestIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Controller
public class ApiController {
	@Autowired FormRepository formRepository;
	@Autowired RequestIDRepository requestIDRepository;
	@Autowired GuardRepository guardRepository;

	@RequestMapping(value="/api/test")
	public @ResponseBody FormData test() {
		FormData formData = new FormData();
		Guard a = new Guard("Sam", 0, 0, 0, 0);
		Guard b = new Guard("Fredrik", 0, 0, 0, 0);
		formData.setSecurityFields(
				"Joe",
				Arrays.asList(new Guard[]{a, b}),
				Arrays.asList(new String[]{"hello", "goodbye", "Friend."}),
				null,
				null
		);

		return formData;
	}

	@RequestMapping(value="/api/guard/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Guard> getGuard(@PathVariable("id") Long id) {
		Guard guard = guardRepository.findOne(id);
		if (guard != null) {
			return ResponseEntity.ok(guard);
		} else {
			return new ResponseEntity<Guard>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/api/guard/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Guard>> searchGuard() {
		List<Guard> guards = new ArrayList<Guard>();
		Iterable<Guard> itr = guardRepository.findAll();
		itr.forEach(guards::add);
		return ResponseEntity.ok(guards);
	}

	@RequestMapping(value="/api/guard/save", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Guard> saveGuard(
			@RequestParam(required=true) String name,
			@RequestParam(required=true) Integer regularHours,
			@RequestParam(required=true) Integer overtimeHours,
			@RequestParam(required=true) Double regularRate,
			@RequestParam(required=true) Double overtimeRate
	) {
		Guard guard = new Guard(name, regularHours, regularRate, overtimeHours, overtimeRate);
		guard = guardRepository.save(guard);
		if (guard != null) {
			return ResponseEntity.ok(guard);
		} else {
			return new ResponseEntity<Guard>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/form/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<FormData> getForm(@PathVariable("id") Long id) {
		FormData form = formRepository.findOne(id);
		if (form != null) {
			return ResponseEntity.ok(form);
		} else {
			return new ResponseEntity<FormData>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/api/form/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<FormData>> search() {
		List<FormData> forms = new ArrayList<FormData>();
		Iterable<FormData> itr = formRepository.findAll();
		itr.forEach(forms::add);
		return ResponseEntity.ok(forms);
	}

	// Reserve the next request ID in the sequence to ensure each form has a unique request ID
	private String reserveNextRequestID() {
		int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
		Integer formDigit = requestIDRepository.getNextID(year);
		//ToDo: ensure nextDigit is only four digits (i.e. less than 10,000)
		RequestID requestID = new RequestID();
		requestID.setYear(year);
		requestID.setDigits(formDigit);
		requestIDRepository.save(requestID);

		return String.format("%02d", year) + "-" + String.format("%04d", formDigit);
	}

	@RequestMapping(value = "/api/form/save", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<FormData> addForm(
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

		if (requestID == null || requestID.isEmpty()) {
			// Need to reserve a request id for this form
			form.setRequestID(reserveNextRequestID());
		} else {
			form.setRequestID(requestID);
		}

		form = formRepository.save(form);

		if (form != null) {
			System.out.println("Successfully saved Form with requestID=" + form.getId());
			return ResponseEntity.ok(form);
		} else {
			System.out.println("Failed to save Form");
			return new ResponseEntity<FormData>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
