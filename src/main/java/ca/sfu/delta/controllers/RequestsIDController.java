package ca.sfu.delta.controllers;

import ca.sfu.delta.models.RequestID;
import ca.sfu.delta.repository.RequestIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/RequestID")
public class RequestsIDController {
    @Autowired
    private RequestIDRepository requestIDRepository;

	@GetMapping(path="/all")
	public @ResponseBody Iterable<RequestID> getAllRequestIDs() {
		return requestIDRepository.findAll();
	}

	@GetMapping(path="/reserveNext")
	public @ResponseBody String reserveNextRequestID(@RequestParam Integer year) {
		//ToDo: ensure year is only two digits (i.e. less than 100)
    	Integer nextDigit = requestIDRepository.getNextID(year);
		//ToDo: ensure nextDigit is only four digits (i.e. less than 10,000)
		addNewRequestID(year, nextDigit);

		return String.format("%04d", nextDigit) + "-" + String.format("%02d", year);
	}

	@GetMapping(path="/add")
	private  @ResponseBody String addNewRequestID (@RequestParam Integer year, @RequestParam Integer digits) {
		RequestID requestID = new RequestID();
		requestID.setYear(year);
		requestID.setDigits(digits);
		requestIDRepository.save(requestID);

		return "Saved";
	}
}
