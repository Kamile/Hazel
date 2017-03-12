package uk.ac.cam.km662.hazel;

/**
 * Created by sisimac on 11/03/2017.
 */

import android.provider.ContactsContract;

import com.google.firebase.database.*;

public class Firebase {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static void tryDB() {
        DatabaseReference myRef = Firebase.database.getReference("/users2");
        System.out.println(myRef);

        myRef.setValue("Hello, World!");
    }

    public static void updateFriendLocation(String userID, double latitude, double longitude) {
        DatabaseReference friendRefLat = Firebase.database.getReference(userID + "/latitude");
        DatabaseReference friendRefLon = Firebase.database.getReference(userID + "/longitude");
        friendRefLat.setValue(latitude);
        friendRefLon.setValue(longitude);
    }

}
