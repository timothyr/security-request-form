package ca.sfu.delta.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {
    private int thisYear = 2017;
    private int january = 1;
    private int fifth = 5;
    private int twoPm = 14;
    private int quarter = 15;
    private int newYear = 2018;
    private int february = 2;
    private int twentySecond = 22;
    private int fivePm = 17;
    private int half = 30;
    Date testMe;

    @Before
    public void setUp() throws Exception {
        testMe = new Date(thisYear, january, fifth, twoPm, quarter);
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
    public void getHour() throws Exception {
        assertEquals(testMe.getHour(), twoPm);
    }

    @Test
    public void getMinute() throws Exception {
        assertEquals(testMe.getMinute(), quarter);
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
    public void setHour() throws Exception {
        testMe.setHour(fivePm);
        assertEquals(testMe.getHour(), fivePm);
    }

    @Test
    public void setMinute() throws Exception {
        testMe.setMinute(half);
        assertEquals(testMe.getMinute(), half);
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(testMe.toString(), "2017-01-05 14:15");
    }

}