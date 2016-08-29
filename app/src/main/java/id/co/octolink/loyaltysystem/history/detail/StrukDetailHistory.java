package id.co.octolink.loyaltysystem.history.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import id.co.octolink.R;
import id.co.octolink.utils.SessionManager;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class StrukDetailHistory extends AppCompatActivity {

    String storeName, customerID, date, diskon, merchantName;
    Button buttonDoneDetailHistory;
    TextView textViewStoreName, textViewCustomerIDDetailHistory,textViewDateDetailHistory, txtDiskon, textMerchantName;
    SessionManager sessionManager;

    private static final String TAG = StrukDetailHistory.class.getName(); //ersa boleh hapus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk_detail_history);

        // declaration
        buttonDoneDetailHistory = (Button)findViewById(R.id.buttonDoneDetailHistory);
        textViewStoreName = (TextView)findViewById(R.id.textViewStoreName);
        textMerchantName = (TextView) findViewById(R.id.textMerchantName);
        textViewCustomerIDDetailHistory = (TextView)findViewById(R.id.textViewCustomerIDDetailHistory);
        textViewDateDetailHistory = (TextView)findViewById(R.id.textViewDateDetailHistory);
        txtDiskon = (TextView)findViewById(R.id.textViewDiscountDetailHistory);

        // get from intent
        Intent i = getIntent();
        storeName = i.getStringExtra("storeName");
        date = i.getStringExtra("date");
        merchantName = i.getStringExtra("merchantName");
        diskon = i.getStringExtra("diskon");

        //Log.d(TAG, "diskon"+diskon);

        // session
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        customerID = user.get(SessionManager.KEY_CID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textMerchantName.setText(merchantName);
        textViewStoreName.setText(storeName);
        textViewCustomerIDDetailHistory.setText(customerID);
        textViewDateDetailHistory.setText(date);
        txtDiskon.setText(diskon+"%");

        buttonDoneDetailHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
