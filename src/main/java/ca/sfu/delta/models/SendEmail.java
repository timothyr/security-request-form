package ca.sfu.delta.models;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

@Service
public class SendEmail {

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.username}")
    private String smtpSenderEmail;
    @Value("${spring.mail.password}")
    private String smtpSenderEmailPassword;
    @Value("${spring.mail.port}")
    private String smtpPort;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private Boolean IsAuthorized;
    @Value("{spring.mail.properties.mail.smtp.starttls.enable}")
    private String IsTtlsEnabled;

    @Autowired
    public SendEmail(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendTo(String sendToEmailAddress, String personName, String trackingID, String requestURL) throws MessagingException {

        //Mail Properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.user", smtpSenderEmail);
        properties.put("mail.smtp.password", smtpSenderEmailPassword);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", IsAuthorized);

        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);

        String greeting;
        if (personName == null || personName.isEmpty()) {
        	greeting = "Hello!\n\n";
        }
        else {
        	greeting = "Hello " + personName + "!\n\n";
        }

        message.setFrom(new InternetAddress(smtpSenderEmail));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmailAddress));
        message.setSubject("SFU: Your Event Security Request Confirmation");
        message.setText(greeting + " Your request has been sent to SFU security.\n" +
                " You will be contacted shortly! Your request ID is: " + trackingID + ".\n\n" +
                " You may view your request at " + requestURL);

        MimeMultipart multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();

        Map<String, String> input = new HashMap<>();
        input.put("User",personName);

        String htmlText = readEmailFromHtml("/home/harjotsingh/Documents/Project373/Project/src/main/resources/static/emailUser.html",input);

        messageBodyPart.setContent(htmlText, "text/html");

        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);


        try{
            Transport transport = session.getTransport("smtp");
            transport.connect(mailHost, smtpSenderEmail, smtpSenderEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Mail Sent!!");

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

    //**      Helper Functions     **//

    private String readEmailFromHtml(String filePath, Map<String, String> input)
    {
        String emailMessage = readContentFromFile(filePath);
        try
        {
            Set<Entry<String, String>> entries = input.entrySet();
            for(Map.Entry<String, String> entry : entries) {
                emailMessage = emailMessage.replace(entry.getKey().trim(), entry.getValue().trim());
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return emailMessage;
    }
    //Method to read HTML file as a String
    private String readContentFromFile(String fileName)
    {
        StringBuffer htmlContentFile = new StringBuffer();

        try {
            //use buffering, reading one line at a time
            BufferedReader reader =  new BufferedReader(new FileReader(fileName));
            try {
                String line = null;
                while (( line = reader.readLine()) != null){
                    htmlContentFile.append(line);
                    htmlContentFile.append(System.getProperty("line.separator"));
                }
            }
            finally {
                reader.close();
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return htmlContentFile.toString();
    }
}


