package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {

    @GetMapping("/")
    public String showForm() {
        return "form.html";
    }

	@GetMapping("/requests")
	public String showRequests() {
		return "requestsView.html";
	}
}