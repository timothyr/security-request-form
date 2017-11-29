package ca.sfu.delta.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

@Service
public class SendEmail {

    private JavaMailSender javaMailSender;

	@Autowired
	private ServletContext servletContext;

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.username}")
    private String smtpSenderEmail;
    @Value("${spring.mail.password}")
    private String smtpSenderEmailPassword;
    @Value("${spring.mail.port}")
    private String smtpPort;
    @Value("{spring.mail.properties.mail.smtp.starttls.enable}")
    private String IsTtlsEnabled;
    @Value ("{spring.mail.properties.mail.transport.protocol}")
	private String transportProtocol;


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

		String htmlEmailBody = makeEmailFromHtml("emailUserRequestConfirmation.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	public void sendRequestAuthorizationEmail(String sendToEmailAddress, String trackingID, String requestURL) throws MessagingException {
		String subject = "SFU: Request for authorization";

		//Swap keys in the HTML file with actual values
		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting(null));
		input.put("trackingID", trackingID);
		input.put("requestURL", requestURL);


		String htmlEmailBody = makeEmailFromHtml("emailAuthorizerRequestConfirmation.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	public void sendEventRequestRejection(String sendToEmailAddress, String personName,
	                                      String trackingID, String rejectionMessage) throws MessagingException {
		String subject = "SFU: Event Security Rejected";

		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting(personName));
		input.put("trackingID", trackingID);
		input.put("rejectionMessage", rejectionMessage);

		String htmlEmailBody = makeEmailFromHtml("emailRequestRejected.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	public void sendDistributionEmail(String sendToEmailAddress,
	                                  String eventName, String requestURL) throws MessagingException {
		String subject = "SFU Security: Notification of event";

		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting("")); //These will go out in bulk and we won't always know name
		input.put("eventName", eventName);
		input.put("requestURL", requestURL);

		String htmlEmailBody = makeEmailFromHtml("emailDistribution.html", input);

		sendMail(sendToEmailAddress, subject, htmlEmailBody);
	}

	public void sendEventRequestApproved(String sendToEmailAddress, String personName,
	                                     String trackingID) throws MessagingException {
		String subject = "SFU: Event Security Approved";

		Map<String, String> input = new HashMap<>();
		input.put("Greeting", getGreeting(personName));
		input.put("trackingID", trackingID);

		String htmlEmailBody = makeEmailFromHtml("emailRequestApproved.html", input);

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
		properties.put("mail.smtp.starttls.enable", "false");
		properties.put("mail.smtp.ssl.enable", IsTtlsEnabled);
		properties.put("mail.smtp.host", mailHost);
		properties.put("mail.smtp.port", smtpPort);
		properties.put("mail.transport.protocol", transportProtocol);
		properties.put("mail.smtp.user", smtpSenderEmail);
		properties.put("mail.smtp.password", smtpSenderEmailPassword);

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
			transport = session.getTransport("smtps");
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
    private String makeEmailFromHtml(String fileName, Map<String, String> input)
    {
		String emailMessage = readContentFromFile(fileName);
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
		String emailMessage = "";
		try {
			InputStream stream = servletContext.getResourceAsStream("/WEB-INF/"+fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				emailMessage += line;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return emailMessage;
    }
}


