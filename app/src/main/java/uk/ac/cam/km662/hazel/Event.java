package uk.ac.cam.km662.hazel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class Event {
    private String id, name;
    double latitude, longitude;
    private Date startDate = null;

    protected Event(String id, String name, double latitude, double longitude, String dateInString){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        parseDate(dateInString);
    }

    protected String getID(){
        return this.id;
    }

    protected String getName(){
        return this.name;
    }

    protected double getLongitude(){
        return this.longitude;
    }

    protected double getLatitude(){
        return this.latitude;
    }

    protected void parseDate(String dateInString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            startDate = sdf.parse(dateInString);
        } catch (ParseException e) {
            startDate = null;
        }
    }

    protected boolean isValidEvent(){
        Date now = new Date();
        return now.before(startDate);
    }

    protected boolean isValidEvent(int y, int m, int d){
        Calendar date = getInstance();
        date.setTime(startDate);
        return y == date.get(YEAR) && m == date.get(MONTH) && d == date.get(DAY_OF_MONTH);
    }

    protected boolean isValidEvent(Date from, Date to){
        return (from.before(startDate)||from.equals(from)) && (to.after(startDate)||to.equals(startDate));
    }
}
