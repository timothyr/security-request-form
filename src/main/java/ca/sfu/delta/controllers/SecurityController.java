package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/security")
public class SecurityController {
    @RequestMapping("/request/**")
    public String securityLogin() {;
        return "/securityview";
    }
}
