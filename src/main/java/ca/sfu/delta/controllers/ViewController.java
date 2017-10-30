package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Controller
public class ViewController extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("").setViewName("form.html");
		registry.addViewController("/results").setViewName("results.html");
		registry.addViewController("/requests").setViewName("requests.html");

		registry.addViewController("/admin").setViewName("admin.html");
		registry.addViewController("/securitylogin").setViewName("securitylogin.html");
	}
}