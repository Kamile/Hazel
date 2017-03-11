package uk.ac.cam.km662.hazel;

/**
 * Created by singhaniasnigdha on 11/3/2017.
 */

public class Event {
    private String id, latitude, longitude;

    protected Event(String id, String latitude, String longitude){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected String getID(){
        return this.id;
    }

    protected String getLongitude(){
        return this.longitude;
    }

    protected String getLatitude() {return this.latitude; }
}
