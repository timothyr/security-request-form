package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Optional;

public class FormDataTest {
    private String date = "2017-09-19";
    private String name = "Billy";
    private String phoneNum = "778-555-5555";
    private String faxNum = "604-555-5555";
    private String unspecifiedNum = "Unspecified";
    private String email = "Billy@sfu.ca";

    private FormData data;
    private FormData dataNoNumbers;

    @Before
    public void setUp() throws Exception {
        data = new FormData(date, name, Optional.of(phoneNum), Optional.of(faxNum), email);
        dataNoNumbers = new FormData(date, name, Optional.empty(), Optional.empty(), email);
    }

    @Test
    public void getDate() throws Exception {
        assertEquals(date, data.getDate());
    }

    @Test
    public void setDate() throws Exception {
        data.setDate("2017-09-20");
        assertEquals("2017-09-20", data.getDate());
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