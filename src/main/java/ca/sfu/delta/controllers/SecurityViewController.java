package ca.sfu.delta.controllers;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class SecurityViewController {

	@Autowired
	FormRepository formRepository;

	// redirects to /requests if bad reqID
	@RequestMapping("/security/request/{reqID}")
	public String securityView(@PathVariable String reqID, Model model) {

		FormData form;
		long id;

		try {
			id = Long.parseLong(reqID);
		} catch (NumberFormatException e) {
			return "redirect:/requests";
		}

		// save a form for debugging
		if (id == 1234) {
			form = new FormData("Department", "Aug 22, 2017", "Steve Smith",
					"123456789", "604-430-4444", "(04) 3056 6523",
					"smithysmithers@sfu.ca", "Aug 10, 2017", "Beer Pong",
					false, 400, "7:00pm - 2:00am", "546-546546-5453131",
					true, "Biggest thing ever");
			form.setId(id);
			System.out.println(id);
			form = formRepository.save(form);
			if (form == null) {
				System.out.println("Form didn't save");
			} else {
				System.out.println("Form saved with id " + form.getId());
			}
		}

		model.addAttribute("reqID, id");

		return "securityview";
	}

}
