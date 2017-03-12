package uk.ac.cam.km662.hazel;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by sisimac on 11/03/2017.
 */

public class ProfilePull {
    private static JSONArray friends;
    private static String userID;

    public static String getUserID() { return ProfilePull.userID; }
    //friends
    public static JSONArray getFriends() {
        return ProfilePull.friends;
    }
    public static void getProfile() {

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            System.out.println(response);
                            String tmpProfile = (String) response.getJSONObject().get("id");
                            System.out.println(tmpProfile);
                            ProfilePull.userID = tmpProfile;
                        }
                        catch (JSONException e) {
                            System.err.println(e);
                        }
                    }
                }
        ).executeAsync();
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/friends",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                JSONArray tmpFriends = (JSONArray) response.getJSONObject().get("data");
                                ProfilePull.friends = tmpFriends;
                            }
                            catch (JSONException e) {
                                    System.err.println(e);
                            }
                        }
                    }
            ).executeAsync();
    }
}
