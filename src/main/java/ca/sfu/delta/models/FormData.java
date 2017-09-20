package ca.sfu.delta.models;

import java.lang.String;
import java.util.Optional;
import java.util.ArrayList;

public class FormData {
    //Date of the form yyyy-mm-dd for the time being.
    private ArrayList<String> eventDates;
    private String requesterName;
    private String phoneNumber;
    private String faxNumber;
    private String emailAddress;

    public FormData(ArrayList<String> dates, String name, Optional<String> phoneNum, Optional<String> faxNum, String email){
        eventDates = dates;
        requesterName = name;
        phoneNumber = phoneNum.orElse("Unspecified");
        faxNumber = faxNum.orElse("Unspecified");
        emailAddress = email;
    }

    //Getter and Setter methods
    public ArrayList<String> getDates() {
        return eventDates;
    }

    public void setDates(ArrayList<String> newDates) {
        eventDates = newDates;
    }

    public void addDate(String newDate){
    	eventDates.add(newDate);
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
