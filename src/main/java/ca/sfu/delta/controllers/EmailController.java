package ca.sfu.delta.controllers;

import ca.sfu.delta.models.FormData;
import ca.sfu.delta.models.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.jws.WebResult;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@RestController
public class EmailController extends WebMvcConfigurerAdapter {

    @RequestMapping(value = "//", method = RequestMethod.POST)
    public @ResponseBody
    String sendConfirmationEmail(@RequestParam String emailAddress){
        //SendEmail emailParameters = new SendEmail();
        //emailParameters.getFirstName();

        SendEmail eMail = new SendEmail();
        try{
            eMail.sendEmail("singh.harjot85@gmail.com");
            System.out.println("Email sent to: " + emailAddress);
            return "An email has been sent for your record.";

        } catch (Exception ex) {
            return "Error sending mail: " + ex;
        }
    }



}
