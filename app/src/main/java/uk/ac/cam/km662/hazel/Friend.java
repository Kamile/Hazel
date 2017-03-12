package uk.ac.cam.km662.hazel;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sisimac on 12/03/2017.
 */

public class Friend {
    private static JSONObject obj = new JSONObject();
    public static void setObj(String userID, double latitude, double longitude) {
        try {
            System.out.println("setObj");
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
        if (Friend.obj.has("url") && Friend.obj.has("id")) {
            try {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/" + Friend.obj.get("id") + "/picture",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    System.out.println("getObjResponse");
                                    System.out.println("response " + response);
                                    String url = (String) new JSONObject((String) response.getJSONObject().get("data")).get("url");
                                    System.out.println(url);
                                    Friend.obj.put("url", url);
                                } catch (JSONException e) {
                                    System.err.println(e);
                                }
                            }
                        }
                ).executeAsync();
            }
            catch (JSONException e) {
                System.err.println(e);
            }
        }
        return Friend.obj;
    }
}
