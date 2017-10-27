package ca.sfu.delta.controllers;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.models.Guard;
import ca.sfu.delta.models.RequestID;
import ca.sfu.delta.models.User;
import ca.sfu.delta.repository.FormRepository;
import ca.sfu.delta.repository.GuardRepository;
import ca.sfu.delta.repository.RequestIDRepository;
import ca.sfu.delta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ApiController {
	@Autowired FormRepository formRepository;
	@Autowired RequestIDRepository requestIDRepository;
	@Autowired GuardRepository guardRepository;
	@Autowired UserRepository userRepository;

	/**
	 * This method allows us to set up data binders for custom objects, e.g.
	 * the User.Role enum.
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(User.Role.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(User.Role.valueOf(text.toUpperCase()));
			}
		});
	}

	@RequestMapping(value="/api/user/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<User> getUser(
			@PathVariable("id") Long id,
			@RequestParam(required=true) String authtoken
	) {
		User user = userRepository.findOne(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/api/user/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<User>> searchUsers(@RequestParam(required=true) String authtoken) {
		List<User> users = new ArrayList<User>();
		Iterable<User> itr = userRepository.findAll();
		itr.forEach(users::add);
		return ResponseEntity.ok(users);
	}

	@RequestMapping(value="/api/user/save", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<User> saveUser(
			@RequestParam(required=true) String username,
			@RequestParam(required=true) User.Role role,
			@RequestParam(required=true) String authtoken
	) {
		User user = new User();
		user.setUsername(username);
		user.setRole(role);
		user = userRepository.save(user);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/api/guard/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Guard> getGuard(@PathVariable("id") Long id) {
		Guard guard = guardRepository.findOne(id);
		if (guard != null) {
			return ResponseEntity.ok(guard);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
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
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/api/form/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<FormData> getForm(@PathVariable("id") Long id) {
		FormData form = formRepository.findOne(id);
		if (form != null) {
			return ResponseEntity.ok(form);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
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
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
