package uk.ac.cam.km662.hazel;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

/**
 * Created by sisimac on 11/03/2017.
 */

public class ProfilePull {
    public static void getProfile(){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        System.out.println(response);
                    }
                }
        ).executeAsync();
    }
}
