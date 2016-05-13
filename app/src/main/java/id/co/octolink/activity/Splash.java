package id.co.octolink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import id.co.octolink.R;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class Splash extends AppCompatActivity {
    private static int splashInterval = 2000;

    private static final String TAG = Splash.class.getName(); //ersa boleh hapus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(Splash.this, LoginActivity.class);
                startActivity(i);
                this.finnish();
            }

            private void finnish() {
            }
        }, splashInterval);
    }
}