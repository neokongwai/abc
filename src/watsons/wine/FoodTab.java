package watsons.wine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FoodTab extends Activity {

	int[] icons;
	String[] names;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Food & Wine Tab Content */
		setContentView(R.layout.food_tab);

		ListView listView = (ListView) findViewById(R.id.list_food);
		icons = new int[] { 
				R.drawable.chinese_cuisine,
				R.drawable.japanese_cuisine, 
				R.drawable.thai_cuisine,
				R.drawable.korean_cuisine, 
				R.drawable.vietnamese_cuisine };

		names = new String[] { 
				"Chinese Cuisine", 
				"Japanese Cuisine",
				"Thai Cuisine", 
				"Korean Cuisine", 
				"Vietnamese Cuisine" };

		listView.setAdapter(new IconTextAdapter(this,
				android.R.layout.simple_list_item_1, names, icons));
		listView.setDividerHeight(2);

	}

	public class IconTextAdapter extends ArrayAdapter<Object> {
		int[] imageicons;
		String[] textname;

		public IconTextAdapter(Context context, int textViewResourceId,
				String[] itemname, int[] images) {
			super(context, textViewResourceId, itemname);
			imageicons = images;
			textname = itemname;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View rowview = convertView;
			if (rowview == null) {
				LayoutInflater inflater = getLayoutInflater();
				rowview = inflater.inflate(R.layout.list_cuisine_item, parent, false);
			}
			TextView Nmae = (TextView) rowview.findViewById(R.id.list_cuisine_name);
			Nmae.setText(textname[position]);

			ImageView icon = (ImageView) rowview.findViewById(R.id.list_cuisine_image);
			icon.setImageResource(imageicons[position]);

			return rowview;
		}
	}
}
