package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

import java.util.Optional;

public class FormDataTest {
    private String date = "2017-09-19";
    private ArrayList<String> dates = new ArrayList<String>();
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
        data = new FormData(dates, name, Optional.of(phoneNum), Optional.of(faxNum), email);
        dataNoNumbers = new FormData(dates, name, Optional.empty(), Optional.empty(), email);
    }

    @Test
    public void getDates() throws Exception {
        assertEquals(dates, data.getDates());
    }

    @Test
    public void setDates() throws Exception {
        ArrayList<String> newDates = new ArrayList<String>();
        newDates.add("2017-09-20");
        data.setDates(newDates);
        assertEquals(newDates, data.getDates());
    }

    @Test
    public void addDate() throws Exception {
        assertEquals(dates, data.getDates());
        data.addDate("2017-09-20");
        dates.add("2017-09-20");
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

}