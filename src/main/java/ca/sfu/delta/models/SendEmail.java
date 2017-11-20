package ca.sfu.delta.models;

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
import java.io.File;
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

	public void sendEventRequestConfirmation(String sendToEmailAddress, String personName, String trackingID, String requestURL) throws MessagingException {
		String subject = "SFU: Your Event Security Request Confirmation";

		//Swap keys in the HTML file with actual values
		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting(personName));
		input.put("trackingID", trackingID);
		input.put("requestURL", requestURL);

		String htmlEmailBody = makeEmailFromHtml("src/main/resources/static/emailUserRequestConfirmation.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	public void sendRequestAuthorizationEmail(String sendToEmailAddress, String trackingID, String requestURL) throws MessagingException {
		String subject = "SFU: Request for authorization";

		//Swap keys in the HTML file with actual values
		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting(null));
		input.put("trackingID", trackingID);
		input.put("requestURL", requestURL);

		String htmlEmailBody = makeEmailFromHtml("src/main/resources/static/emailAuthorizerRequestConfirmation.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	public void sendEventRequestRejection(String sendToEmailAddress, String personName,
	                                      String trackingID, String rejectionMessage) throws MessagingException {
		String subject = "SFU: Event Security Rejected";

		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting(personName));
		input.put("trackingID", trackingID);
		input.put("rejectionMessage", rejectionMessage);

		String htmlEmailBody = makeEmailFromHtml("src/main/resources/static/emailRequestRejected.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	public void sendEventRequestApproved(String sendToEmailAddress, String personName,
	                                     String trackingID) throws MessagingException {
		String subject = "SFU: Event Security Approved";

		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting(personName));
		input.put("trackingID", trackingID);

		String htmlEmailBody = makeEmailFromHtml("src/main/resources/static/emailRequestApproved.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	private void sendMail(String sendToEmailAddress, String subject, String htmlEmailBody) throws MessagingException {
		//Calls a function that sets SMTP properties
		setSmtpProperties();
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);

		//Calls a function which sets attributes: Sender's Email, Recipient's Email, Email Subject
		setEmailAttributes(message, sendToEmailAddress, subject);

		MimeMultipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();

		messageBodyPart.setContent(htmlEmailBody, "text/html");

		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);

		//Sends Email
		sendMail(session, message);
	}

	// Sets appropriate greeting for a person based on if the name is known or not
	private String getGreeting(String personName){
		String greeting;
		if (personName == null || personName.isEmpty()) {
			greeting = "Hello!\n\n";
		}
		else {
			greeting = "Hello " + personName + ",\n\n";
		}
		return greeting;
	}

	//Sets SMTP Properties
	Properties properties;
	private Properties setSmtpProperties(){
		//Mail Properties
		properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", mailHost);
		properties.put("mail.smtp.user", smtpSenderEmail);
		properties.put("mail.smtp.password", smtpSenderEmailPassword);
		properties.put("mail.smtp.port", smtpPort);
		properties.put("mail.smtp.auth", IsAuthorized);

		return properties;
	}

	private void setEmailAttributes(MimeMessage message, String sendToEmailAddress, String subject){
		try {
			message.setFrom(new InternetAddress(smtpSenderEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmailAddress));
			message.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void sendMail(Session session, MimeMessage message){
		Transport transport = null;
		try {
			transport = session.getTransport("smtp");
			transport.connect(mailHost, smtpSenderEmail, smtpSenderEmailPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

    //**      Helper Functions     **//
    private String makeEmailFromHtml(String filePathName, Map<String, String> input)
    {
	    //Get file path of HTML file to be embedded in the email
	    String filePath;
	    File resourcesDirectory = new File(filePathName);
	    filePath = resourcesDirectory.getPath();

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


