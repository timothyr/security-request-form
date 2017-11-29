package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @RequestMapping({"", "/"})
    public String defaultView() {
        return "/requests";
    }

    @RequestMapping("/login")
    public String loginView() {
        return "/securitylogin";
    }

    @RequestMapping("/request/**")
    public String securityLoginView() {;
        return "/request";
    }

    @RequestMapping("/requests")
    public String securityRequestsView() {;
        return "/requests";
    }

    @RequestMapping("/admin")
    public String adminView() {
        return "/admin";
    }

}
