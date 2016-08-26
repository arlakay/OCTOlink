package id.co.octolink.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import id.co.octolink.R;
import id.co.octolink.activity.MerchantDetail;
import id.co.octolink.activity.MerchantViewHolder;
import id.co.octolink.model.Merchant;

/**
 * Created by FirdaRinoa on 8/21/16.
 */
public class ListMerchantAdapter2 extends RecyclerView.Adapter<MerchantViewHolder>{

    List<Merchant> list = Collections.emptyList();
    Context context;


    public ListMerchantAdapter2(List<Merchant> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public MerchantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mercant_card, parent, false);
        MerchantViewHolder holder = new MerchantViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MerchantViewHolder holder, int position) {
        holder.name_merchant.setText(list.get(position).merchant_name);
        holder.address_merchant.setText(list.get(position).merchant_address);
        holder.rating_merchant.setText(list.get(position).merchant_star);
        holder.discount_merchant.setText(list.get(position).merchant_discount);
        holder.location_merchant.setText(list.get(position).merchant_location);
        holder.photo_merchant.setImageResource(list.get(position).merchant_image);
        holder.cardview_merchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MerchantDetail.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
