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

    //friends
    public static JSONArray getFriends() {
        return ProfilePull.friends;
    }
    public static void getProfile() {
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/friends",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                /*
                                System.out.println(response);
                                System.out.println("1 getJSONObject");
                                System.out.println(response.getJSONObject());
                                System.out.println("2 getData");
                                */
                                JSONArray tmpFriends = (JSONArray) response.getJSONObject().get("data");
                                System.out.println(tmpFriends);
//                                System.out.println(response.getJSONObject().get("data").getClass());
//                                System.out.println("3 ProfilePull.friends");
//                                System.out.println(ProfilePull.friends);
                                ProfilePull.friends = tmpFriends;
                                System.out.println("4 ProfilePull.friends");
                                System.out.println(ProfilePull.friends);
//                                System.out.println("5 ProfilePull.friends ends");
//                                ProfilePull.friends = response.getJSONObject().get("data");
                            }
                            catch (JSONException e) {
                                    System.err.println(e);
                            }
                        }
                    }
            );
    }
}
