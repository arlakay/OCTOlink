package id.co.octolink.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import id.co.octolink.R;
import id.co.octolink.adapter.EventAdapter;
import id.co.octolink.model.Event;
import id.co.octolink.singleton.AppController;
import id.co.octolink.utils.Constants;
import id.co.octolink.utils.SessionManager;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class History extends AppCompatActivity {
    // Log tag
    private static final String TAG = History.class.getSimpleName();
    ArrayList<HashMap<String, String>> arraylist;

    AlertDialog.Builder alertDialogBuilder;

    // Movies json url
    private static final String url =  Constants.BASE_URL + "/users/history";
    private ProgressDialog pDialog;
    private List<Event> eventList = new ArrayList<Event>();
    private ListView listView;
    private EventAdapter adapter;
    String d_version = "0.0.0.1";
    String currentDate,yearNow,monthNow,year,month;
    private Toolbar toolbar;
    //ArrayList<HashMap<String, String>> historyList;
    SessionManager session;
    private SwipeRefreshLayout swipeContainerHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Alert Dialog
        alertDialogBuilder = new AlertDialog.Builder(History.this);
        alertDialogBuilder.setCancelable(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Showing progress dialog before making http request
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        listView = (ListView) findViewById(R.id.list);
        adapter = new EventAdapter(this, eventList);
        listView.setAdapter(adapter);

        swipeContainerHistory = (SwipeRefreshLayout) findViewById(R.id.swipeContainerHistory);
        swipeContainerHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Remember to CLEAR OUT old items before appending in the new ones
                eventList.clear();
                adapter.notifyDataSetChanged();
                // ...the data has come back, add new items to your adapter...
                getHistoryFromCloud();
                // Now we call setRefreshing(true) to signal refresh has start
                swipeContainerHistory.setRefreshing(true);
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            }
        });

        swipeContainerHistory.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright);

        getHistoryFromCloud();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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

    private void getHistoryFromCloud(){
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cid = user.get(SessionManager.KEY_CID);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        currentDate = dateFormat.format(date);
        
        //Log.e("CURRENT_DATE",currentDate);

        StringRequest movieReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("History Bro : ", response.toString());
                arraylist = new ArrayList<HashMap<String, String>>();
                //hidePDialog();
                try {
                    //Thread.sleep(2000);
                    JSONObject job = new JSONObject(response);
                    JSONArray data = job.getJSONArray("history");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        Event event = new Event();
                        event.setTitle(obj.getString("merchant_id"));
                        event.setDesc(obj.getString("transaction_date"));
                        event.setDate(obj.getString("total_amount"));
                        event.setThumbnailUrl(obj.getString("merchant_name"));
                        event.setStoreName(obj.getString("store_name"));
                        event.setDiskon(obj.getString("discount"));

                        String date = event.getDesc();
                        String dateFromAPI = "";
                        for (int j = 0; j <7 ; j++) {
                            dateFromAPI += date.charAt(j);
                        }

                        if (validationYearAndMonth(currentDate,dateFromAPI)){
                            eventList.add(event);
                        }

                        HashMap<String, String> map = new HashMap<String, String>();
                        obj = data.getJSONObject(i);
                        // Retrive JSON Objects
                        map.put("name", obj.getString("merchant_name"));
                        map.put("rank", obj.getString("merchant_id"));
                        map.put("country", obj.getString("transaction_date"));
                        map.put("population", obj.getString("total_amount"));
                        arraylist.add(map);
                    }

                    listView.setAdapter(adapter);
                    swipeContainerHistory.setRefreshing(false);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }/*catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //hidePDialog();
                adapter.notifyDataSetChanged();
                hidePDialog();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
                swipeContainerHistory.setRefreshing(false);

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
                params.put("cid", cid);
                //Log.d("cidkey", "cidkey" + cid);
                return params;
            }
        };

        movieReq.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(movieReq);
        EventAdapter adapter = new EventAdapter(History.this, eventList);
        listView.setAdapter(adapter);
    }

    private boolean validationYearAndMonth (String currentMonthAndYear, String MonthAndYear){

        if (currentMonthAndYear.equals(MonthAndYear)){return true;}else {return false;}
    }

}
