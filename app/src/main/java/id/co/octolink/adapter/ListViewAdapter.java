package id.co.octolink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.octolink.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import id.co.octolink.activity.DetailPromo;
import id.co.octolink.activity.PromoActivity;
import id.co.octolink.utils.ImageLoader;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
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
		TextView rank;
		TextView country;
		TextView population;
		TextView desc;
		ImageView flag;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		rank = (TextView) itemView.findViewById(R.id.rank);
		country = (TextView) itemView.findViewById(R.id.country);
		population = (TextView) itemView.findViewById(R.id.population);
		desc = (TextView) itemView.findViewById(R.id.diskon);

		ImageView iv_firstLetter = (ImageView) itemView.findViewById(R.id.list_image);

		//flag = (FeedImageView) itemView.findViewById(R.id.flag);

		// Locate the ImageView in listview_item.xml
		//flag = (ImageView) itemView.findViewById(R.id.flag);
		//flag = (ImageView) itemView.findViewById(R.id.flag);

		//flag = (CircularImageView) itemView.findViewById(R.id.flag);
		//FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.flag);

		// Capture position and set results to the TextViews
		rank.setText(resultp.get(PromoActivity.RANK));
		country.setText(resultp.get(PromoActivity.COUNTRY));
		//population.setText(resultp.get(PromoActivity.POPULATION));
		desc.setText(resultp.get(PromoActivity.FLAG)+"%");

		//flag.setBackgroundResource(resultp.get(PromoActivity.FLAG));
		//resultp.get(PromoActivity.FLAG)
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		//flag.setText(resultp.get(PromoActivity.FLAG), flag);
		// Capture ListView item click
		//imageLoader.DisplayImage(resultp.get(PromoActivity.FLAG), flag);

		// get the first letter in merchant name
		String firstLetter = String.valueOf(resultp.get(PromoActivity.RANK).charAt(0));
		ColorGenerator generator = ColorGenerator.MATERIAL;
		// generate random color
		int color = generator.getColor(resultp.get(PromoActivity.RANK));
		TextDrawable drawable = TextDrawable.builder()
				.buildRound(firstLetter, color); // radius in px
		iv_firstLetter.setImageDrawable(drawable);

		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, DetailPromo.class);
				// Pass all data merchant name
				intent.putExtra("rank", resultp.get(PromoActivity.RANK));
				// Pass all data date
				intent.putExtra("country", resultp.get(PromoActivity.COUNTRY));
				// Pass all data place
				intent.putExtra("population",resultp.get(PromoActivity.POPULATION));
				// Pass all data image
				intent.putExtra("flag", resultp.get(PromoActivity.FLAG));
				intent.putExtra("desc", resultp.get(PromoActivity.DESC));
				// Start SingleItemView Class
				context.startActivity(intent);
			}
		});
		return itemView;
	}
}
