package id.co.octolink.loyaltysystem.mainmenu;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.octolink.R;
import id.co.octolink.loyaltysystem.history.list.History2Activity;
import id.co.octolink.loyaltysystem.login.LoginActivity;
import id.co.octolink.loyaltysystem.mainmenu.navdrawer.ProfileActivity;
import id.co.octolink.loyaltysystem.merchant.category.CategoryPromo2;
import id.co.octolink.loyaltysystem.purchase.scan.BarcodeScanner;
import id.co.octolink.service.GeofenceErrorMessages;
import id.co.octolink.service.GeofenceTransitionsIntentService;
import id.co.octolink.singleton.AppController;
import id.co.octolink.utils.CekNet;
import id.co.octolink.utils.Constants;
import id.co.octolink.utils.SessionManager;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private String URL_STORE = "http://octolink.co.id/api/OCTOlink/index.php/api/transaction/merchant";
    private static final String TAG = Main2Activity.class.getName();
    protected ArrayList<Geofence> mGeofenceList;
    protected GoogleApiClient mGoogleApiClient;
    private SessionManager session;
    private JSONArray data;
    private CekNet cd;

    private String jsonResponse, id, mid, sid, sname, lat, lng, cid, pic;
    private Boolean isInternetPresent = false;
    private double lati,lngi;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        cd = new CekNet(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        cid = user.get(SessionManager.KEY_CID);
        final String fname = user.get(SessionManager.KEY_FNAME);
        final String lname = user.get(SessionManager.KEY_LNAME);

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        setupToolbar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mGeofenceList = new ArrayList<Geofence>();
        populateGeofenceList();
        buildGoogleApiClient();
        getStoreFromAPI();

        Intent intent = getIntent();
        pic = intent.getStringExtra("pic");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        TextView txtActiveUserName = (TextView)header.findViewById(R.id.txt_ActiveUserLogin);
        txtActiveUserName.setText(fname + " " + lname);
        txtActiveUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, ProfileActivity.class);
                i.putExtra("fullname", fname + " " + lname);
                i.putExtra("image_url", R.drawable.photo);
                startActivity(i);
            }
        });

        ImageView imgActiveUserName = (ImageView)header.findViewById(R.id.imageView);
        imgActiveUserName.setImageResource(R.drawable.photo);
        imgActiveUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, ProfileActivity.class);
                i.putExtra("fullname", fname + " " + lname);
                i.putExtra("image_url",R.drawable.photo);
                startActivity(i);
            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    @OnClick(R.id.sale)
    public void sale(View view) {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            Intent intent = new Intent(Main2Activity.this, BarcodeScanner.class);
            intent.putExtra("cid", cid);
            startActivity(intent);
        } else {
            showAlertDialog(Main2Activity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

    @OnClick(R.id.history)
    public void history(View view) {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            Intent intent = new Intent(Main2Activity.this, History2Activity.class);
            intent.putExtra("cid", cid);
            startActivity(intent);
        } else {
            showAlertDialog(Main2Activity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

    @OnClick(R.id.promo)
    public void promo(View view) {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            Intent intent = new Intent(Main2Activity.this, CategoryPromo2.class);
            intent.putExtra("cid", cid);
            startActivity(intent);
        } else {
            showAlertDialog(Main2Activity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main2, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_notification) {
//            addGeofencesButtonHandler();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_signout) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    logoutUser();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        } else {
            drawer.closeDrawer(GravityCompat.START);
            super.onBackPressed();
            finish();
        }
    }

    private void logoutUser() {
        session.setLogin(false);
        Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    private void getStoreFromAPI() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                URL_STORE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String rs = response.toString();
                Log.d(TAG, response.toString());
                try {
                    jsonResponse = "";
                    JSONObject job = new JSONObject(rs);
                    data = job.getJSONArray("store");

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject person = data.getJSONObject(i);

                        id = person.getString("idstore");
                        mid = person.getString("merchant_id");
                        sid = person.getString("store_id");
                        sname = person.getString("store_name");
                        lat = person.getString("latitude");
                        lng = person.getString("longitude");

                        jsonResponse += "ID: " + id + "\n\n";
                        jsonResponse += "MID: " + mid + "\n\n";
                        jsonResponse += "SID: " + sid + "\n\n";
                        jsonResponse += "Name: " + sname + "\n\n";
                        jsonResponse += "Lat: " + lat + "\n\n";
                        jsonResponse += "Lng: " + lng + "\n\n";

                        //txtResponse.setText(jsonResponse);

                        Log.d(TAG, String.valueOf(lati));
                        Log.d(TAG, String.valueOf(lngi));

                        lati = Double.parseDouble(lat);
                        lngi = Double.parseDouble(lng);

                        LatLng koor = new LatLng(lati, lngi);

                        Constants.BAY_AREA_LANDMARKS.put(sname,koor);
                        //Log.d(TAG, String.valueOf(Constants.BAY_AREA_LANDMARKS));
                    }
                    //txtResponse.setText(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }


    public void onResult(Status status) {
        if (status.isSuccess()) {
            Toast.makeText(this, "Notification Active until 12 hours from now", Toast.LENGTH_SHORT).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    status.getStatusCode());
        }
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void addGeofencesButtonHandler() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
        }
    }

    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : Constants.BAY_AREA_LANDMARKS.entrySet()) {

            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this geofence.
                    .setRequestId(entry.getKey())

                    // Set the circular region of this geofence.
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )

                    // Set the expiration duration of the geofence. This geofence gets automatically
                    // removed after this period of time.
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                    // Set the transition types of interest. Alerts are only generated for these
                    // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)

                    // Create the geofence.
                    .build());
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

}
