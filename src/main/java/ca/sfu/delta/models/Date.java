package ca.sfu.delta.models;

import java.lang.String;
import org.springframework.util.StringUtils;

public class Date {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public Date(int yearNum, int monthNum, int dayNum, int hourNum, int minuteNum){
       year = yearNum;
       month = monthNum;
       day = dayNum;
       hour = hourNum;
       minute = minuteNum;
    }

    //Getters and setters
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        String yearReturn = String.format("%04d", year);
        String monthReturn = String.format("%02d", month);
        String dayReturn = String.format("%02d", day);
        String hourReturn = String.format("%02d", hour);
        String minuteReturn = String.format("%02d", minute);
        return yearReturn + "-" + monthReturn + "-" + dayReturn + " " + hourReturn + ":" + minuteReturn;
    }
}
