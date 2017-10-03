package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestsController {

    @RequestMapping("/requests")
    public String requests() {
        return "requests";
    }

}
