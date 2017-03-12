package uk.ac.cam.km662.hazel;

import org.json.JSONException;
import org.json.JSONObject;


public class Friend {
    private static JSONObject obj = new JSONObject();
    public static void setObj(String userID, double latitude, double longitude) {
        try {

            Friend.obj.put("id", userID);
            Friend.obj.put("latitude", latitude);
            Friend.obj.put("longitude", longitude);
            System.out.println(Friend.obj);
        }
        catch (JSONException e) {
            System.err.println(e);
        }
    }

    public static JSONObject getObj() {
        return Friend.obj;
    }
}
