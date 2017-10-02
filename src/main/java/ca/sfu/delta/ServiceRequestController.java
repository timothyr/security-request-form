package ca.sfu.delta;

import javax.validation.Valid;

import ca.sfu.delta.models.FormData;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Controller
public class ServiceRequestController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
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
        //Todo: save formData to database
    }

    @ModelAttribute("FormData")
	public FormData createModel() {
		return new FormData();
	}
}