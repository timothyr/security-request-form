package ca.sfu.delta.models;

import java.lang.String;
import java.util.Optional;

public class FormData {
    //Date of the form yyyy-mm-dd for the time being.
    private String eventDate;
    private String requesterName;
    private String phoneNumber;
    private String faxNumber;
    private String emailAddress;

    public FormData(String date, String name, Optional<String> phoneNum, Optional<String> faxNum, String email){
        eventDate = date;
        requesterName = name;
        phoneNumber = phoneNum.orElse("Unspecified");
        faxNumber = faxNum.orElse("Unspecified");
        emailAddress = email;
    }

    //Getter and Setter methods
    public String getDate() {
        return eventDate;
    }

    public void setDate(String newDate) {
        eventDate = newDate;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String newName) {
        requesterName = newName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String newNumber) {
        phoneNumber = newNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String newNumber) {
        faxNumber = newNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String newEmail) {
        emailAddress = newEmail;
    }
}
