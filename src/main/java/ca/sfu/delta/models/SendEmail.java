package ca.sfu.delta.models;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmail {

    private JavaMailSender javaMailSender;

    @Autowired
    public SendEmail(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendTo(String sendToEmailAddress, String personName, String trackingID, String requestURL) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String greeting;
        if (personName == null || personName.isEmpty()) {
        	greeting = "Hello!";
        }
        else {
        	greeting = "Hello, " + personName + "!";
        }

        helper.setTo(sendToEmailAddress);
        helper.setSubject("SFU: Your Request Confirmation");
        helper.setText(greeting + " Your request has been sent to the authorities." +
                " You will be contacted shortly by security. Your request ID is: " + trackingID + "." +
                " You may view your request at " + requestURL);

        try{
            javaMailSender.send(message);
        }
        catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void sendTo(String sendToEmailAddress, String trackingID, String requestURL) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(sendToEmailAddress);
        helper.setSubject("SFU: Request for authorization");
        helper.setText("Hi! You have been requested to authorize request: " + trackingID +
                " You may view the request at: " + requestURL);

        try{
            javaMailSender.send(message);
        }
        catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }
}
