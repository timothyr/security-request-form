package ca.sfu.delta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;


@Controller
public class ViewController extends WebMvcConfigurerAdapter {
	@Autowired AuthController authController;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("form.html");
		registry.addViewController("/results").setViewName("results.html");

		registry.addViewController("/admin").setViewName("adminPage.html");
		registry.addViewController("/securitylogin").setViewName("securitylogin.html");
	}

	@RequestMapping("/")
	public String viewForm() {
		return "form.html";
	}

	@RequestMapping("/requests")
	public String viewRequests(
			@RequestHeader(value="X-Authorization", required=false) String headertoken,
			@ModelAttribute("X-Authorization") String modeltoken
	) {
		String token = headertoken != null ? headertoken : modeltoken;
		if (authController.isValid(token)) {
			return "requestsPage.html";
		} else {
			return "/login?redirect=requests";
		}
	}

	@RequestMapping("/admin")
	public String viewAdmin(
			@RequestHeader(value="X-Authorization", required=false) String headertoken,
			@ModelAttribute("X-Authorization") String modeltoken
	) {
		String token = headertoken != null ? headertoken : modeltoken;
		if (authController.isValid(token)) {
			return "adminPage.html";
		} else {
			return "/login?redirect=admin";
		}
	}
}