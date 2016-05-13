package id.co.octolink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import id.co.octolink.R;
import id.co.octolink.activity.MerchantActivity;
import id.co.octolink.activity.PromoActivity;
import id.co.octolink.utils.ImageLoader;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class ListMerchantAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListMerchantAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView merchantName;
        TextView diskon;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_item_merchant, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        merchantName = (TextView) itemView.findViewById(R.id.merchantName);
        diskon = (TextView) itemView.findViewById(R.id.diskon);
        ImageView iv_firstLetter2 = (ImageView) itemView.findViewById(R.id.imageView2);

        // Capture position and set results to the TextViews
        merchantName.setText(resultp.get(MerchantActivity.MERCHANT_NAME));
        diskon.setText(resultp.get(MerchantActivity.DISKON)+"%");

        //flag.setBackgroundResource(resultp.get(PromoActivity.FLAG));
        //resultp.get(PromoActivity.FLAG)
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        //flag.setText(resultp.get(PromoActivity.FLAG), flag);
        // Capture ListView item click
        //imageLoader.DisplayImage(resultp.get(PromoActivity.FLAG), flag);

        // get the first letter in merchant name
        String firstLetter = String.valueOf(resultp.get(MerchantActivity.MERCHANT_NAME).charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        // generate random color
        int color = generator.getColor(resultp.get(MerchantActivity.MERCHANT_NAME));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px
        iv_firstLetter2.setImageDrawable(drawable);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, PromoActivity.class);
                // Pass all data merchant name
                intent.putExtra("merchant_id", resultp.get(MerchantActivity.MERCHANT_ID));
                // Pass all data date
                intent.putExtra("merchant_name", resultp.get(MerchantActivity.MERCHANT_NAME));
                // Pass all data place
                intent.putExtra("diskon",resultp.get(MerchantActivity.DISKON));

                // Start SingleItemView Class
                context.startActivity(intent);
            }
        });
        return itemView;
    }
}
