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
        	greeting = "Hello!\n\n";
        }
        else {
        	greeting = "Hello " + personName + "!\n\n";
        }

        helper.setTo(sendToEmailAddress);
        helper.setSubject("SFU: Your Event Security Request Confirmation");
        helper.setText(greeting + " Your request has been sent to SFU security.\n" +
                " You will be contacted shortly! Your request ID is: " + trackingID + ".\n\n" +
                " You may view your request at " + requestURL);

        try{
            javaMailSender.send(message);
        }
        catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }
}
