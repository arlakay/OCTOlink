package id.co.octolink.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.co.octolink.R;
import id.co.octolink.adapter.ListMerchantAdapter;
import id.co.octolink.singleton.AppController;
import id.co.octolink.utils.Constants;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class MerchantActivity extends AppCompatActivity {
    ListView listview;
    ListMerchantAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    private SwipeRefreshLayout swipeContainerPromo;

    public static String MERCHANT_ID = "merchant_id";
    public static String MERCHANT_NAME = "merchant_name";
    public static String DISKON = "diskon";

    private static final String url =  Constants.BASE_URL + "/users/merchant";
    private static final String TAG = MerchantActivity.class.getName(); //ersa boleh hapus

    AlertDialog.Builder alertDialogBuilder;

    int gridId;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        // Alert Dialog
        alertDialogBuilder = new AlertDialog.Builder(MerchantActivity.this);
        alertDialogBuilder.setCancelable(false);

        Intent i = getIntent();
        gridId = i.getIntExtra("id", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(PromoActivity.this);
        // Set progressdialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
        */
        // Locate the listview in listview_main.xml
        listview = (ListView) findViewById(R.id.listview);

        // Create an array
        arraylist = new ArrayList<HashMap<String, String>>();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //Log.d("network", "network" + networkInfo);
        //String network = networkInfo.toString();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            swipeContainerPromo = (SwipeRefreshLayout) findViewById(R.id.swipeContainerMerchant);
            swipeContainerPromo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Remember to CLEAR OUT old items before appending in the new ones
                    arraylist.clear();
                    adapter.notifyDataSetChanged();
                    // ...the data has come back, add new items to your adapter...
                    //new DownloadJSON().execute();
                    getMerchantFromCloud();

                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainerPromo.setRefreshing(true);
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.

                }
            });

            swipeContainerPromo.setColorSchemeResources(android.R.color.holo_red_light,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_blue_bright);

            //new DownloadJSON().execute();
            //getMerchantFromCloud();

        } else {
            Toast.makeText(getApplicationContext(), "No Internet Access Available!", Toast.LENGTH_LONG).show();
        }


        // Showing Swipe Refresh animation on activity create
        // As animation won't start on onCreate, post runnable is used
        swipeContainerPromo.post(new Runnable() {
            @Override
            public void run() {
                swipeContainerPromo.setRefreshing(true);
                getMerchantFromCloud();
            }
        });

        // Pass the results into ListViewAdapter.java
        adapter = new ListMerchantAdapter(MerchantActivity.this, arraylist);
        // Set the adapter to the ListView
        listview.setAdapter(adapter);
    }

    private void getMerchantFromCloud(){

        swipeContainerPromo.setRefreshing(true);

        if(gridId == 0){
            category = "Architecture";
        }else if (gridId == 1) {
            category = "Automotive";
        }else if (gridId == 2){
            category = "F&B";
        }else if (gridId == 3){
            category = "Health";
        }else if (gridId == 4){
            category = "Leisure";
        }else if (gridId == 5){
            category = "Sport";
        }else{
            category = "Master";
        }

        StringRequest movieReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("Merchant Bro : ", response.toString());
                //arraylist = new ArrayList<HashMap<String, String>>();
                //hidePDialog();
                try {
                    //Thread.sleep(5000);
                    JSONObject job = new JSONObject(response);
                    JSONArray data = job.getJSONArray("merchant");
                    for (int i = 0; i < data.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject obj = data.getJSONObject(i);

                        // Retrive JSON Objects
                        map.put("merchant_id", obj.getString("merchant_id"));
                        map.put("merchant_name", obj.getString("merchant_name"));
                        map.put("diskon", obj.getString("discount"));
                        //map.put("category", obj.getString("category"));

                        // Set the JSON Objects into     the array
                        arraylist.add(map);
                    }
                    adapter.notifyDataSetChanged();

                    //listview.setAdapter(adapter);
                    //make swipe container false
                    //mProgressDialog.dismiss();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }/*catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //hidePDialog();
                //mProgressDialog.dismiss();
                swipeContainerPromo.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                //mProgressDialog.dismiss();
                swipeContainerPromo.setRefreshing(false);
                alertDialogBuilder.setMessage("Error in Collectiong Data \nSwipe Down to Refresh");
                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //comment
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("category", category);
                //Log.d(TAG, "category" + category );
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(movieReq);
        //adapter = new ListViewAdapter(PromoActivity.this, arraylist);
        //listview.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
