package watsons.wine.mycellars;

import java.util.ArrayList;

import watsons.wine.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CellarsListItemsAdapter extends BaseAdapter {
	private static ArrayList<MyCellarItemDetails> itemDetailsrrayList;

	/*
	 * private Integer[] imgid = { R.drawable.p1, R.drawable.bb2, R.drawable.p2,
	 * R.drawable.bb5, R.drawable.bb6, R.drawable.d1 };
	 */

	private LayoutInflater l_Inflater;
	
	private Context context;

	public void setImage(String url) {

	}

	public CellarsListItemsAdapter(Context context, ArrayList<MyCellarItemDetails> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
		this.context = context;
	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.my_cellars_list_view,null);
			holder = new ViewHolder();
			holder.itemImageView = (ImageView) convertView.findViewById(R.id.cellarWineImage);
			holder.itemName = (TextView) convertView.findViewById(R.id.cellarWineName);
			holder.itemStatus = (TextView) convertView.findViewById(R.id.cellarWineStatus);
			holder.itemView = new ArrayList<ImageView>();
			holder.itemView.add((ImageView) convertView.findViewById(R.id.cellarWineImage_0));
			holder.itemView.add((ImageView) convertView.findViewById(R.id.cellarWineImage_1));
			holder.itemView.add((ImageView) convertView.findViewById(R.id.cellarWineImage_2));
			holder.itemView.add((ImageView) convertView.findViewById(R.id.cellarWineImage_3));
			holder.itemView.add((ImageView) convertView.findViewById(R.id.cellarWineImage_4));
			holder.itemView.add((ImageView) convertView.findViewById(R.id.cellarWineImage_5));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.itemName.setText(itemDetailsrrayList.get(position).getWineName());
		holder.itemStatus.setText(itemDetailsrrayList.get(position).getWineStatus().equals("Y")? "In Stock":"Wish List");
		int favour = Integer.valueOf(itemDetailsrrayList.get(position).getWinefavourite());
		for (int i=0; i <favour; i++) {
			holder.itemView.get(i).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icon_wine_glass_full));
		}
		for (int i=favour; i <6; i++) {
			holder.itemView.get(i).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icon_wine_glass));
		}
		

		return convertView;
	}

	static class ViewHolder {
		TextView itemName;
		TextView itemStatus;
		ImageView itemImageView;
		ImageView itemView_1;
		ImageView itemView_2;
		ImageView itemView_3;
		ImageView itemView_4;
		ImageView itemView_5;
		ImageView itemView_6;
		ArrayList<ImageView> itemView;
	}
}