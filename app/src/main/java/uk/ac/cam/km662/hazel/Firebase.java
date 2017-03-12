package uk.ac.cam.km662.hazel;

import com.google.firebase.database.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Firebase {
    private static String retrieveID;
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference mDatabase;

    public Firebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void updateFriendLocation(String userID, double latitude, double longitude) {
        System.out.println("updating values for user " + userID);
        mDatabase.child(userID).child("longitude").setValue(longitude);
        mDatabase.child(userID).child("latitude").setValue(latitude);
    }

    public void retrieveFriendLocation(final String userID) {
        Firebase.retrieveID = userID;
        mDatabase.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println(dataSnapshot);
                String id = Firebase.retrieveID;
                HashMap userData = (HashMap)  dataSnapshot.getValue();

                if (userData!=null) {
                        double latitude = (Double) userData.get("latitude");
                        double longitude = (Double) userData.get("longitude");
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
