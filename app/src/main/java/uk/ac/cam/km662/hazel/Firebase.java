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
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println(dataSnapshot);

                JSONObject userData = (JSONObject) ((HashMap) dataSnapshot.getValue()).get(userID);
                System.out.println(userData);

                if (userData!=null) {
                    try {
                        double latitude = Double.parseDouble(userData.getString("latitude"));
                        double longitude = Double.parseDouble(userData.getString("longitude"));
                        Friend.setObj(userID, latitude, longitude);

                    } catch (JSONException e) {
                        System.err.println("Couldn't get friend location data.");
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println(databaseError);
            }
        });
    }
}
