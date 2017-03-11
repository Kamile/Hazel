package uk.ac.cam.km662.hazel;

/**
 * Created by singhaniasnigdha on 11/3/2017.
 */

public class Event {
    private String id;
    double latitude, longitude;

    protected Event(String id, double latitude, double longitude){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected String getID(){
        return this.id;
    }

    protected double getLongitude(){
        return this.longitude;
    }

    protected double getLatitude(){
        return this.latitude;
    }
}
