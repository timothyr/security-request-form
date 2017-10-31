package ca.sfu.delta.models;

import javax.persistence.*;
import java.lang.String;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


@Entity
public class FormData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    // TODO: ensure all fields are saved in csv

    //Specified by user
    private String department;
    private String requesterName;
    private String phoneNumber;
    private String faxNumber;
    private String emailAddress;
    private String eventName;
    private String requesterID; //SFU ID
    private String eventLocation; //String for now, until we have full list of possible locations.
    private Boolean isLicensed;
    private int numAttendees;
    private String authorizerEmailAddress;
    private String times; //Unsure of how we want to store times, String for now.
	//Todo: these will need to be changed back to arrays of dates once the front end supports dates
    private String eventDates;
    private String requestedOnDate;

    private String paymentAccountCode;
    private Boolean invoiceRequested;
    private String eventDetails;
    private String serviceRequestNumber; //Generated automatically, pre-populate.
    private String recievingSecuritySupervisor;

    @ElementCollection
    private List<Guard> guards; //Things like total billable and grand total can be calculated from these
    @ElementCollection
    private List<String> distributionList;
    private String preparedBy;
    private String securityRemarks;
    private String requestStatus;

	private String requestID;

    public FormData() {

    }

    //Specified by Authorizer
    private String authorizerName;
    private String authorizerID; //SFU ID
	//Todo: this will need to be changed back to a date once the front end supports dates
    private String authorizationDate;
    private String authorizerPhoneNumber;
    private Boolean isAuthorized;


    //Constructor takes all info that a requester can provide (optional or no)
    public FormData(
            String department,
            String dates,
            String requestorName,
            String requesterID,
            String phoneNumber,
            String faxNumber,
            String emailAddress,
            String requestedOnDate,
            String eventName,
            Boolean isLicensed,
            Integer numAttendees,
            String times,
            String paymentAccountCode,
            Boolean invoiceRequested,
            String eventDetails

    ){
        this.department = department;
    	this.eventDates = dates;
        this.requesterName = requestorName;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
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

    public Long getId() {
        return this.id;
    }

	public void setId(long id) {
		this.id = id;
	}

    //Getter and Setter methods
    //Set by requester
    public String getEventDates() {
        return eventDates;
    }

    public void setEventDates(String newDates) {
        eventDates = newDates;
    }

    public String getDepartment() {
    	return department;
    }

    public void setDepartment(String newDepartment) {
    	department = newDepartment;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String newName) {
        requesterName = newName;
    }

    public String getPhoneNumber() {
		if (phoneNumber == null) {
			return "";
		} else {
        	return phoneNumber;
		}
    }

    public void setPhoneNumber(String newNumber) {
        phoneNumber = newNumber;
    }

    public String getFaxNumber() {
		if (faxNumber == null) {
			return "";
		} else {
			return faxNumber;
		}
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

    public String getRequestedOnDate() {
        return requestedOnDate;
    }

    public void setRequestedOnDate(String newDate) {
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

	public Boolean getIsLicensed() {
		return isLicensed;
	}

	public void setIsLicensed(Boolean licensed) {
        isLicensed = licensed;
    }

    public int getNumAttendees() {
        return numAttendees;
    }

    public void setNumAttendees(Integer newNumAttendees) {
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

    public Boolean getInvoiceRequested() {
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

    public String getAuthorizerID() {
    	return authorizerID;
    }

    public void setAuthorizerID(String newAuthorizerID) {
    	authorizerID = newAuthorizerID;
    }

	public String getAuthorizerPhoneNumber() {
		return authorizerPhoneNumber;
	}

	public void setAuthorizerPhoneNumber(String newAuthorizerPhoneNumber) {
		authorizerPhoneNumber = newAuthorizerPhoneNumber;
	}

	public String getAuthorizerName() {
    	return authorizerName;
	}

	public void setAuthorizerName(String newAuthorizerName) {
		authorizerName = newAuthorizerName;
	}

	public String getAuthorizationDate() {
    	return authorizationDate;
	}

	public void setAuthorizationDate(String newAuthorizationDate) {
    	authorizationDate = newAuthorizationDate;
	}

	public String getAuthorizerEmailAddress() { return authorizerEmailAddress;}

	public void setAuthorizerEmailAddress(String newAuthorizerEmailAddress) {authorizerEmailAddress = newAuthorizerEmailAddress;}

    public void setAuthorizationFields(
            String authorizerName,
            String authorizerID,
            String authorizationDate,
            String authorizerPhoneNumber,
            String authorizerEmailAddress
    ){
        this.authorizerName = authorizerName;
        this.authorizerID = authorizerID;
        this.authorizationDate = authorizationDate;
        this.authorizerPhoneNumber = authorizerPhoneNumber;
        this.isAuthorized = true;
        this.authorizerEmailAddress = authorizerEmailAddress;
    }

    public Boolean getIsAuthorized(){
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

    public List<Guard> getGuards(){
    	return guards;
    }

    public List<String> getDistributionList(){
    	return distributionList;
    }

    public String getPreparedBy(){
    	return preparedBy;
    }

    public String getSecurityRemarks(){
    	return securityRemarks;
    }

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

    public void setSecurityFields(
            String recievingSecuritySupervisor,
            List<Guard> guards,
            List<String> distributionList,
            String preparedBy,
            String securityRemarks
    ){
    	this.recievingSecuritySupervisor = recievingSecuritySupervisor;
    	this.guards = guards;
    	this.distributionList = distributionList;
    	this.preparedBy = preparedBy;
    	this.securityRemarks = securityRemarks;
    }

    public Map<String, Object> jsonify() {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field f : getClass().getDeclaredFields()) {
            try {
                map.put(f.getName(), f.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return "FormData{" +
                "department='" + department + '\'' +
                ", requesterName='" + requesterName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", faxNumber='" + faxNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", eventName='" + eventName + '\'' +
                ", requesterID='" + requesterID + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", isLicensed=" + isLicensed +
                ", numAttendees=" + numAttendees +
                ", times='" + times + '\'' +
                ", eventDates='" + eventDates + '\'' +
                ", requestedOnDate='" + requestedOnDate + '\'' +
                ", paymentAccountCode='" + paymentAccountCode + '\'' +
                ", invoiceRequested=" + invoiceRequested +
                ", eventDetails='" + eventDetails + '\'' +
                ", serviceRequestNumber='" + serviceRequestNumber + '\'' +
                ", recievingSecuritySupervisor='" + recievingSecuritySupervisor + '\'' +
                ", guards=" + guards +
                ", distributionList=" + distributionList +
                ", preparedBy='" + preparedBy + '\'' +
                ", securityRemarks='" + securityRemarks + '\'' +
				", requestStatus='" + requestStatus + '\'' +
                ", authorizerName='" + authorizerName + '\'' +
                ", authorizerID='" + authorizerID + '\'' +
                ", authorizationDate='" + authorizationDate + '\'' +
                ", authorizerPhoneNumber='" + authorizerPhoneNumber + '\'' +
                ", isAuthorized=" + isAuthorized +
		        ", requestID=" + requestID + '\'' +
                '}';
    }

    /**
     * Writes the contents of this formdata to a csv file specified by fileName. fileName should
     * probably be the requestID of the form, and NOT user input. If the file being written to already exists
     * (in the case of writing multiple forms to a csv), a new row is appended to it; Otherwise the file is created.
     * @param fileName - If this does not have the .csv extension it will be added.
     */
    public void saveAsCSV(String fileName) {
        //Make sure fileName ends with .csv
        if (!fileName.endsWith(".csv")) {
            fileName = fileName + ".csv";
        }
        File csv = new File(fileName);
        //Creating csvWriter below creates the file if not present, so check existance now.
        boolean newFile = true;
        if (csv.exists()) newFile = false;
        try {
            //"true" in this case is specifying to open the file in append mode
            BufferedWriter csvWriter = new BufferedWriter(new FileWriter(fileName, true));
            //Only add first row with field names if file doesn't exist yet
            if (newFile) {
                String firstRow = "Request ID" + ", " +
                                  "Department" + ", " +
                                  "Requester Name" + ", " +
                                  "Phone Number" + ", " +
                                  "Fax Number" + ", " +
                                  "Email Address" + ", " +
                                  "Event Name" + ", " +
                                  "Requester ID" + ", " +
                                  "Event Location" + ", " +
                                  "Licensed?" + ", " +
                                  "Number of Attendees" + ", " +
                                  "Time(s)" + ", " +
                                  "Event Dates" + ", " +
                                  "Requested On Date" + ", " +
                                  "Payment Account Code" + ", " +
                                  "Invoice Requested?" + ", " +
                                  "Event Details" + ", " +
                                  "Service Request Number" + ", " +
                                  "Recieving Security Supervisor" + ", " +
                                  "Prepared By" + ", " +
                                  "Security Remarks" + ", " +
                                  "Authorizer Name" + ", " +
                                  "Authorizer ID" + ", " +
                                  "Authorization Date" + ", " +
                                  "Authorizer Phone Number" + ", " +
                                  "Authorized?" + "\n";
                csvWriter.append(firstRow);
            }
            //Append fields to csv, strip out commas from places they could be present
            String nextRow = requestID + ", " +
                             department + ", " +
                             requesterName + ", " +
                             phoneNumber + ", " +
                             faxNumber + ", " +
                             emailAddress + ", " +
                             eventName + ", " +
                             requesterID + ", " +
                             eventLocation + ", " +
                             isLicensed + ", " +
                             numAttendees + ", " +
                             times + ", " +
                             eventDates + ", " +
                             requestedOnDate + ", " +
                             paymentAccountCode + ", " +
                             invoiceRequested + ", " +
                             eventDetails.replace(",", "") + ", " +
                             serviceRequestNumber + ", " +
                             recievingSecuritySupervisor + ", " +
                             preparedBy + ", " +
                             securityRemarks.replace(",", "") + ", " +
                             authorizerName + ", " +
                             authorizerID + ", " +
                             authorizationDate + ", " +
                             authorizerPhoneNumber + ", " +
                             isAuthorized + "\n";

            //Do some prettying up
            nextRow = nextRow.replace("null", "Not specified");
            nextRow = nextRow.replace("false", "No");
            nextRow = nextRow.replace("true", "Yes");
            csvWriter.append(nextRow);
            csvWriter.close();
        } catch (IOException e)
        {
            System.err.println("Error writing to csv:  " + e.getMessage());
        }
    }
}
