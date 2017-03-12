package uk.ac.cam.km662.hazel;

/**
 * Created by sisimac on 11/03/2017.
 */

import android.provider.ContactsContract;

import com.google.firebase.database.*;

import org.json.JSONObject;

import java.util.HashMap;

public class Firebase {
    private static String retrieveID;
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static void tryDB() {
        DatabaseReference myRef = Firebase.database.getReference("/users2");
        System.out.println(myRef);

        myRef.setValue("Hello, World!");
    }

    public static void updateFriendLocation(String userID, double latitude, double longitude) {
        System.out.println("updating values for user " + userID);
        DatabaseReference friendRefLat = Firebase.database.getReference(userID + "/latitude");
        DatabaseReference friendRefLon = Firebase.database.getReference(userID + "/longitude");
        friendRefLat.setValue(latitude);
        friendRefLon.setValue(longitude);
    }

    public static void retrieveFriendLocation(String userID) {
        Firebase.retrieveID = userID;
        System.out.println("retrieveFriendLocation");
        System.out.println(userID);
        database.getReference().child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            private String id = Firebase.retrieveID;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println(dataSnapshot);
                System.out.println(id);

                HashMap<String, String> userData = (HashMap) dataSnapshot.getValue();

                if (userData != null) {
                    double latitude = (Double)((HashMap)dataSnapshot.getValue()).get("latitude");
                    double longitude = (Double)((HashMap)dataSnapshot.getValue()).get("longitude");
                    Friend.setObj(id, latitude, longitude);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println(databaseError);
            }
        });
    }
}
