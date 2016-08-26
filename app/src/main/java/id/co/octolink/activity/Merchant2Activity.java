package id.co.octolink.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.co.octolink.R;
import id.co.octolink.adapter.ListMerchantAdapter2;
import id.co.octolink.model.Merchant;
import id.co.octolink.utils.Constants;

public class Merchant2Activity extends AppCompatActivity {
    RecyclerView merchant_recyclerView;
    private ListMerchantAdapter2 adapter2;
    public static String MERCHANT_ID = "merchant_id";
    public static String MERCHANT_NAME = "merchant_name";
    public static String DISKON = "diskon";
    private static final String url =  Constants.BASE_URL + "/users/merchant";
    ArrayList<HashMap<String, String>> arraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant2);
        List<Merchant> merchant = fill_with_data();
        adapter2 = new ListMerchantAdapter2(merchant, getApplication());
        merchant_recyclerView = (RecyclerView) findViewById(R.id.merchant_recyclerview);
        merchant_recyclerView.setAdapter(adapter2);
        merchant_recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public List<Merchant> fill_with_data() {

        List<Merchant> data = new ArrayList<>();

        data.add(new Merchant("Fish n Co", "Emporium Pluit Mall lt. GF1","5.0","20%","12", R.drawable.dummy_image));
        data.add(new Merchant("Social House", "Grand Indonesia East Mall","4.5","50%","30",R.drawable.dummy_image));
        data.add(new Merchant("Secret Recipe", "Central Park lt. LG","4.0","30%","17",R.drawable.dummy_image));

        return data;
    }

}
