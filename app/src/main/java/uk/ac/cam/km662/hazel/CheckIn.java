package uk.ac.cam.km662.hazel;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by singhaniasnigdha on 12/3/2017.
 */

public class CheckIn {

    private String name;
    private double latitude, longitude;
    int count = 0;

    protected CheckIn(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        count = 1;
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

    public boolean equals(Object o) {
        if(o instanceof CheckIn) {
            CheckIn that = (CheckIn) o;
            return this.name.equals(that.name) && this.latitude == that.latitude && this.longitude == that.longitude;
        }
        return false;
    }
}
