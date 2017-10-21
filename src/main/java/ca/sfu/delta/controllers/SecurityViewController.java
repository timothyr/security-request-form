package ca.sfu.delta.controllers;

import ca.sfu.delta.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityViewController {

	@Autowired
	FormRepository formRepository;

	@RequestMapping("/security/request/{id}")
	public String securityview(@PathVariable(value = "id", required = true) long id, Model model) {
		model.addAttribute("securityview", formRepository.findOne(id));
		return "securityview";
	}

}
