package ca.sfu.delta.controllers;

import ca.sfu.delta.models.SecurityUser;
import ca.sfu.delta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
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
    public static final String COOKIE_NAME = "sfu-event-form-authtoken";

    @Autowired UserRepository userRepository;

    private TokenManager tokenManager = new TokenManager();

    public boolean isValid(String token) {
        return tokenManager.tokenExists(token);
    }

    public boolean isInvalid(String token) {
        return ! isValid(token);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String handleLoginRequest(
            @RequestParam(value = "ticket", required = false) String ticket,
            @RequestParam(value = "redirect", required = false) String redirect,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes attributes
    ) {
        String baseUrl = getBaseUrl(request);

        String service = baseUrl + "/login";

        if (redirect != null) {
            service += "?redirect=" + redirect;
        }

        if (ticket == null) {
            return "redirect://cas.sfu.ca/cas/login?service=" + service;
        } else {
            String url = "https://cas.sfu.ca/cas/serviceValidate?ticket=" + ticket + "&service=" + service;

            String casResponse = httpRequest(url);

            String username = getStringBetween(casResponse, "<cas:user>", "</cas:user>");

            List<SecurityUser> users = userRepository.getUser(username);
            if (users != null && !users.isEmpty()) {
                SecurityUser user = users.get(0);
                SecurityUser.Role role = user.getRole();
                boolean isAdmin = user.getRole() == SecurityUser.Role.ADMIN;
                boolean isSecurity = user.getRole() == SecurityUser.Role.SECURITY;

                if (!isSecurity && !isAdmin) {
                    throw new RuntimeException("Error 401");
                }
            } else {
                throw new RuntimeException("Error 401");
            }

            if (username != null) {
                String token = tokenManager.getToken(username);
                token = token != null ? token : tokenManager.createToken(username);

                attributes.addFlashAttribute("X-Authorization", token);
                response.addCookie(new Cookie(COOKIE_NAME, token));
                response.addHeader("X-Authorization", token);
                return "redirect:/" + redirect;
            } else {
                throw new RuntimeException("Username could not be extracted from CAS response");
            }
        }
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public @ResponseBody String handleAuthenticationSuccess(@RequestParam(value="username") String username) {
        return username;
    }

    /**
     * Taken from https://stackoverflow.com/a/45339284
     * @param req an HTTP request
     * @return the base URL of the server issuing this request
     */
    private String getBaseUrl(HttpServletRequest req) {
        return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
    }

    private String getStringBetween(String text, String leftTag, String rightTag) {
        Matcher matcher = Pattern.compile(leftTag + "(.*?)" + rightTag).matcher(text);

        if (matcher.find()) {
            String substring = matcher.group();
            substring = substring.replace(leftTag, "");
            substring = substring.replace(rightTag, "");
            return substring;

        } else {
            return null;
        }
    }

    private String httpRequest(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuffer response = new StringBuffer();

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            return response.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
