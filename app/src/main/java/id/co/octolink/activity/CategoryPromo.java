package id.co.octolink.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import id.co.octolink.R;
import id.co.octolink.adapter.ImageAdapter;
import id.co.octolink.utils.CekNet;
import id.co.octolink.utils.SessionManager;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class CategoryPromo extends AppCompatActivity {
    SessionManager sessionManager;
    Boolean isInternetPresent = false;
    CekNet cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_new_design);

        // Session manager
        sessionManager = new SessionManager(getApplicationContext());

        // creating connection detector class instance
        cd = new CekNet(getApplicationContext());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        //gridview.setNumColumns(2);
        gridview.setColumnWidth(GridView.AUTO_FIT);
        //gridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String cid = intent.getStringExtra("cid");

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    // Send intent to SingleViewActivity
                    Intent i = new Intent(getApplicationContext(), MerchantActivity.class);
                    // Pass image index
                    i.putExtra("id", position);
                    startActivity(i);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(CategoryPromo.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }
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

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

}
