package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {
    private int thisYear = 2017;
    private int january = 1;
    private int fifth = 5;
    private int newYear = 2018;
    private int february = 2;
    private int twentySecond = 22;
    Date testMe;

    @Before
    public void setUp() throws Exception {
        testMe = new Date(thisYear, january, fifth);
    }

    @Test
    public void getDay() throws Exception {
        assertEquals(testMe.getDay(), fifth);
    }

    @Test
    public void getMonth() throws Exception {
        assertEquals(testMe.getMonth(), january);
    }

    @Test
    public void getYear() throws Exception {
        assertEquals(testMe.getYear(), thisYear);
    }

    @Test
    public void setDay() throws Exception {
        testMe.setDay(twentySecond);
        assertEquals(testMe.getDay(), twentySecond);
    }

    @Test
    public void setMonth() throws Exception {
        testMe.setMonth(february);
        assertEquals(testMe.getMonth(), february);
    }

    @Test
    public void setYear() throws Exception {
        testMe.setYear(newYear);
        assertEquals(testMe.getYear(), newYear);
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(testMe.toString(), "2017-01-05");
    }

}