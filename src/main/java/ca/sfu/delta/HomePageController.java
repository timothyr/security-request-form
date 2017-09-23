package ca.sfu.delta;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HomePageController {
    @RequestMapping("/")
    public String index() {
        return "Team Delta";
    }
}
