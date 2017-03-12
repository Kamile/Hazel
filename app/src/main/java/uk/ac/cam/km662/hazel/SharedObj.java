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

public class SharedObj {
    private static JSONObject obj = new JSONObject();
    public static void setObj(String userID, double latitude, double longitude) {
        try {
            System.out.println("setObj");
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
        System.out.println("getObj");
        System.out.println(SharedObj.obj);
        if (SharedObj.obj.has("url") == false && SharedObj.obj.has("id")) {
            try {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/" + SharedObj.obj.get("id") + "/picture",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                try {
                                    System.out.println("getObjResponse");
                                    System.out.println(response);
                                    String url = (String) new JSONObject((String) response.getJSONObject().get("data")).get("url");
                                    System.out.println(url);
                                    SharedObj.obj.put("url", url);
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
        return SharedObj.obj;
    }
}
