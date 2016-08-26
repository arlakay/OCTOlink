package id.co.octolink.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.co.octolink.R;
import id.co.octolink.adapter.History2Adapter;
import id.co.octolink.model.History2;

public class History2Activity extends AppCompatActivity {
    RecyclerView history2_recyclerview;
    private History2Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);

        List<History2> history2 = fill_with_history();
        history2_recyclerview = (RecyclerView) findViewById(R.id.history2_recyclerview);
        adapter = new History2Adapter(history2, getApplication());
        history2_recyclerview.setAdapter(adapter);
        history2_recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }
    public List<History2> fill_with_history() {

        List<History2> history2 = new ArrayList<>();

        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));

        return history2;
    }
}
