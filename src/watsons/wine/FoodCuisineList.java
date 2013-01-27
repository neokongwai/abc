package watsons.wine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class FoodCuisineList extends Activity {
	// url to make request
    private static String url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/list_cuisines_and_regions";
    
    // JSON Node names
    private static final String TAG_LIST = "list_cuisines_and_regions";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ICON = "icon";
    private static final String TAG_SPONSOR = "sponsor_img";
    private static final String TAG_USE_SPONSOR = "use_sponsor_img";
    private static final String TAG_DELETED = "deleted";
    private static final String TAG_REGION = "regions";
    private static final String TAG_CUISINE_ID = "cuisine_id";
    

 
    // contacts JSONArray
    JSONArray cuisines = null;
    JSONArray regions = null;
    JSONArray regions_children = null;
    private ExpandableListAdapter mAdapter;
    Context mContext = FoodCuisineList.this;    
    ImageView iv;
    ExpandableListView listView;

    List<Integer> emptyList = new ArrayList<Integer>();
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.food_list_tab);
		/* Wine List Tab Content */
		Constants.AT_FOOD_CUISINE = true;
		
		/* change food and wine top image*/
		iv = (ImageView) findViewById(R.id.food_top_image);
		iv.setImageResource(R.drawable.food_index1);
		if (Constants.FOOD_CUISINE == true)
		{
			iv.setVisibility(View.GONE);
		}
			
		
		// Hashmap for ListView
		final List<Map<String, String>> cuisineList = new ArrayList<Map<String, String>>();
		final List<List<Map<String, String>>> regionList = new ArrayList<List<Map<String, String>>>();	
 
        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
 
        // getting JSON string from URL
        JSONObject json = jParser.getJSONFromUrl(url);
                
        try {
            // Getting Array of Contacts
        	cuisines = json.getJSONArray(TAG_LIST);

            // looping through All Contacts
            for(int i = 0; i < cuisines.length(); i++){
                JSONObject c = cuisines.getJSONObject(i);
 
                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String icon = c.getString(TAG_ICON);
                String sponsor = c.getString(TAG_SPONSOR);
                String use_sponsor = c.getString(TAG_USE_SPONSOR);
                
                // Provinces is again a JSON Object
                try
                {
                	regions = c.getJSONArray(TAG_REGION);
                }
                catch (Exception e) {
                    Log.e("Json Error", "province converting result " + e.toString());       
                }

                List<Map<String, String>> children = new ArrayList<Map<String, String>>();
                for(int j = 0; j < regions.length(); j++){
                	JSONObject p = regions.getJSONObject(j);
	                String regions_id = p.getString(TAG_ID);
	                String regions_name = p.getString(TAG_NAME);
	                
	                // creating new HashMap
	                HashMap<String, String> p_map = new HashMap<String, String>();
	                p_map.put(TAG_ID, regions_id);
	                p_map.put(TAG_NAME, regions_name);
	                children.add(p_map);
                }
                regionList.add(children);
                
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                // adding each child node to HashMap key => value
                map.put(TAG_ID, id);
                map.put(TAG_NAME, name);
                map.put(TAG_ICON, icon);
                map.put(TAG_SPONSOR, sponsor);
                map.put(TAG_USE_SPONSOR, use_sponsor);
 
                // adding HashList to ArrayList
                cuisineList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		//  R.layout.list_wine_item, R.id.list_wineitem_text, contryArr);

		 mAdapter = new SimpleExpandableListAdapter(
	                this,
	                cuisineList,
	                R.layout.food_list_item,
	                new String[] {TAG_NAME},
	                new int[] { R.id.list_wineitem_text },
	                regionList,
	                R.layout.food_list_item_child,
	                new String[] {TAG_NAME},
	                new int[] {	R.id.list_wineitem_text_child }
	                )
		 {
			@Override
			public View getGroupView (int groupPosition, 
						boolean isExpanded, 
						View convertView, 
						ViewGroup parent) 
			{
					//final View v = super.getGroupView( groupPosition, isExpanded, convertView, parent);
					//convertView = newGroupView(isExpanded, parent);
					LayoutInflater li = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View view = li.inflate(R.layout.food_list_item, null);
					ImageView inidicatorImage = (ImageView) view.findViewById(R.id.explist_indicator);
					
					
					try {
						ImageView iconImage = (ImageView) view.findViewById(R.id.list_fooditem_img);
						Bitmap bitmap = loadBitmap(cuisineList.get(groupPosition).get(TAG_ICON));
						iconImage.setImageBitmap(bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if ( getChildrenCount( groupPosition ) == 0 ) 
					{
						emptyList.add(groupPosition);
						inidicatorImage .setVisibility(View.INVISIBLE);
					}
					else
					{	
						inidicatorImage.setImageResource(isExpanded?R.drawable.arrow_down:R.drawable.arrow_right);
						inidicatorImage .setVisibility( View.VISIBLE );
					}
					TextView tv = (TextView) view.findViewById(R.id.list_fooditem_text);
		            tv.setText(cuisineList.get(groupPosition).get(TAG_NAME));
					return view;
			}
			
			public View getChildView(int groupPosition, int childPosition, 
				    boolean isLastChild, View convertView, ViewGroup parent) 
			{
				LayoutInflater li = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = li.inflate(R.layout.food_list_item_child, null);
				TextView tv = (TextView) view.findViewById(R.id.list_fooditem_text_child);
	            tv.setText(regionList.get(groupPosition).get(childPosition).get(TAG_NAME));
			    return view;
			}
			
		 };

		// Assign adapter to ListView
		listView = (ExpandableListView) findViewById(R.id.list_food);
		listView.setAdapter(mAdapter);
		listView.setDividerHeight(0);
		listView.setOnChildClickListener(new OnChildClickListener() 
		{
			
	        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
	                int childPosition, long id) 
	        {
	        	Constants.AT_FOOD_CUISINE = false;
	        	Bundle bundle = new Bundle();
	        	bundle.putBoolean("country", false);
	        	bundle.putString("id", regionList.get(groupPosition).get(childPosition).get(TAG_ID));
	        	bundle.putString("name", regionList.get(groupPosition).get(childPosition).get(TAG_NAME));
	        	Intent intent = new Intent(getParent(), FoodDishList.class);
	        	TabGroupBase parentActivity = (TabGroupBase)getParent();
	        	intent.putExtras(bundle);
	        	parentActivity.startChildActivity("FoodDishList", intent);
	        	return true;
	        }
		});
		
		
		listView.setOnGroupClickListener(new OnGroupClickListener()
		{
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) 
			{
				if (iv.getVisibility() != View.GONE)
				{
					Constants.FOOD_CUISINE = true;
					iv.setVisibility(View.GONE);
				}
				return false;
			}
			
			
		});
		
	}
	public static Bitmap loadBitmap(String url) throws IOException {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		return bitmap;
	}
	
	@Override
	public void onBackPressed() {
	   if(iv.getVisibility() == View.GONE || Constants.FOOD_CUISINE == true)
	   {
       		Constants.FOOD_CUISINE = false;
			iv.setVisibility(View.VISIBLE);
			for (int i=0;i<cuisines.length();i++)
			{
				listView.collapseGroup(i);
			}
	   }
	   else
		   super.onBackPressed();
	}
}