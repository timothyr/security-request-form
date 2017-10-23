package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class FormDataTest {
	//Todo: re-enable once dates are implemented in the front and back end
//    private Date date = new Date(2017,9,19);
//    private ArrayList<Date> dates = new ArrayList<Date>();
    private String eventDate = "August 19, 2017";
    private String department = "Computer Science";
    private String name = "Billy";
    private String phoneNum = "778-555-5555";
    private String faxNum = "604-555-5555";
    private String unspecifiedNum = "";
    private String email = "Billy@sfu.ca";
//    private Date requestedOnDate = new Date(2017,9,30);
	private String requestedOnDate = "September 30, 2017";
    private String eventLocation = "BBY AQ3019";
    private String eventName = "Networking Night";
    private String requesterID = "301248474";
    private boolean licensed = false;
    private int numAttendees = 4;
    private String times = "14:30:00";
    private String paymentAccountCode = "ASD12345";
    private boolean invoiceRequested = false;
    private String eventDetails = "details of the event.";

    private String serviceRequestNumber = "99999999";
    private String recievingSecuritySupervisor = "Aaron Judge";
    private ArrayList<Guard> guards = new ArrayList<Guard>();
    private ArrayList<String> distributionList = new ArrayList<String>();
    private String preparedBy = "SFU Security";
    private String securityRemarks = "Security remarks";

    private FormData data;
    private FormData dataNoNumbers;

    @Before
    public void setUp() throws Exception {
//        dates.add(date);
        data = new FormData(department, eventDate, name, requesterID, phoneNum, faxNum, email, requestedOnDate,
                eventName, licensed, numAttendees, times, paymentAccountCode,
                invoiceRequested, eventDetails);
        data.setEventLocation(eventLocation);
        data.setServiceRequestNumber(serviceRequestNumber);
        data.setSecurityFields(recievingSecuritySupervisor,guards,distributionList,preparedBy,securityRemarks);

        dataNoNumbers = new FormData(department, eventDate, name, requesterID, null, null, email, requestedOnDate,
                eventName, licensed, numAttendees, times, paymentAccountCode,
                invoiceRequested, eventDetails);
        dataNoNumbers.setEventLocation(eventLocation);
        dataNoNumbers.setServiceRequestNumber(unspecifiedNum);
        dataNoNumbers.setSecurityFields(recievingSecuritySupervisor,guards,distributionList,preparedBy,securityRemarks);
    }

    @Test
    public void getDates() throws Exception {
        assertEquals(eventDate, data.getEventDates());
    }

    @Test
    public void setDates() throws Exception {
//        ArrayList<Date> newDates = new ArrayList<Date>();
//        newDates.add(new Date(2017,9,20));
	    String newEventDate = "December 31, 2017";
        data.setEventDates(newEventDate);
        assertEquals(newEventDate, data.getEventDates());
    }

	//Todo: re-enable once dates are implemented in the front and back end
//    @Test
//    public void addDate() throws Exception {
//        assertEquals(dates, data.getDates());
//        data.addDate(new Date(2017,9,20));
//        dates.add(new Date(2017,9,20));
//        assertEquals(dates, data.getDates());
//    }

	@Test
	public void getDepartment() throws Exception {
		assertEquals(department, data.getDepartment());
	}

	@Test
	public void setDepartment() throws Exception {
    	department = "Arts";
		data.setDepartment(department);
		assertEquals(department, data.getDepartment());
	}

    @Test
    public void getRequesterName() throws Exception {
        assertEquals(name, data.getRequesterName());
    }

    @Test
    public void setRequesterName() throws Exception {
        data.setRequesterName("Brian");
        assertEquals("Brian", data.getRequesterName());
    }

    @Test
    public void getPhoneNumber() throws Exception {
        assertEquals(phoneNum, data.getPhoneNumber());
        assertEquals(unspecifiedNum, dataNoNumbers.getPhoneNumber());
    }

    @Test
    public void setPhoneNumber() throws Exception {
        data.setPhoneNumber("555-555-5555");
        assertEquals("555-555-5555", data.getPhoneNumber());
    }

    @Test
    public void getFaxNumber() throws Exception {
        assertEquals(faxNum, data.getFaxNumber());
        assertEquals(unspecifiedNum, dataNoNumbers.getFaxNumber());
    }

    @Test
    public void setFaxNumber() throws Exception {
        data.setFaxNumber("555-555-5555");
        assertEquals("555-555-5555", data.getFaxNumber());
    }

    @Test
    public void getEmailAddress() throws Exception {
        assertEquals(email, data.getEmailAddress());
    }

    @Test
    public void setEmailAddress() throws Exception {
        data.setEmailAddress("Brian@sfu.ca");
        assertEquals("Brian@sfu.ca", data.getEmailAddress());

    }


    //More Test functions.-ska158
    @Test
    public void getRequestedOnDate() throws Exception  {
        assertEquals(requestedOnDate, data.getRequestedOnDate());
    }

    @Test
    public void setRequestedOnDate() throws Exception  {
//        Date newDate = new Date(2017,9,22);
	    String newDate = "September 22, 2017";
        data.setRequestedOnDate(newDate);
        assertEquals(newDate, data.getRequestedOnDate());
    }

    @Test
    public void getEventLocation() throws Exception  {
        assertEquals(eventLocation, data.getEventLocation());
    }

    @Test
    public void setEventLocation() throws Exception  {
        data.setEventLocation("SUR 5200");
        assertEquals("SUR 5200", data.getEventLocation());
    }

    @Test
    public void getEventName() throws Exception  {
        assertEquals(eventName, data.getEventName());
    }

    @Test
    public void setEventName() throws Exception  {
        data.setEventName("formal event one");
        assertEquals("formal event one", data.getEventName());
    }

    @Test
    public void getRequesterID() throws Exception  {
        assertEquals(requesterID, data.getRequesterID());
    }

    @Test
    public void setRequesterID() throws Exception  {
        data.setRequesterID("30123456");
        assertEquals("30123456",data.getRequesterID());
    }

    @Test
    public void setLicensed() throws Exception  {
        data.setIsLicensed(true);
        assertEquals(true, data.getIsLicensed());
    }

    @Test
    public void isLicensed() throws Exception  {
        assertEquals(licensed, data.getIsLicensed());
    }

    @Test
    public void getNumAttendees() throws Exception  {
        assertEquals(numAttendees, data.getNumAttendees());
    }

    @Test
    public void setNumAttendees() throws Exception  {
        data.setNumAttendees(10);
        assertEquals(10, data.getNumAttendees());
    }

    @Test
    public void getTimes() throws Exception  {
        assertEquals(times, data.getTimes());
    }

    @Test
    public void setTimes() throws Exception  {
        data.setTimes("19:20:00");
        assertEquals("19:20:00", data.getTimes());
    }

    @Test
    public void getPaymentAccountCode() throws Exception  {
        assertEquals(paymentAccountCode, data.getPaymentAccountCode());
    }

    @Test
    public void setPaymentAccountCode() throws Exception  {
        data.setPaymentAccountCode("QWE45678");
        assertEquals("QWE45678",data.getPaymentAccountCode());
    }

    @Test
    public void isInvoiceRequested() throws Exception  {
        assertEquals(invoiceRequested, data.getInvoiceRequested());
    }

    @Test
    public void setInvoiceRequested() throws Exception  {
        data.setInvoiceRequested(true);
        assertEquals(true,data.getInvoiceRequested());
    }

    @Test
    public void getEventDetails() throws Exception  {
        assertEquals(eventDetails,data.getEventDetails());
    }

    @Test
    public void setEventDetails() throws Exception  {
        data.setEventDetails("Following details are..");
        assertEquals("Following details are..",data.getEventDetails());
    }

    @Test
    public void setAuthorizationFields() throws Exception {
//        Date newDate = new Date(2017,9,30);
	    String newDate = "September 30, 2017";
        data.setAuthorizationFields("Kelly Jensen","6666666",newDate,"778-777-7777");
        assertEquals("Kelly Jensen",data.getAuthorizerName());
        assertEquals("6666666",data.getAuthorizerID());
        assertEquals(newDate,data.getAuthorizationDate());
        assertEquals("778-777-7777",data.getAuthorizerPhoneNumber());
        assertEquals(true,data.getIsAuthorized());
    }

    @Test
    public void setServiceRequestNumber() throws Exception {
        data.setServiceRequestNumber("00000000");
        assertEquals("00000000",data.getServiceRequestNumber());
    }

    @Test
    public void getServiceRequestNumber() throws Exception {
        assertEquals(serviceRequestNumber, data.getServiceRequestNumber());
        assertEquals(unspecifiedNum,dataNoNumbers.getServiceRequestNumber());
    }

    @Test
    public void getRecievingSecuritySupervisor() throws Exception {
        assertEquals(recievingSecuritySupervisor,data.getRecievingSecuritySupervisor());
    }

    @Test
    public void getGuards() throws Exception {
        assertEquals(guards,data.getGuards());
    }

    @Test
    public void getDistributionList() throws Exception {
        assertEquals(distributionList,data.getDistributionList());
    }

    @Test
    public void getPreparedBy() throws Exception {
        assertEquals(preparedBy,data.getPreparedBy());
    }

    @Test
    public void getSecurityRemarks() throws Exception {
        assertEquals(securityRemarks,data.getSecurityRemarks());
    }

    @Test
    public void setSecurityFields() throws Exception {
        ArrayList<Guard> newGuardList = new ArrayList<Guard>();
        ArrayList<String> newDistributionList = new ArrayList<String>();
        data.setSecurityFields("Justin Turner",newGuardList,newDistributionList,"Parladin","security remark");
        assertEquals("Justin Turner",data.getRecievingSecuritySupervisor());
        assertEquals(newGuardList,data.getGuards());
        assertEquals(newDistributionList,data.getDistributionList());
        assertEquals("Parladin",data.getPreparedBy());
        assertEquals("security remark",data.getSecurityRemarks());
    }

    @Test
    public void getRequestId() throws Exception {
        // The requestID should be null before one is requested
        assertEquals(null, data.getRequestID());
    }

    @Test
    public void setRequestID() throws Exception {
        String newRequestID = "17-0001";
        data.setRequestID(newRequestID);
        assertEquals(newRequestID, data.getRequestID());
    }
}