package ca.sfu.delta.controllers;

import ca.sfu.delta.models.*;
import ca.sfu.delta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {
	@Autowired FormRepository formRepository;
	@Autowired RequestIDRepository requestIDRepository;
	@Autowired GuardRepository guardRepository;
	@Autowired UserRepository userRepository;
	@Autowired DistributionRepository distributionRepository;

	@Autowired AuthController authController;

	@PostConstruct
	public void init() {
		SecurityUser lance = new SecurityUser("lhannest");
		lance.setRole(SecurityUser.Role.ADMIN);
		userRepository.save(lance);
		userRepository.save(new SecurityUser("hlotey"));
		userRepository.save(new SecurityUser("timr"));
		userRepository.save(new SecurityUser("awessel"));
		userRepository.save(new SecurityUser("cbinnie"));
		userRepository.save(new SecurityUser("raymonde"));
		userRepository.save(new SecurityUser("rcretu"));
		userRepository.save(new SecurityUser("ska158"));
	}

	/**
	 * This method allows us to set up data binders for custom objects, e.g.
	 * the SecurityUser.Role enum.
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(SecurityUser.Role.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(SecurityUser.Role.valueOf(text.toUpperCase()));
			}
		});
	}

	@RequestMapping(value="/distribution/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<DistributionEntry> getDistribution(@PathVariable("id") Long id) {
		DistributionEntry d = distributionRepository.findOne(id);
		if (d != null) {
			return ResponseEntity.ok(d);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/distribution/delete/{id}", method = RequestMethod.GET, produces = "application/json")
	public void deleteDistribution(@PathVariable("id") Long id) {
		distributionRepository.delete(id);
	}

	@RequestMapping(value="/distribution/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<DistributionEntry>> searchDistributions(@RequestParam(required=true) String authtoken) {
		List<DistributionEntry> distributions = new ArrayList<DistributionEntry>();
		Iterable<DistributionEntry> itr = distributionRepository.findAll();
		itr.forEach(distributions::add);
		return ResponseEntity.ok(distributions);
	}

	@RequestMapping(value="/distribution/save", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<DistributionEntry> saveDistribution(
			@RequestParam(required=true) String email,
			@RequestParam(required=true) String name
	) {
		DistributionEntry distribution = new DistributionEntry();
		distribution.setName(name);
		distribution.setEmail(email);
		distribution = distributionRepository.save(distribution);
		if (distribution != null) {
			return ResponseEntity.ok(distribution);
		} else {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/user/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<SecurityUser> getUser(
			@PathVariable("id") Long id,
			@RequestParam(required=true) String authtoken
	) {
		SecurityUser user = userRepository.findOne(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/user/delete/{id}", method = RequestMethod.GET, produces = "application/json")
	public void deleteUser(
			@PathVariable("id") Long id
	) {
		userRepository.delete(id);
	}

	@RequestMapping(value="/user/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SecurityUser>> searchUsers(@RequestParam(required=true) String authtoken) {
		List<SecurityUser> users = new ArrayList<SecurityUser>();
		Iterable<SecurityUser> itr = userRepository.findAll();
		itr.forEach(users::add);
		return ResponseEntity.ok(users);
	}

	@RequestMapping(value="/user/save", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<SecurityUser> saveUser(
			@RequestParam(required=true) String username,
			@RequestParam(required=true) SecurityUser.Role role,
			@RequestHeader(value="X-Authorization") String authtoken
	) {
		if (!authController.isValid(authtoken)) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		SecurityUser user = new SecurityUser();
		user.setUsername(username);
		user.setRole(role);
		user = userRepository.save(user);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/guard/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Guard> getGuard(@PathVariable("id") Long id) {
		Guard guard = guardRepository.findOne(id);
		if (guard != null) {
			return ResponseEntity.ok(guard);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/guard/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Guard>> searchGuard() {
		List<Guard> guards = new ArrayList<Guard>();
		Iterable<Guard> itr = guardRepository.findAll();
		itr.forEach(guards::add);
		return ResponseEntity.ok(guards);
	}

	@RequestMapping(value="/guard/save", method = RequestMethod.GET, produces = "application/json")
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

	@RequestMapping(value = "/form/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<FormData> getForm(@PathVariable("id") Long id) {
		FormData form = formRepository.findOne(id);
		if (form != null) {
			return ResponseEntity.ok(form);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/form/search", method = RequestMethod.GET, produces = "application/json")
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

	@RequestMapping(value = "/form/save", method = RequestMethod.GET, produces = "application/json")
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
