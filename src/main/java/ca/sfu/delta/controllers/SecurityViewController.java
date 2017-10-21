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

	@RequestMapping("/security/request/{reqID}")
	public String securityview(@PathVariable long reqID, Model model) {

		FormData form;

		if (reqID == 1234) {
			// dummy data for debugging
			// TODO: remove for production

			form = new FormData("Department", "Aug 22, 2017", "Steve Smith",
					"123456789", "604-430-4444", "(04) 3056 6523",
					"smithysmithers@sfu.ca", "Aug 10, 2017", "Beer Pong",
					false, 400, "7:00pm - 2:00am", "546-546546-5453131",
					true, "Biggest thing ever");
		} else {
			form = formRepository.findOne(reqID);
		}

		model.addAttribute("reqID", reqID);
		model.addAttribute("form", form);

		Map modelMap = model.asMap();
		for (Object modelKey : modelMap.keySet()) {
			Object modelValue = modelMap.get(modelKey);
			System.out.println(modelKey+" -> "+modelValue);
		}

		return "securityview";
	}

}
