package ca.sfu.delta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private String username;

    /**
     * Checks whether or not the current user is authenticated.
     *
     * @return <b>true</b> if the current user is authenticated, otherwise <b>false</b>
     */
    public boolean isAuthenticated() {
        return username != null;
    }

    /**
     * @return The SFU username of the current user if the user is authenticated, otherwise <b>null</b>
     */
    public String getUsername() {
        return username;
    }

    /**
     * Ensures that all further calls to {@code isAuthenticated()} returns <b>false</b>.
     * Does not actually log out through CAS, since this is not recommended by the CAS documentation.
     */
    public void logout() {
        username = null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String handleLoginRequest(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest req
    ) {
        String baseUrl = getBaseUrl(req);

        if (ticket == null) {
            return "redirect://cas.sfu.ca/cas/login?service=" + baseUrl + "/requests";
        } else {
            String url = "https://cas.sfu.ca/cas/serviceValidate?service=" + baseUrl + "/requests&ticket=" + ticket;
            String response = httpRequest(url);

            username = getStringBetween(response, "<cas:user>", "</cas:user>");

            return baseUrl;
        }
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
