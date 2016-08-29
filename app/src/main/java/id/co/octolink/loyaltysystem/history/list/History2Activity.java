package id.co.octolink.loyaltysystem.history.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.octolink.R;
import id.co.octolink.model.History2;

public class History2Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.history2_recyclerview)RecyclerView history2_recyclerview;

    private History2Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);
        ButterKnife.bind(this);

        setupToolbar();

        List<History2> history2 = fill_with_history();
        adapter = new History2Adapter(history2, getApplication());
        history2_recyclerview.setAdapter(adapter);
        history2_recyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

    public List<History2> fill_with_history() {

        List<History2> history2 = new ArrayList<>();

        history2.add(new History2("Merchant", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant1", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant2", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant3", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant4", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant5", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant6", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant7", "Jl.Merchant no 3", "22-09-16"));
        history2.add(new History2("Merchant8", "Jl.Merchant no 3", "22-09-16"));

        return history2;
    }
}
