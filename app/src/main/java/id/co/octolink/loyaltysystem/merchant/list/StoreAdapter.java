package id.co.octolink.loyaltysystem.merchant.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.co.octolink.R;
import id.co.octolink.model.Merchant;

/**
 * Created by ILM on 8/1/2016.
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.VersionViewHolder> {

    private List<Merchant> merchantList;
    private int rowLayout;
    Context context;
    OnItemClickListener clickListener;

    public StoreAdapter(List<Merchant> login, int rowLayout, Context context, OnItemClickListener listener) {
        this.merchantList = login;
        this.rowLayout = rowLayout;
        this.context = context;
        this.clickListener = listener;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mercant_card, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        final Merchant model = merchantList.get(i);
        versionViewHolder.bind(model, clickListener);
    }

    @Override
    public int getItemCount() {
        return merchantList == null ? 0 : merchantList.size();
    }

    public void animateTo(List<Merchant> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Merchant> newModels) {
        for (int i = merchantList.size() - 1; i >= 0; i--) {
            final Merchant model = merchantList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Merchant> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Merchant model = newModels.get(i);
            if (!merchantList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Merchant> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Merchant model = newModels.get(toPosition);
            final int fromPosition = merchantList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Merchant removeItem(int position) {
        final Merchant model = merchantList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Merchant model) {
        merchantList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Merchant model = merchantList.remove(fromPosition);
        merchantList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        CardView cardStore;
        TextView namaStore;
        TextView alamatStore;
        TextView rateStore;
        TextView kmNearStore;
        TextView diskonStore;
        ImageView imgStore;

        public VersionViewHolder(View itemView) {
            super(itemView);

            namaStore = (TextView) itemView.findViewById(R.id.name_merchant);
            alamatStore = (TextView) itemView.findViewById(R.id.address_merchant);
            rateStore = (TextView) itemView.findViewById(R.id.startext_merchant);
            kmNearStore = (TextView) itemView.findViewById(R.id.locationtext_merchant);
            diskonStore = (TextView) itemView.findViewById(R.id.discount_mercant);
            imgStore = (ImageView) itemView.findViewById(R.id.photo_merchant);

        }

        public void bind(final Merchant model, final OnItemClickListener listener) {
            namaStore.setText( model.getMerchant_name());
//            alamatStore.setText( model.getTerminal_code());
//            rateStore.setText( model.getTerminal_code());
//            kmNearStore.setText( model.getTerminal_code());
//            diskonStore.setText( model.getTerminal_code());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);

                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(Merchant model);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
