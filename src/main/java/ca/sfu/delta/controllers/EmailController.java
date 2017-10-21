package ca.sfu.delta.controllers;

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

@RestController
public class EmailController extends WebMvcConfigurerAdapter {

    @Autowired
    private JavaMailSender sender;

    @RequestMapping(value = "/api/form/", method = RequestMethod.POST)
    private String home(@RequestParam("email-address") String emailAddress){
        //SendEmail emailParameters = new SendEmail();
        //emailParameters.getFirstName();

        try{
            sendEmail(emailAddress);
            System.out.println("Email sent to: " + emailAddress);
            return "An email has been sent for your record.";

        } catch (Exception ex) {
            return "Error sending mail: " + ex;
        }
    }


    public void sendEmail(String emailAddress) throws Exception {
        //Send Notification
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //Recipient's EmailID
        helper.setTo(emailAddress);
        helper.setText("Your request has been submitted.");
        helper.setSubject("Security Request Confirmation");

        sender.send(message);
    }
}
