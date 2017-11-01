package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
    @RequestMapping("/security")
    public String securityLogin(Model model) {;
        return "securitylogin";
    }
}
