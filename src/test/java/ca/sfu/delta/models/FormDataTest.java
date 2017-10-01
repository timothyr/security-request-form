package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

import java.util.Optional;

public class FormDataTest {
	//Todo: re-enable once dates are implemented in the front and back end
//    private Date date = new Date(2017,9,19);
//    private ArrayList<Date> dates = new ArrayList<Date>();
    private String eventDate = "August 19, 2017";
    private String requestDate = "January 1, 2017";
    private String department = "Computer Science";
    private String name = "Billy";
    private String phoneNum = "778-555-5555";
    private String faxNum = "604-555-5555";
    private String unspecifiedNum = "Unspecified";
    private String email = "Billy@sfu.ca";

    private FormData data;
    private FormData dataNoNumbers;

    @Before
    public void setUp() throws Exception {
//        dates.add(date);
        data = new FormData(department, eventDate, name, "Unspecified", Optional.of(phoneNum), Optional.of(faxNum), email, requestDate,
                "unspecified", false, 0, "Unspecified", "Unspecified",
                false, "Unspecified");
        dataNoNumbers = new FormData(department, eventDate, name, "Unspecified", Optional.empty(), Optional.empty(), email, requestDate,
                "unspecified", false, 0, "Unspecified", "Unspecified",
                false, "Unspecified");
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

}