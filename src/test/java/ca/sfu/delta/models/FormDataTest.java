package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

import java.util.Optional;

public class FormDataTest {
    private Date date = new Date(2017,9,19);
    private ArrayList<Date> dates = new ArrayList<Date>();
    private String name = "Billy";
    private String phoneNum = "778-555-5555";
    private String faxNum = "604-555-5555";
    private String unspecifiedNum = "Unspecified";
    private String email = "Billy@sfu.ca";

    private FormData data;
    private FormData dataNoNumbers;

    @Before
    public void setUp() throws Exception {
        dates.add(date);
        data = new FormData(dates, name, "Unspecified", Optional.of(phoneNum), Optional.of(faxNum), email, date,
                "unspecified", false, 0, "Unspecified", "Unspecified",
                false, "Unspecified");
        dataNoNumbers = new FormData(dates, name, "Unspecified", Optional.empty(), Optional.empty(), email, date,
                "unspecified", false, 0, "Unspecified", "Unspecified",
                false, "Unspecified");
    }

    @Test
    public void getDates() throws Exception {
        assertEquals(dates, data.getDates());
    }

    @Test
    public void setDates() throws Exception {
        ArrayList<Date> newDates = new ArrayList<Date>();
        newDates.add(new Date(2017,9,20));
        data.setDates(newDates);
        assertEquals(newDates, data.getDates());
    }

    @Test
    public void addDate() throws Exception {
        assertEquals(dates, data.getDates());
        data.addDate(new Date(2017,9,20));
        dates.add(new Date(2017,9,20));
        assertEquals(dates, data.getDates());
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

    }

    @Test
    public void setRequestedOnDate() throws Exception  {

    }

    @Test
    public void getEventLocation() throws Exception  {

    }

    @Test
    public void setEventLocation() throws Exception  {

    }

    @Test
    public void getEventName() throws Exception  {

    }

    @Test
    public void setEventName() throws Exception  {

    }

    @Test
    public void getRequesterID() throws Exception  {

    }

    @Test
    public void setRequesterID() throws Exception  {

    }

    @Test
    public void setLicensed() throws Exception  {

    }

    @Test
    public void isLicensed() throws Exception  {

    }

    @Test
    public void getNumAttendees() throws Exception  {

    }

    @Test
    public void setNumAttendees() throws Exception  {

    }

    @Test
    public void getTimes() throws Exception  {

    }

    @Test
    public void setTimes() throws Exception  {

    }

    @Test
    public void getPaymentAccountCode() throws Exception  {

    }

    @Test
    public void setPaymentAccountCode() throws Exception  {

    }

    @Test
    public void isInvoiceRequested() throws Exception  {

    }

    @Test
    public void setInvoiceRequested() throws Exception  {

    }

    @Test
    public void getEventDetails() throws Exception  {

    }

    @Test
    public void setEventDetails() throws Exception  {

    }

    @Test
    public void setAuthorizationFields() throws Exception {

    }

    @Test
    public void isAuthorized() throws Exception {

    }

    @Test
    public void setServiceRequestNumber() throws Exception {

    }

    @Test
    public void getServiceRequestNumber() throws Exception {

    }

    @Test
    public void getRecievingSecuritySupervisor() throws Exception {

    }

    @Test
    public void getGuards() throws Exception {

    }

    @Test
    public void getDistributionList() throws Exception {

    }

    @Test
    public void getPreparedBy() throws Exception {

    }

    @Test
    public void getSecurityRemarks() throws Exception {

    }

    @Test
    public void setSecurityFields() throws Exception {

    }

}