package ca.sfu.delta.controllers;

import ca.sfu.delta.repository.FormRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestsController {

    @RequestMapping("/requests")
    public String requests(Model model) {
        model.addAttribute("requests", FormRepository.findAll());
        return "requests";
    }

}
