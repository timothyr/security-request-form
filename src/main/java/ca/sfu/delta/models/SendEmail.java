package ca.sfu.delta.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class SendEmail {
    private String firstName;
    private String lastName;
    private String emailAddress;

    public SendEmail(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.isEmpty()) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException("Could not find First Name.");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Could not find Last Name.");
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        if (emailAddress != null && !emailAddress.isEmpty()) {
            this.emailAddress = emailAddress;
        } else {
            throw new IllegalArgumentException("Could not find First Name.");
        }
    }

   // @Autowired
    private JavaMailSender sender;
    public void sendEmail(String emailAddress) throws Exception {
        //Send Notification
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //Recipient's EmailID
        helper.setTo(emailAddress);

        //TODO: Append Request ID
        helper.setText("Your request has been submitted.");

        //TODO: Add param values(Dates, time, location) from user form
        helper.setSubject("Security Request Confirmation");

        sender.send(message);
    }

    @Override
    public String toString() {
        return "SendEmail{" +
                "firstName='" + firstName + "\'" +
                ", lastName='" + lastName + "\'" +
                ", emailAddress=" + emailAddress + "}";
    }
}
