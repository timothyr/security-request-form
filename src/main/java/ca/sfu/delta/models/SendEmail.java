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

    public void sendEventRequestConfirmation(String sendToEmailAddress, String personName, String trackingID, String requestURL) throws MessagingException {
        String subject = "SFU: Your Event Security Request Confirmation";
        String emailBody = getGreeting(personName) + " Your request has been sent to SFU security.\n" +
		        " You will be contacted shortly. Your request ID is: " + trackingID + ".\n\n" +
		        " You may view your request at " + requestURL;
	    sendMail(sendToEmailAddress, subject, emailBody);
    }

    public void sendRequestAuthorizationEmail(String sendToEmailAddress, String trackingID, String requestURL) throws MessagingException {
        String subject = "SFU: Request for authorization";
        String emailBody = "Hello. You have been requested to authorize request: " + trackingID +
			    " You may view the request at: " + requestURL;
        sendMail(sendToEmailAddress, subject, emailBody);
    }

    public void sendEventRequestRejection(String sendToEmailAddress, String personName,
                                          String trackingID, String rejectionMessage) throws MessagingException {
		String subject = "SFU: Event Security Rejected";
		String emailBody = getGreeting(personName) + "Your event security request (ID " + trackingID + ") has been rejected.";
		if (rejectionMessage != null && !rejectionMessage.isEmpty()) {
			emailBody = emailBody + "\nReason:\n" + rejectionMessage;
		}
		sendMail(sendToEmailAddress, subject, emailBody);
    }

	public void sendEventRequestApproved(String sendToEmailAddress, String personName,
	                                      String trackingID) throws MessagingException {
		String subject = "SFU: Event Security Approved";
		String emailBody = getGreeting(personName) + "Your event security request (ID " + trackingID + ") has been approved.";
		sendMail(sendToEmailAddress, subject, emailBody);
	}

    private String getGreeting(String personName) {
    	if (personName == null) {
    		return "Hello.\n\n";
	    }
	    else {
    		return "Hello " + personName + ".\n\n";
	    }
    }

    private void sendMail(String sendToEmailAddress, String subject, String emailBody) throws MessagingException {
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);

	    helper.setTo(sendToEmailAddress);
	    helper.setSubject(subject);
	    helper.setText(emailBody);

	    try{
		    javaMailSender.send(message);
	    }
	    catch (MailException ex){
		    System.err.println(ex.getMessage());
	    }
    }
}
