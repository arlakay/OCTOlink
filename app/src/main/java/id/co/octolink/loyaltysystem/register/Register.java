package id.co.octolink.loyaltysystem.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.co.octolink.R;
import id.co.octolink.singleton.AppController;
import id.co.octolink.utils.Constants;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class Register extends AppCompatActivity {

    private static final String url = Constants.BASE_URL + "/users/register";
    private TextInputLayout inputLayoutPhone, inputLayoutEmail, inputLayoutPassword, inputLayoutFirstName, inputLayoutLastName;
    EditText et_firstName, et_lastName, et_email,et_phoneNumber,et_newPassword;
    Button bt_register;
    TextView tv_backToLogin;
    String firstName, lastName, email, phoneNumber, newPassword;
    AlertDialog.Builder alertDialogBuilder;
    private ProgressDialog pDialog;
    private static final String TAG = Register.class.getName(); //ersa boleh hapus
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
//                .coordinatorLayout);

        // Alert Dialog
        alertDialogBuilder = new AlertDialog.Builder(Register.this, R.style.AppCompatAlertDialogStyle);
        alertDialogBuilder.setCancelable(false);

        //inputLayoutFirstName = (TextInputLayout) findViewById(R.id.input_layout_first_name);
        //inputLayoutLastName = (TextInputLayout) findViewById(R.id.input_layout_last_name);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        //inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        //et_firstName = (EditText)findViewById(R.id.et_first_name);
        //et_lastName = (EditText)findViewById(R.id.et_last_name);
        //et_email = (EditText)findViewById(R.id.et_email);
        et_phoneNumber = (EditText)findViewById(R.id.et_phoneNumber);
        et_newPassword = (EditText)findViewById(R.id.et_newPassword);

        bt_register = (Button)findViewById(R.id.buttonRegister);
        tv_backToLogin = (TextView)findViewById(R.id.btnLinkToLogin);

//        et_firstName.setTypeface(Typeface.DEFAULT);
//        et_lastName.setTypeface(Typeface.DEFAULT);
//        et_email.setTypeface(Typeface.DEFAULT);
        et_phoneNumber.setTypeface(Typeface.DEFAULT);
        et_newPassword.setTypeface(Typeface.DEFAULT);

        et_newPassword.setTransformationMethod(new PasswordTransformationMethod());

//        et_firstName.addTextChangedListener(new MyTextWatcher(et_firstName));
//        et_lastName.addTextChangedListener(new MyTextWatcher(et_lastName));
        et_phoneNumber.addTextChangedListener(new MyTextWatcher(et_phoneNumber));
//        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_newPassword.addTextChangedListener(new MyTextWatcher(et_newPassword));

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        tv_backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            firstName = et_firstName.getText().toString();
//                lastName = et_lastName.getText().toString();
//                email = et_email.getText().toString();
                phoneNumber = et_phoneNumber.getText().toString();
                newPassword = et_newPassword.getText().toString();

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                // Check connection internet
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    registerUser(firstName, lastName, phoneNumber, email, newPassword);
                }else {
                    Toast.makeText(getApplicationContext(),
                        "No Internet Access Available!", Toast.LENGTH_LONG)
                        .show();
                }
            }
        });
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumberValid(String phoneNumber) {
        int intIndex = phoneNumber.indexOf("08");
        return intIndex == 0 && phoneNumber.trim().length() > 9;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateFirstName() {
        if (et_firstName.getText().toString().trim().isEmpty()) {
            //inputLayoutCardNumb.setError(getString(R.string.err_msg_card_number));
            et_firstName.setError(getString(R.string.err_msg_first_name));
            requestFocus(et_firstName);
            return false;
        } else {
            inputLayoutFirstName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateLastName() {
        if (et_lastName.getText().toString().trim().isEmpty()) {
            //inputLayoutCardNumb.setError(getString(R.string.err_msg_card_number));
            et_lastName.setError(getString(R.string.err_msg_last_name));
            requestFocus(et_lastName);
            return false;
        } else {
            inputLayoutLastName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone() {
        String phoneNumber = et_phoneNumber.getText().toString().trim();

        if (phoneNumber.isEmpty() || !isPhoneNumberValid(phoneNumber)) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone_number));
            et_phoneNumber.setError(getString(R.string.err_msg_phone_number));
            requestFocus(et_phoneNumber);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email) || !isEmailValid(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            et_email.setError(getString(R.string.err_msg_email));
            requestFocus(et_email);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        String newPassword  = et_newPassword.getText().toString().trim();

        if (newPassword.isEmpty() || newPassword.length()<5) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            et_newPassword.setError(getString(R.string.err_msg_password));
            requestFocus(et_newPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private void registerUser(final String firstName, final String lastName, final String phoneNumber, final String email,
                              final String newPassword) {
//
//        if (!validateFirstName()) {
//            return;
//        }
//        if (!validateLastName()) {
//            return;
//        }
//        if (!validateEmail()) {
//            return;
//        }
        if (!validatePhone()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("response");

                    // Check for code node in json
                    if (error.equals("00")){

                        JSONObject data = jObj.getJSONObject("status");
                        String reg_status = data.getString("register");

                        hideDialog();

                        alertDialogBuilder.setMessage("Registration Success");
                        alertDialogBuilder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                                //startActivity(i);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }else{

                        alertDialogBuilder.setMessage("Registration Success");
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

                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Registration Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                //        "Registration Failed", Toast.LENGTH_LONG).show();
                hideDialog();

                AppController.getInstance().cancelPendingRequests(this);

                alertDialogBuilder.setMessage("Registration Fail");
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
//                params.put("firstname", firstName);
//                params.put("lastname", lastName);
//                params.put("email", email);
                params.put("phone", phoneNumber);
                params.put("pin", newPassword);
                return params;

            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
//                case R.id.et_first_name:
//                    validateFirstName();
//                    break;
//                case R.id.et_last_name:
//                    validateLastName();
//                    break;
                case R.id.et_phoneNumber:
                    validatePhone();
                    break;
//                case R.id.et_email:
//                    validateEmail();
//                    break;
                case R.id.et_newPassword:
                    validatePassword();
                    break;
            }
        }
    }

}
