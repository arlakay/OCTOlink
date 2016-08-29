package id.co.octolink.loyaltysystem.history.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import id.co.octolink.R;

/**
 * Created by FirdaRinoa on 8/22/16.
 */
public class History2ViewHolder extends RecyclerView.ViewHolder{
    public CardView history2_cardview;
    public TextView namemerchant_history2,addressmerchant_history2,datemerchant_history2;
    public History2ViewHolder(View itemView) {
        super(itemView);
        history2_cardview = (CardView) itemView.findViewById(R.id.history2_cardView);
        namemerchant_history2 = (TextView) itemView.findViewById(R.id.merchantname_history2);
        addressmerchant_history2 = (TextView) itemView.findViewById(R.id.merchantaddress_history2);
        datemerchant_history2 = (TextView) itemView.findViewById(R.id.merchantdate_history2);
    }
}
