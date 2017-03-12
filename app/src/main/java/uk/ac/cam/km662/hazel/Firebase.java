package uk.ac.cam.km662.hazel;

import com.google.firebase.database.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Firebase {
    private static String retrieveID, retrieveName;
    private static Map mapPointer;
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

    public void retrieveFriendLocation(final String userID, final String userName, final Map pointer) {
        Firebase.retrieveID = userID;
        Firebase.retrieveName = userName;
        Firebase.mapPointer = pointer;
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
                Firebase.mapPointer.actualGetFriends(Friend.getObj(), Firebase.retrieveName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println(databaseError);
            }
        });
    }
}
