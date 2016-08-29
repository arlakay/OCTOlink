package id.co.octolink.loyaltysystem.history.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import id.co.octolink.R;
import id.co.octolink.model.History2;

/**
 * Created by FirdaRinoa on 8/22/16.
 */
public class History2Adapter extends RecyclerView.Adapter<History2ViewHolder>{

    List<History2> listhist = Collections.emptyList();
    Context context;

    public History2Adapter(List<History2> listhist, Context context){
        this.listhist = listhist;
        this.context = context;
    }

    @Override
    public History2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history2_card, parent, false);
        History2ViewHolder holder = new History2ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(History2ViewHolder holder, int position) {
        holder.namemerchant_history2.setText(listhist.get(position).namemerchant_history2);
        holder.addressmerchant_history2.setText(listhist.get(position).addressmerchant_history2);
        holder.datemerchant_history2.setText(listhist.get(position).datemerchant_history2);

    }

    @Override
    public int getItemCount() {
        return listhist.size();
    }
}
