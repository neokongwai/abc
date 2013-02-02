package watsons.wine.notification;

import java.io.File;
import java.util.ArrayList;

import watsons.wine.R;
import watsons.wine.R.drawable;
import watsons.wine.R.id;
import watsons.wine.R.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<ItemDetails> itemDetailsrrayList;

	/*
	 * private Integer[] imgid = { R.drawable.p1, R.drawable.bb2, R.drawable.p2,
	 * R.drawable.bb5, R.drawable.bb6, R.drawable.d1 };
	 */

	private LayoutInflater l_Inflater;
	
	private Context context;

	public void setImage(String url) {

	}

	public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
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
			convertView = l_Inflater.inflate(R.layout.notification_list_view,null);
			holder = new ViewHolder();
			holder.itemTitle = (TextView) convertView.findViewById(R.id.ItemTitle);
			holder.itemTime = (TextView) convertView.findViewById(R.id.ItemText);
			holder.itemView = (LinearLayout) convertView.findViewById(R.id.MyListItem);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.itemTitle.setText(itemDetailsrrayList.get(position).getTitle());
		holder.itemTime.setText(itemDetailsrrayList.get(position).getDate());
		Drawable temp = null;
		if (itemDetailsrrayList.get(position).isRead()) {
			temp = context.getResources().getDrawable(R.drawable.bg_msg_read);
		}else {
			temp = context.getResources().getDrawable(R.drawable.bg_msg_unread);
		}
		holder.itemView.setBackgroundDrawable(temp);
		

		return convertView;
	}

	static class ViewHolder {
		TextView itemTitle;
		TextView itemTime;
		LinearLayout itemView;
	}
}