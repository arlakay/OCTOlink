package id.co.octolink.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import id.co.octolink.R;
import id.co.octolink.utils.CekNet;
import id.co.octolink.utils.SessionManager;

public class CategoryPromo2 extends AppCompatActivity {
    SessionManager sessionManager;
    Boolean isInternetPresent = false;
    CekNet cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_promo2);
        ImageButton homeDeco = (ImageButton) findViewById(R.id.btn_home_deco);
        ImageButton automotive = (ImageButton) findViewById(R.id.btn_automotive);
        ImageButton foodDrinks = (ImageButton) findViewById(R.id.btn_food_drinks);
        ImageButton health = (ImageButton) findViewById(R.id.btn_health);
        ImageButton leisure = (ImageButton) findViewById(R.id.btn_leisure);
        ImageButton sports = (ImageButton) findViewById(R.id.btn_sports);

        // Session manager
        sessionManager = new SessionManager(getApplicationContext());

        // creating connection detector class instance
        cd = new CekNet(getApplicationContext());

        homeDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    Intent i = new Intent(CategoryPromo2.this, Merchant2Activity.class);
                    i.putExtra("id", 0);
                    startActivity(i);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(CategoryPromo2.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }

            }
        });

        automotive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    Intent i = new Intent(CategoryPromo2.this, Merchant2Activity.class);
                    i.putExtra("id", 1);
                    startActivity(i);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(CategoryPromo2.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }

            }
        });

        foodDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    Intent i = new Intent(CategoryPromo2.this, Merchant2Activity.class);
                    i.putExtra("id", 2);
                    startActivity(i);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(CategoryPromo2.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    Intent i = new Intent(CategoryPromo2.this, Merchant2Activity.class);
                    i.putExtra("id", 4);
                    startActivity(i);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(CategoryPromo2.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }
            }
        });

        leisure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    Intent i = new Intent(CategoryPromo2.this, Merchant2Activity.class);
                    i.putExtra("id", 4);
                    startActivity(i);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(CategoryPromo2.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    Intent i = new Intent(CategoryPromo2.this, Merchant2Activity.class);
                    i.putExtra("id", 5);
                    startActivity(i);
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(CategoryPromo2.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }
            }
        });
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
