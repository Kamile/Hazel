package uk.ac.cam.km662.hazel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Map extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    final ArrayList<Event> events = new ArrayList<Event>();

    private DrawerLayout mDrawerLayout;
    private String[] mOptions;
    private ListView mDrawerList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);


        // Build Drawer for settings
        mOptions = getResources().getStringArray(R.array.nav_item_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptions));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mMap.clear(); // clear map of markers
                switch(position) {
                    case 0:
                        System.out.println("Events Only..");
                        displayNearbyEvents();
                        break;
                    case 1:
                        System.out.println("Friends Only..");
                        //displayNearbyFriends();
                        break;
                    case 3:
                        System.out.println("Logout");
                        finish();
                        break;
                    default:
                        System.out.println("Events & Friends..");
                        displayNearbyEvents();
                        //displayNearbyFriends();
                        break;
                }
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            if (mLastLocation != null) {
                System.out.println("last location 1");
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                Firebase.updateFriendLocation(ProfilePull.getUserID(), mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }

        } else {
            // Show rationale and request permission.
        }
        // get user's events and plot their locations on map
        displayNearbyEvents();
    }

    final GraphRequest.Callback graphCallback = new GraphRequest.Callback(){
        @Override
        public void onCompleted(GraphResponse response) {
            Bundle parameters = new Bundle();
            parameters.putString("limit", "10");
            JSONObject jObj = response.getJSONObject();
            try {
                JSONArray result = jObj.getJSONArray("data");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject obj = result.getJSONObject(i);
                    if(!obj.isNull("place")) {
                        JSONObject place = obj.getJSONObject("place");
                        String time = null;
                        if (!obj.isNull("start_time"))
                            time = obj.getString("start_time");
                        if(!place.isNull("location")) {
                            JSONObject location = place.getJSONObject("location");
                            final Event event = new Event(obj.getString("id"), obj.getString("name"),
                                    location.getDouble("latitude"), location.getDouble("longitude"),
                                    time);

                            if (event.isValidEvent()) {
                                events.add(event);
                                LatLng coordinates = new LatLng(event.getLatitude(), event.getLongitude());
                                mMap.addMarker(new MarkerOptions()
                                        .position(coordinates)
                                        .title(event.getName() + "///" + event.getID())
                                        .snippet(new SimpleDateFormat("EEE MMM d yyyy, K:mm a").format(event.getTime()))

                                );
                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        Intent intent = new Intent(getApplicationContext(), EventPage.class);
                                        String eventID = marker.getTitle().split("///")[1];
                                        intent.putExtra("eventID", eventID);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            GraphRequest nextRequest = response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
            if (nextRequest != null) {
                nextRequest.setGraphPath(parameters.getString("url"));
                nextRequest.setCallback(this);
                nextRequest.setParameters(parameters);
                nextRequest.executeAsync();
            }
        }
    };

    protected void getEvents(String id) {
        String url = "/"+id+"/events";
        Bundle parameters = new Bundle();
        parameters.putString("url", url);
        parameters.putString("limit", "10");

        new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    url,
                    parameters,
                    HttpMethod.GET,
                    graphCallback
            ).executeAsync();
    }

    protected void displayNearbyEvents() {
        getEvents("me");
        JSONArray friendList = null;
        while(friendList == null) {
            System.out.print("**WHILE**");
            friendList = ProfilePull.getFriends();
        }
        System.out.println("&&&");
        for(int index=0; index<friendList.length(); index++){
            try {
                JSONObject friend = friendList.getJSONObject(index);
                String id = friend.getString("id");
                getEvents(id);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            System.out.println("last location 2");
            // Move camera to last known location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            Firebase.updateFriendLocation(ProfilePull.getUserID(), mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }







}
