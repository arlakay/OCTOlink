package id.co.octolink.loyaltysystem.merchant.detail;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.octolink.R;

public class MerchantDetail extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.toolbar_layout)CollapsingToolbarLayout collapsingToolbarLayout;

    private String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);
        ButterKnife.bind(this);

        setupToolbar();

        Intent i = getIntent();
        storeName = i.getStringExtra("namaStore");

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent));

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

}
