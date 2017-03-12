package uk.ac.cam.km662.hazel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by singhaniasnigdha on 11/3/2017.
 */

public class Event {
    private String id, name;
    double latitude, longitude;
    Date startDate = null;

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
}
