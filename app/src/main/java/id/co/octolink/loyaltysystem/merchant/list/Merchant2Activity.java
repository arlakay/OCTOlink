package id.co.octolink.loyaltysystem.merchant.list;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.octolink.R;
import id.co.octolink.loyaltysystem.merchant.detail.MerchantDetail;
import id.co.octolink.api.RestApi;
import id.co.octolink.api.services.ApiService;
import id.co.octolink.model.Merchant;
import id.co.octolink.model.MerchantResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Merchant2Activity extends AppCompatActivity {
    private StoreAdapter adapter2;
    private List<Merchant> merchantList;
    private String TAG = Merchant2Activity.class.getSimpleName();
    private int idCategoryPromo2;
    private String categoryAfterFiltering;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.merchant_recyclerview) RecyclerView merchant_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant2);
        ButterKnife.bind(this);

        setupToolbar();
        getData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication().getBaseContext());
        merchant_recyclerView.setLayoutManager(linearLayoutManager);
        merchant_recyclerView.setHasFixedSize(true);

        getStoreByCategory(idCategoryPromo2);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    private void getData(){
        Intent i = getIntent();
        idCategoryPromo2 = i.getIntExtra("id", 0);

    }

    private void getStoreByCategory(int category) {
        final ProgressDialog dialog = ProgressDialog.show(Merchant2Activity.this, "", "loading...");

        Log.e(TAG, String.valueOf(category));
        if(category == 0){
            categoryAfterFiltering = "Architecture";
        }else if (category == 1) {
            categoryAfterFiltering = "Automotive";
        }else if (category == 2){
            categoryAfterFiltering = "F&B";
        }else if (category == 3){
            categoryAfterFiltering = "Health";
        }else if (category == 4){
            categoryAfterFiltering = "Leisure";
        }else if (category == 5){
            categoryAfterFiltering = "Sport";
        }else{
            categoryAfterFiltering = "Master";
        }
        Log.e(TAG, categoryAfterFiltering);

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<MerchantResponse> call = apiService.getMerchantByCategory(categoryAfterFiltering);
        call.enqueue(new Callback<MerchantResponse>() {
            @Override
            public void onResponse(Call<MerchantResponse>call, Response<MerchantResponse> response) {
                dialog.dismiss();

                merchantList = response.body().getMerchant();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                adapter2 = new StoreAdapter(merchantList, R.layout.mercant_card, getApplicationContext(), new StoreAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Merchant model) {
//                        int jobId = model.getJob_id();
                        String namaMerchant =  model.getMerchant_name();
//                        String startTime = model.getStart_time();
//                        String finishTime = model.getFinish_time();
//                        String terminalCode = model.getTerminal_code();
//                        String func = model.getFunction_code();
//                        String symp = model.getSymptom_code();
//
//                        List<String> test = model.getSpareparts();
//                        Log.e("Var Woi : ", test.toString());
//                        String spare_code = test.toString();
//
                        Intent intent = new Intent(Merchant2Activity.this, MerchantDetail.class);
                        intent.putExtra("namaStore", namaMerchant);
//                        intent.putExtra("tech_code", tecCode);
//                        intent.putExtra("start_time", startTime);
//                        intent.putExtra("finish_time", finishTime);
//                        intent.putExtra("term_code", terminalCode);
//                        intent.putExtra("func_code", func);
//                        intent.putExtra("symp_code", symp);
//                        intent.putExtra("spare_code", spare_code);
//
                        startActivity(intent);
                    }
                });
                merchant_recyclerView.setAdapter(adapter2);
            }

            @Override
            public void onFailure(Call<MerchantResponse>call, Throwable t) {
                dialog.dismiss();

                Log.e(TAG, t.toString());

                AlertDialog alertDialog = new AlertDialog.Builder(Merchant2Activity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Kesalahan Jaringan");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialog.show();
            }
        });
    }

}
