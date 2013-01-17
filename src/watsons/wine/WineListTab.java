package watsons.wine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WineListTab extends Activity {
	// url to make request
    private static String url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/list_countries";
 
    // JSON Node names
    private static final String TAG_COUNTRIES = "countries";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_PROVINCE = "province";
    private static final String TAG_PRODUCT_COUNT = "product_count";
 
    // contacts JSONArray
    JSONArray contries = null;
    
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.winelist_tab);
		/* Wine List Tab Content */
		
		 // Hashmap for ListView
        ArrayList<HashMap<String, String>> contryList = new ArrayList<HashMap<String, String>>();
 
        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
 
        // getting JSON string from URL
        JSONObject json = jParser.getJSONFromUrl(url);
        
        
        
        try {
            // Getting Array of Contacts
        	contries = json.getJSONArray(TAG_COUNTRIES);
 
            // looping through All Contacts
            for(int i = 0; i < contries.length(); i++){
                JSONObject c = contries.getJSONObject(i);
 
                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String province = c.getString(TAG_PROVINCE);
                String product_count = c.getString(TAG_PRODUCT_COUNT);
 
                /* Phone number is agin JSON Object
                JSONObject phone = c.getJSONObject(TAG_PHONE);
                String mobile = phone.getString(TAG_PHONE_MOBILE);
                String home = phone.getString(TAG_PHONE_HOME);
                String office = phone.getString(TAG_PHONE_OFFICE);*/
 
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
 
                // adding each child node to HashMap key => value
                map.put(TAG_ID, id);
                map.put(TAG_NAME, name);
                map.put(TAG_PROVINCE, province);
                map.put(TAG_PRODUCT_COUNT, product_count);
 
                // adding HashList to ArrayList
                contryList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int len = contries.length();
        List<String> zoom = new ArrayList<String>();
        for (int i = 0 ; i<len ; i++)
        {
        	zoom.add(contryList.get(i).get(TAG_NAME));
        	
        }

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		  R.layout.list_wine_item, R.id.list_wineitem_text, zoom);

		// Assign adapter to ListView
		ListView listView = (ListView) findViewById(R.id.list_wine);
		listView.setAdapter(adapter);
		listView.setDividerHeight(2);

	}
}