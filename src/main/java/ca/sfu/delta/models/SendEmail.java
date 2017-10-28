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

    public void sendTo(String sendToEmailAddress, String personName, String trackingID) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(sendToEmailAddress);
        helper.setSubject("SFU: Your Request Confirmation");
        helper.setText("Hello, " + personName + "!" + "Your request has been sent to the authority. " +
                "You will be contacted shortly by the security. Your request ID is: " + trackingID);

        try{
            javaMailSender.send(message);
        }
        catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }
    public void sendTo(String sendToEmailAddress, String trackingID) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(sendToEmailAddress);
        helper.setSubject("SFU: Your Request Confirmation");
        helper.setText("Hello!" + "Your request has been sent to the authority. " +
                "You will be contacted shortly by the security. Your request ID is: " + trackingID);

        try{
            javaMailSender.send(message);
        }
        catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }
}
