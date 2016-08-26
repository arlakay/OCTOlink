package id.co.octolink.activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import id.co.octolink.R;
/**
 * Created by FirdaRinoa on 8/21/16.
 */
public class MerchantViewHolder extends RecyclerView.ViewHolder {
    public CardView cardview_merchant;
    public ImageView photo_merchant;
    public TextView name_merchant,address_merchant,rating_merchant,location_merchant,discount_merchant;
    public RatingBar star_merchant;
    public MerchantViewHolder(View itemView) {
        super(itemView);
        cardview_merchant = (CardView) itemView.findViewById(R.id.cardview_merchant);
        photo_merchant = (ImageView) itemView.findViewById(R.id.photo_merchant);
        name_merchant = (TextView) itemView.findViewById(R.id.name_merchant);
        address_merchant = (TextView) itemView.findViewById(R.id.address_merchant);
        rating_merchant = (TextView) itemView.findViewById(R.id.startext_merchant);
        location_merchant = (TextView) itemView.findViewById(R.id.locationtext_merchant);
        discount_merchant = (TextView) itemView.findViewById(R.id.discount_mercant);
        star_merchant = (RatingBar) itemView.findViewById(R.id.ratingBar_mercant);
    }
}
