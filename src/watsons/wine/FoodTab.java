package watsons.wine;

import java.io.InputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import android.graphics.Typeface;

public class FoodTab extends Activity {

	int[] icons;
	String[] names;
	
	// url to make request
	private static String url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/food_and_wine_top_image";
	 // JSON Node names
    private static final String TAG_CONTENT = "top_image_url";
    private static final String TAG_URL = "url";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Food & Wine Tab Content */
		setContentView(R.layout.food_tab);
		
		/* change food and wine top image*/
		ImageView imageView_listwine = (ImageView) findViewById(R.id.list_wine);
		
		/*
		String top_image_url = get_top_img_url();
		if(top_image_url != null) {
			Drawable d = LoadImageFromWebOperations(top_image_url);
			Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
			Drawable dx = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, (int)(320*480/320), (int)(220*480/320), true));
			
			imageView_listwine.setImageDrawable(dx);
		}*/
		// Use local src img to increase performance and reduce network usage
		imageView_listwine.setImageResource(R.drawable.food_index1);

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
		//listView.setDividerHeight(2);
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) 
			{
				Bundle bundle = new Bundle();
	        	Intent intent = new Intent(getParent(), FoodCuisineList.class);
	        	TabGroupBase parentActivity = (TabGroupBase)getParent();
	        	intent.putExtras(bundle);
	        	parentActivity.startChildActivity("FoodCustineList", intent);
			}
		});

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
				rowview = inflater.inflate(R.layout.food_tab_cuisine_item, parent, false);
			}
			Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),
					"fonts/Arial Bold.ttf");
			TextView Nmae = (TextView) rowview.findViewById(R.id.list_cuisine_name);
			Nmae.setText(textname[position]);
			Nmae.setTypeface(tf);

			ImageView icon = (ImageView) rowview.findViewById(R.id.list_cuisine_image);
			icon.setImageResource(imageicons[position]);

			return rowview;
		}
	}
	
	public static Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
	private String get_top_img_url() {
		String return_url = null;
		
		// Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.getJSONFromUrl(url);
        JSONArray top_image_url = null;
        try {
            // Getting Array of Contacts
            top_image_url = json.getJSONArray(TAG_CONTENT);
            JSONObject c = top_image_url.getJSONObject(0);
            return_url = c.getString(TAG_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return return_url;
	}
	
}
