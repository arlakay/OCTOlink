package id.co.octolink.loyaltysystem.mainmenu.navdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.octolink.R;

public class ProfileActivity extends AppCompatActivity {
    TextView creditcard_name;
    ImageView creditcard_photo;
    String fullname;
    int photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        creditcard_name = (TextView) findViewById(R.id.credit_card_name);
        creditcard_photo = (ImageView) findViewById(R.id.credit_card_photo);
        Intent i = getIntent();
        fullname = i.getStringExtra("fullname");
        photo = getIntent().getIntExtra("image_url",R.drawable.photo);
        creditcard_name.setText(fullname);
        creditcard_photo.setImageResource(photo);
    }
}
