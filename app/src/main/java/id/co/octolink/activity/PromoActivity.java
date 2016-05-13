package id.co.octolink.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import id.co.octolink.adapter.ListViewAdapter;
import id.co.octolink.singleton.AppController;
import id.co.octolink.utils.Constants;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class PromoActivity extends AppCompatActivity {
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    public static String RANK = "rank";
    public static String COUNTRY = "country";
    public static String POPULATION = "population";
    public static String FLAG = "flag";
    public static String DESC = "desc";
    private SwipeRefreshLayout swipeContainerPromo;

    private static final String url = Constants.BASE_URL + "/users/store";
    private static final String TAG = PromoActivity.class.getName(); //ersa boleh hapus

    AlertDialog.Builder alertDialogBuilder;

    String category, merch_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        // Alert Dialog
        alertDialogBuilder = new AlertDialog.Builder(PromoActivity.this);
        alertDialogBuilder.setCancelable(false);

        Intent i = getIntent();
        merch_id = i.getStringExtra("merchant_id");
        //Log.d(TAG, "mid : " + merch_id);//ersa boleh hapus

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
            swipeContainerPromo = (SwipeRefreshLayout) findViewById(R.id.swipeContainerPromo);
            swipeContainerPromo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Remember to CLEAR OUT old items before appending in the new ones
                    arraylist.clear();
                    adapter.notifyDataSetChanged();
                    // ...the data has come back, add new items to your adapter...
                    //new DownloadJSON().execute();
                    getStoreFromCloud();
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
                getStoreFromCloud();
            }
        });
        // Pass the results into ListViewAdapter.java
        adapter = new ListViewAdapter(PromoActivity.this, arraylist);
        // Set the adapter to the ListView
        listview.setAdapter(adapter);
    }

    private class DownloadJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getStoreFromCloud();
            /*
            // Retrieve JSON Objects from the given URL address
            jsonobject = JSONfunctions.getJSONfromURL("http://192.168.43.132/AdMedicaAPI/index.php/api/users/merchant");
            try {
                // wait for list
                Thread.sleep(2000);
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("merchant");
                Log.d("pasti", "pasti" + jsonarray.toString());

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);
                    // Retrive JSON Objects
                    map.put("rank", jsonobject.getString("merchant_name"));
                    map.put("country", jsonobject.getString("country"));
                    map.put("population", jsonobject.getString("city"));
                    map.put("flag", jsonobject.getString("discount"));
                    //map.put("desc", jsonobject.getString("description"));

                    // Set the JSON Objects into     the array
                    arraylist.add(map);
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(PromoActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Set swipe container false
            swipeContainerPromo.setRefreshing(false);

        }
    }

    private void getStoreFromCloud(){

        swipeContainerPromo.setRefreshing(true);

        StringRequest movieReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("Merchant Bro : ", response.toString());
                //arraylist = new ArrayList<HashMap<String, String>>();
                //hidePDialog();
                try {
                    //Thread.sleep(5000);
                    JSONObject job = new JSONObject(response);
                    JSONArray data = job.getJSONArray("store");
                    for (int i = 0; i < data.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject obj = data.getJSONObject(i);

                        // Retrive JSON Objects
                        map.put("rank", obj.getString("store_name"));
                        map.put("country", obj.getString("street"));
                        map.put("population", obj.getString("city"));
                        map.put("flag", obj.getString("discount"));
                        //map.put("desc", obj.getString("description"));

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
                alertDialogBuilder.setMessage("Error in Collectiong Data \n" +
                        "Swipe Down to Refresh");
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
                params.put("mid", merch_id);
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