package ca.sfu.delta.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A controller class for handling CAS authentication. To better understand the workflow see:
 * https://www.sfu.ca/itservices/publishing/publish_howto/enhanced_web_publishing/cas/cas_for_programmers.html
 *
 * @author Lance Hannestad
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/login")
    public String login(
            @RequestParam(defaultValue="/", required=false) String redirect
    ) throws ServletException, IOException {
        // Nothing to do, spring security automatically handles this.
        return "redirect:" + redirect;
    }

    @RequestMapping(value = "/api/user/get", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getCurrentUser(Authentication principal) {
        if (principal == null) {
            Map<String, Object> map = new HashMap();
            map.put("authenticated", false);
            map.put("username", null);
            map.put("privileges", null);
            return map;
        } else {
            String name = principal.getName();

            String authorities = "";
            for (GrantedAuthority authority : principal.getAuthorities()) {
                authorities += authority.getAuthority() + " ";
            }
            authorities = authorities.trim();

            Map<String, Object> map = new HashMap();
            map.put("authenticated", true);
            map.put("username", name);
            map.put("privileges", authorities);

            return map;
        }
    }

}
