package ca.sfu.delta.models;

import java.lang.String;
import java.util.Optional;
import java.util.ArrayList;

public class FormData {
    //Specified by user
    private String requesterName;
    private String phoneNumber;
    private String faxNumber;
    private String emailAddress;
    private String eventName;
    private String requesterID; //SFU ID or BCDL
    private String eventLocation; //String for now, until we have full list of possible locations.
    private Boolean isLicensed;
    private int numAttendees;
    private String times; //Unsure of how we want to store times, String for now.
    private ArrayList<Date> eventDates;
    private Date requestedOnDate;
    private String paymentAccountCode;
    private Boolean invoiceRequested;
    private String eventDetails;
    private String serviceRequestNumber; //Generated automatically, pre-populate.
    private String recievingSecuritySupervisor;
    private ArrayList<Guard> guards; //Things like total billable and grand total can be calculated from these
    private ArrayList<String> distributionList;
    private String preparedBy;
    private String securityRemarks;

    public FormData() {

    }

    //Specified by Authorizer
    private String authorizerName;
    private String authorizerID; //SFU ID or BCDL
    private Date authorizationDate;
    private String authorizerPhoneNumber;
    private Boolean isAuthorized;


    //Constructor takes all info that a requester can provide (optional or no)
    public FormData(ArrayList<Date> dates, String requestorName, String requesterID, Optional<String> phoneNumber,
                    Optional<String> faxNumber, String emailAddress, Date requestedOnDate, String eventName, Boolean isLicensed,
                    int numAttendees, String times, String paymentAccountCode, Boolean invoiceRequested, String eventDetails){
        this.eventDates = dates;
        this.requesterName = requestorName;
        this.phoneNumber = phoneNumber.orElse("Unspecified");
        this.faxNumber = faxNumber.orElse("Unspecified");
        this.emailAddress = emailAddress;
        this.requestedOnDate = requestedOnDate;
        this.eventName = eventName;
        this.requesterID = requesterID;
        this.isLicensed = isLicensed;
        this.numAttendees = numAttendees;
        this.times = times;
        this.paymentAccountCode = paymentAccountCode;
        this.invoiceRequested = invoiceRequested;
        this.eventDetails = eventDetails;
    }

    //Getter and Setter methods
    //Set by requester
    public ArrayList<Date> getDates() {
        return eventDates;
    }

    public void setDates(ArrayList<Date> newDates) {
        eventDates = newDates;
    }

    public void addDate(Date newDate){
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

    public Date getRequestedOnDate() {
        return requestedOnDate;
    }

    public void setRequestedOnDate(Date newDate) {
        requestedOnDate = newDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String newEventLocation) {
        eventLocation = newEventLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String newEventName) {
        eventName = newEventName;
    }

    public String getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(String newRequesterID) {
        requesterID = newRequesterID;
    }

    public void setLicensed(Boolean licensed) {
        isLicensed = licensed;
    }

    public Boolean isLicensed() {
        return isLicensed;
    }

    public int getNumAttendees() {
        return numAttendees;
    }

    public void setNumAttendees(int newNumAttendees) {
        numAttendees = newNumAttendees;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String newTimes) {
        times = newTimes;
    }

    public String getPaymentAccountCode() {
        return paymentAccountCode;
    }

    public void setPaymentAccountCode(String newPaymentAccountCode) {
        paymentAccountCode = newPaymentAccountCode;
    }

    public Boolean isInvoiceRequested() {
        return invoiceRequested;
    }

    public void setInvoiceRequested(Boolean newInvoiceRequested) {
        invoiceRequested = newInvoiceRequested;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String newEventDetails) {
        this.eventDetails = newEventDetails;
    }

    public void setAuthorizationFields(String authorizerName, String authorizerID, Date authorizationDate, String authorizerPhoneNumber){
        this.authorizerName = authorizerName;
        this.authorizerID = authorizerID;
        this.authorizationDate = authorizationDate;
        this.authorizerPhoneNumber = authorizerPhoneNumber;
        this.isAuthorized = true;
    }

    public Boolean isAuthorized(){
        return isAuthorized;
    }

    public void setServiceRequestNumber(String newNumber){
    	serviceRequestNumber = newNumber;
    }

    public String getServiceRequestNumber(){
    	return serviceRequestNumber;
    }


    public String getRecievingSecuritySupervisor(){
    	return recievingSecuritySupervisor;
    }

    public ArrayList<Guard> getGuards(){
    	return guards;
    }

    public ArrayList<String> getDistributionList(){
    	return distributionList;
    }

    public String getPreparedBy(){
    	reuturn preparedBy;
    }

    public String getSecurityRemarks(){
    	return securityRemarks;
    }

    public void setSecurityFields(String recievingSecuritySupervisor, ArrayList<Guard> guards, ArrayList<String> distributionList,
    							  String preparedBy, String securityRemarks){
    	this.recievingSecuritySupervisor = recievingSecuritySupervisor;
    	this.guards = guards;
    	this.distributionList = distributionList;
    	this.preparedBy = preparedBy;
    	this.securityRemarks = securityRemarks;

    }

}
