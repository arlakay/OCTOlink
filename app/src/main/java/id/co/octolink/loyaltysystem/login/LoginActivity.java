package id.co.octolink.loyaltysystem.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.co.octolink.R;
import id.co.octolink.loyaltysystem.mainmenu.Main2Activity;
import id.co.octolink.loyaltysystem.register.Register;
import id.co.octolink.singleton.AppController;
import id.co.octolink.utils.Constants;
import id.co.octolink.utils.SessionManager;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editUserId;
    private EditText editPass;
    Button btnLogin;
    private ProgressDialog pDialog;
    private static long back_pressed;
    String d_version = "0.0.0.1";
    private Button tvRegister;

    SessionManager session;
    String URL_LOGIN = Constants.BASE_URL + "/users/login";

    private static final String TAG = LoginActivity.class.getName(); //ersa boleh hapus

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserId = (EditText) findViewById(R.id.uid);
        editPass = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.buttonlogin);
        tvRegister = (Button)findViewById(R.id.textViewRegister);

        editUserId.setTypeface(Typeface.DEFAULT);
        editPass.setTypeface(Typeface.DEFAULT);
        editPass.setTransformationMethod(new PasswordTransformationMethod());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        //TO KNOW STATUS LOGIN
        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(intent);
            finish();
        }


        // Login button Click Promo
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = editUserId.getText().toString();
                String password = editPass.getText().toString();
                // Check for empty data in the form
                if (username.trim().length() > 0 && password.trim().length() > 0) {

                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    //Log.e("Problem", connMgr.toString());
                    if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                        checkLogin(username, password);
                    }
                    else{ Toast.makeText(getApplicationContext(),
                            "No Internet Access Available!", Toast.LENGTH_LONG)
                            .show();}
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter Phone Number and Password", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }
    private void checkLogin(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();
        /*if(username.equals("test") && password.equals("test")){
            //session.setLogin(true);
            session.createLoginSession(username, password);
            Log.e(TAG, "ID : " + username);
            Log.e(TAG, "PASSWORD : " + password);
            // Launch main activity
            Intent intent = new Intent(Login.this,
                    Menu_utama.class);
            startActivity(intent);
            finish();


        }else {
            Toast.makeText(getApplicationContext(),
                    "LOGIN ERROR", Toast.LENGTH_LONG).show();
            hideDialog();

        }*/

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String cid = jObj.getString("customer_id");
                    String firstname = jObj.getString("first_name");
                    String lastname = jObj.getString("last_name");
                    String pic = jObj.getString("pic");
                    String status = jObj.getString("status");

                    session.setLogin(true);
                    session.createLoginSession(username, password, cid, firstname, lastname);

                    // Launch main activity
                    Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                    intent.putExtra("cid", cid);
                    intent.putExtra("fname", firstname);
                    intent.putExtra("lname", lastname);
                    intent.putExtra("pic", pic);
                    intent.putExtra("status", status);

                    //Log.d("cid2", "cid2" + cid);
                    startActivity(intent);
                    finish();

                        /**
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("d_message");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
**/
                    //Intent intent2 = new Intent(LoginActivity.this, TotalAmount.class);
/**
                    //startActivity(intent2);
                    //String error = jObj.getString("d_status");
                    // String error = "1";
                    // Check for error node in json
                    //if (error.equals("00")) {
                        // user successfully logged in
                        // Create login session
                    session.setLogin(true);
                    session.createLoginSession(username, password);
                        // Launch main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("cid", cid);
                    Log.d("cid", "cid" + cid);
                    startActivity(intent);
                    finish();
**/
                    /**
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("d_message");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
**/

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    if (error.toString().equals("com.android.volley.ServerError")){
                        //Log.d("salahserver", "salahserver" + error.toString());
                        Toast.makeText(getApplicationContext(), "Phone Number and Password did not match", Toast.LENGTH_LONG).show();
                    } else{
                        //Log.d("salah", "salah" + error.toString());
                        Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_LONG).show();
                    }

                    hideDialog();

                }else{ //DATA GIVEN
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();}}
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("cid", username);
                params.put("pin", password);
                //params.put("d_version",d_version);
                return params;
            }
        };

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
    public void onBackPressed() {
        LoginActivity.super.onBackPressed();
        finish();
        back_pressed = System.currentTimeMillis();
    }
}
