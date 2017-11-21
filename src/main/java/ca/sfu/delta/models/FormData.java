package ca.sfu.delta.models;

import javax.persistence.*;
import java.lang.String;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.File; 
import java.nio.file.Files; 
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream; 
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
    private String times; //Unsure of how we want to store times, String for now.
	//Todo: these will need to be changed back to arrays of dates once the front end supports dates
    private String eventDates;
    private String requestedOnDate;

    private String paymentAccountCode;
    private Boolean invoiceRequested;
    private String eventDetails = null;
    private String serviceRequestNumber; //Generated automatically, pre-populate.
    private String recievingSecuritySupervisor;

    @ElementCollection
    private List<Guard> guards; //Things like total billable and grand total can be calculated from these
    @ElementCollection
    private List<String> distributionList;
    private String preparedBy;
    private String securityRemarks = null;
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
    private String authorizerEmailAddress;


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

	public String getAuthorizerEmailAddress() {
                return authorizerEmailAddress;
    }

    public void setAuthorizerEmailAddress(String newAuthorizerEmailAddress){
                authorizerEmailAddress = newAuthorizerEmailAddress;
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
        this.authorizerEmailAddress = authorizerEmailAddress;
        this.isAuthorized = true;
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

        //for (String s : map.keySet())
        //	System.out.println(s +"->"+map.get(s));

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
    public String getAsCSV(Boolean needHeader) {
            StringWriter csvWriter = new StringWriter();
            //Only add first row with field names if we need the header (in case that we are creating a new CSV
            //with this string)
            if (needHeader) {
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
            //Avoid null pointer exceptions if certain fields are blank

            if(eventDetails == null){
                eventDetails = new String("null");
            }
            if(securityRemarks == null){
                securityRemarks = new String("null");
            }

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
            return csvWriter.toString();
    }


    private void addLineToPDF(PDPageContentStream contentStream, int xPosition, int yPosition, String lineText) throws IOException {
            contentStream.beginText(); 
            contentStream.setFont(PDType1Font.HELVETICA, 10.5f);
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(lineText);  
            contentStream.endText();
    }

        private void addLineToPDFBold(PDPageContentStream contentStream, int xPosition, int yPosition, String lineText) throws IOException {
            contentStream.beginText(); 
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10.5f);
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(lineText);  
            contentStream.endText();
    }


    public byte[] generateInvoicePDF() {
        PDDocument document;
        File file;
        try {
            //First load the PDF template to fill it
            file = new File("src/main/resources/static/img/Invoice_Template.pdf");
            document = PDDocument.load(file);
            System.out.println("Document loaded \n");

        } catch (IOException e)
        {
            System.out.println("Error: cannot open PDF template.\n");
            return null;
        }

        try {
            //Retrieving the pages of the document 
            PDPage page = document.getPage(0);
            //Append to new template rather than wiping old data
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            //Add revelant information
            addLineToPDF(contentStream, 145, 635, this.getRequesterName());
            addLineToPDF(contentStream, 145, 620, "This is where we'd put the rest of the information from the");
            addLineToPDF(contentStream, 145, 610, "template, if we had it.");
            addLineToPDF(contentStream, 145, 600, "What do you think, Jason? Is just the name okay,");
            addLineToPDF(contentStream, 145, 590, "or should the app ask you for this info when you press generate?");

            //Generate Invoice number and date
            DateFormat workOrderNumberDateFormat = new SimpleDateFormat("yyyy");
            DateFormat invoiceDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            Date rightNow = new Date();

            String invoiceNumber = this.getRequestID() + workOrderNumberDateFormat.format(rightNow) + "_019X";
            addLineToPDF(contentStream, 444, 624, invoiceNumber);
            addLineToPDF(contentStream, 454, 610, invoiceDateFormat.format(rightNow));

            //Middle three fields (Type of Service, Requested by, Contact Number)
            addLineToPDF(contentStream, 255, 504, "Event Security");
            addLineToPDF(contentStream, 255, 490, this.getRequesterName());
            addLineToPDF(contentStream, 255, 475, this.getPhoneNumber());

            //Details and total invoice amount
            double totalGuardCost = 0.0;
            for(Guard g : this.getGuards()) {
                totalGuardCost += g.calculateTotalPay().doubleValue();
            }

            addLineToPDF(contentStream, 145, 400, "Total cost of guard services");
            addLineToPDF(contentStream, 500, 400, "$" + totalGuardCost);
            addLineToPDFBold(contentStream, 500, 371, "$" + totalGuardCost);

            //Close the content stream
            contentStream.close();

            //Turns out there isn't a nice way to convert a PDDocument to something usable, so save as PDF and read back.
            document.save(new File("Invoice.pdf"));
            document.close();
            byte[] fileAsBytes = Files.readAllBytes(new File("Invoice.pdf").toPath());

            return fileAsBytes;

        } catch (IOException e) {
            System.out.println("Error creating pdf file.\n");
            return null;
        }
    }
}
