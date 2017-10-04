package ca.sfu.delta.controllers;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RequestsController {


    @Autowired
    FormRepository formRepository;
    @RequestMapping("/requests")
    public String requests(Model model) {
        model.addAttribute("requests", formRepository.findAll());
        return "requests";
    }

}
