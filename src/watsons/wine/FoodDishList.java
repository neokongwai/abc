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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class FoodDishList extends Activity {
	// url to make request
    private static String url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/list_dishes/";
    
    // JSON Node names
    private static final String TAG_LIST = "dishes_list";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME_EN = "name_en";
    private static final String TAG_NAME_CHI= "name_chi";
 
    // contacts JSONArray
    JSONArray dishes = null;
    Context mContext = FoodDishList.this;    

    List<Integer> emptyList = new ArrayList<Integer>();
    List<String> nameListEng = new ArrayList<String>();
    List<String> nameListChi = new ArrayList<String>();
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.food_list_dish);
		/* Wine List Tab Content */
		
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        String dish_url = url + bundle.getString("id");
        
        TextView tv = (TextView)findViewById(R.id.title_dish_text);
        tv.setText(bundle.getString("name"));
		
		// Hashmap for ListView
		final List<Map<String, String>> dishList = new ArrayList<Map<String, String>>();
 
        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
 
        // getting JSON string from URL
        JSONObject json = jParser.getJSONFromUrl(dish_url);
                
        try {
            // Getting Array of Contacts
        	dishes = json.getJSONArray(TAG_LIST);

            // looping through All Contacts
            for(int i = 0; i < dishes.length(); i++){
                JSONObject c = dishes.getJSONObject(i);
 
                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String name_en = c.getString(TAG_NAME_EN);
                String name_chi = c.getString(TAG_NAME_CHI);
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                // adding each child node to HashMap key => value
                map.put(TAG_ID, id);
                map.put(TAG_NAME_EN, name_en);
                map.put(TAG_NAME_CHI, name_chi);
 
                // adding HashList to ArrayList
                nameListEng.add(name_en);
                nameListChi.add(name_chi);
                dishList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView)findViewById(R.id.list_food_dish);
        FoodDishAdapter adapter = new FoodDishAdapter(this, nameListEng,nameListChi);
        listView.setAdapter(adapter);
		listView.setDividerHeight(0);
		listView.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Bundle bundle = new Bundle();
	        	bundle.putString("id", dishList.get(position).get(TAG_ID));
	        	Intent intent = new Intent(getParent(), FoodDishWeb.class);
	        	TabGroupBase parentActivity = (TabGroupBase)getParent();
	        	intent.putExtras(bundle);
	        	parentActivity.startChildActivity("FoodDishWeb", intent);
				
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
		super.onBackPressed();
	}
}