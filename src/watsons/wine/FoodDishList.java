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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
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
    FoodDishAdapter adapter = null;

    List<Integer> emptyList = new ArrayList<Integer>();
    List<String> nameListEng = new ArrayList<String>();
    List<String> nameListChi = new ArrayList<String>();
    
    // Hashmap for ListView
    List<Map<String, String>> dishList = new ArrayList<Map<String, String>>();
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.food_list_dish);
		/* Wine List Tab Content */
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        String dish_url = url + bundle.getString("id");
        Log.d("vk", "food dish list");
        TextView tv = (TextView)findViewById(R.id.title_dish_text);
        tv.setText(bundle.getString("name"));

        new JsonTask().execute(dish_url);

        ListView listView = (ListView)findViewById(R.id.list_food_dish);
        adapter = new FoodDishAdapter(this, nameListEng,nameListChi);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	private class JsonTask extends AsyncTask<String, Void, String> {

		ProgressDialog pdia;
		Boolean quitTask;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(getParent());
			pdia.setMessage("Loading...");
			pdia.show();
			quitTask = false;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			pdia.dismiss();
			if (quitTask) {
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						FoodDishList.this.getParent());
				alertDialogBuilder.setTitle("Warnings!");
				alertDialogBuilder
						.setMessage("Cannot connect. Please check your network and try again later.")
						.setCancelable(true)
						.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				
				return;
			}
			adapter.notifyDataSetChanged();
		}

		@Override
		protected String doInBackground(String... params) {
			if (!isOnline())
	        {
	        	quitTask = true;
	        	return null;
	        }
			// Creating JSON Parser instance
	        JSONParser jParser = new JSONParser();
	 
	        String dish_url = params[0];
	        // getting JSON string from URL
	        JSONObject json = jParser.getJSONFromUrl(dish_url);
	        
	        if(json == null)
	        {
	        	quitTask = true;
	        	return null;
	        }
	                
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
			return null;
		}
		
		public boolean isOnline() {
		    ConnectivityManager cm =
		        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
		}
	}
}