package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorizeeController {
    @RequestMapping("/authlogin")
    public String authLogin(Model model) {;
        return "authlogin";
    }
    @RequestMapping("/auth")
    public String authRequest(Model model) {;
        return "authrequest";
    }
}
