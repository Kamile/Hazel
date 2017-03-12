package uk.ac.cam.km662.hazel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sisimac on 12/03/2017.
 */

public class SharedObj {
    private static JSONObject obj = new JSONObject();
    public static void setObj(String userID, double latitude, double longitude) {
        try {

            SharedObj.obj.put("id", userID);
            SharedObj.obj.put("latitude", latitude);
            SharedObj.obj.put("longitude", longitude);
            System.out.println(SharedObj.obj);
        }
        catch (JSONException e) {
            System.err.println(e);
        }
    }

    public static JSONObject getObj() {
        return SharedObj.obj;
    }
}
