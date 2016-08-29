package id.co.octolink.loyaltysystem.purchase.confirm;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.co.octolink.R;
import id.co.octolink.singleton.AppController;
import id.co.octolink.utils.Constants;
import id.co.octolink.utils.ImageLoader;
import id.co.octolink.utils.SessionManager;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class DiscountPage extends AppCompatActivity {
    private ProgressDialog pDialog;
    private static final String url = Constants.BASE_URL + "/transaction/sales";
    SessionManager session;
    TextView textViewDiscount, textStoreName;
    Button buttonClose;
    ImageView imageViewStore;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ImageLoader imageLoader = new ImageLoader(this);
    AlertDialog.Builder alertDialogBuilder;
    String disc;

    /** The default socket timeout in milliseconds */
    public static final int DEFAULT_TIMEOUT_MS = 0;
    /** The default number of retries */
    public static final int DEFAULT_MAX_RETRIES = 0;
    /** The default backoff multiplier */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

    private static final String TAG = DiscountPage.class.getName(); //ersa boleh hapus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_page);

        // Declare
        textViewDiscount = (TextView)findViewById(R.id.disc);
        buttonClose = (Button)findViewById(R.id.buttonClose);
        imageViewStore = (ImageView) findViewById(R.id.flag);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textStoreName =  (TextView) findViewById(R.id.txt_storeName);

        // Alert Dialog
        alertDialogBuilder = new AlertDialog.Builder(DiscountPage.this);
        alertDialogBuilder.setCancelable(false);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cid = user.get(SessionManager.KEY_CID);

        Intent intent = getIntent();
        final String amount = intent.getStringExtra("amount");
        //final String cid = intent.getStringExtra("cid");
        final String merchant = intent.getStringExtra("merchantid");
        //Log.d(TAG, "cidmain " + cid);


        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                //startActivity(i);
                finish();
            }
        });


        /******** Adding request to request queue ********/

        // Tag used to cancel the request
        String tag_string_req = "req_disc";
        pDialog.setMessage("Processing...");
        showDialog();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d("Response: ", response.toString());
                hideDialog();

                try {
                    JSONObject job = new JSONObject(response);
                    String rsp = job.getString("response");

                    // Check for code node in json
                    if (rsp.equals("00")){
                        JSONObject data = job.getJSONObject("status");
                        String storename = data.getString("store_name");
                        String sale = data.getString("new_amount");
                        disc = data.getString("discount");
                        String new_amount = data.getString("new_amount");
                        String merchantname = data.getString("merchant_name");
                        //String link_image = data.getString("link_image");

                        collapsingToolbarLayout.setTitle(merchantname);
                        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                        textStoreName.setText(storename);
                        toolbar.setTitle(merchantname);
                        textViewDiscount.setText(disc);
                        //textViewDiscount.setVisibility(View.VISIBLE);

                        // Passes flag images URL into ImageLoader.class
                        //Log.e("LINK_IMAGE",link_image);
                        //imageLoader.DisplayImage(link_image, imageViewStore);
                        hideDialog();

                    }else {
                        // Error in login. Get the error message
                        hideDialog();
                        alertDialogBuilder.setMessage("Qr Code does not exist");
                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                                //startActivity(i);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        //Toast.makeText(getApplicationContext(),
                        //        "Qr Code does not exist", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Transaction Error: ", error.getMessage());
                if (error != null){ //NULL DATA GIVEN
                    hideDialog();

                    //Toast.makeText(DiscountPage.this, "", Toast.LENGTH_LONG).show();
                    AppController.getInstance().cancelPendingRequests(this);

                    alertDialogBuilder.setMessage("Fail to recognize the QR Code, Please Try Again");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                            //startActivity(i);
                            finish();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("merchant", merchant);
                params.put("amount", amount);
                params.put("cid", cid);
                return params;
            }

        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
