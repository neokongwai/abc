package watsons.wine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import watsons.wine.notification.NotificationMainActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

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
	// private static final String TAG_DELETED = "deleted";
	private static final String TAG_REGION = "regions";
	// private static final String TAG_CUISINE_ID = "cuisine_id";

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

	// contacts JSONArray
	JSONArray cuisines = null;
	JSONArray regions = null;
	JSONArray regions_children = null;
	private SimpleExpandableListAdapter mAdapter;
	Context mContext = FoodCuisineList.this;
	ImageView topImage;
	ExpandableListView listView;
	ImageView refreshBtn;
	RelativeLayout refreshAni;

	List<Integer> emptyList = new ArrayList<Integer>();
	private SharedPreferences sharedPreferences;
	private JSONObject json;

	private TreeSet<ViewPos> mSeparatorsSet = new TreeSet<ViewPos>();

	// Hashmap for ListView
	List<Map<String, String>> cuisineList = new ArrayList<Map<String, String>>();
	List<List<Map<String, String>>> regionList = new ArrayList<List<Map<String, String>>>();

	public class ViewPos {
		int gourp;
		int child;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.food_list_tab);
		/* Wine List Tab Content */
		Constants.AT_FOOD_CUISINE = true;

		// Refresh Button
		refreshAni = (RelativeLayout) findViewById(R.id.refresh_img);
		refreshBtn = (ImageView) findViewById(R.id.refresh_btn);
		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				performRefresh();
			}

		});

		/* change food and wine top image */
		topImage = (ImageView) findViewById(R.id.food_top_image);
		topImage.setImageResource(R.drawable.food_index1);
		if (Constants.FOOD_CUISINE == true) {
			topImage.setVisibility(View.GONE);
		}

		new JsonTask().execute(url);

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.list_wine_item, R.id.list_wineitem_text, contryArr);

		mAdapter = new SimpleExpandableListAdapter(this, cuisineList,
				R.layout.food_list_item, new String[] { TAG_NAME },
				new int[] { R.id.list_wineitem_text }, regionList,
				R.layout.food_list_item_child, new String[] { TAG_NAME },
				new int[] { R.id.list_wineitem_text_child }) {
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// final View v = super.getGroupView( groupPosition, isExpanded,
				// convertView, parent);
				// convertView = newGroupView(isExpanded, parent);
				LayoutInflater li = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = li.inflate(R.layout.food_list_item, null);
				ImageView inidicatorImage = (ImageView) view
						.findViewById(R.id.explist_indicator);

				try {
					ImageView iconImage = (ImageView) view
							.findViewById(R.id.list_fooditem_img);
					Bitmap bitmap = loadBitmap(cuisineList.get(groupPosition)
							.get(TAG_ICON));
					iconImage.setImageBitmap(bitmap);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (getChildrenCount(groupPosition) == 0) {
					emptyList.add(groupPosition);
					inidicatorImage.setVisibility(View.INVISIBLE);
				} else {
					inidicatorImage
							.setImageResource(isExpanded ? R.drawable.arrow_down
									: R.drawable.arrow_right);
					inidicatorImage.setVisibility(View.VISIBLE);
				}
				TextView tv = (TextView) view
						.findViewById(R.id.list_fooditem_text);
				tv.setText(cuisineList.get(groupPosition).get(TAG_NAME));
				return view;
			}

			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				LayoutInflater li = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = null;
				if (isLastChild
						&& cuisineList.get(groupPosition).get(TAG_USE_SPONSOR)
								.contains("1")) {
					view = li.inflate(R.layout.food_list_sponsor, null);
					ImageView iv = (ImageView) view
							.findViewById(R.id.list_fooditem_sponsor);
					String url = regionList.get(groupPosition)
							.get(childPosition).get(TAG_SPONSOR);
					System.out.println("sponsor:" + url);
					try {
						Bitmap photo = loadBitmap(url);
						iv.setImageBitmap(photo);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					view = li.inflate(R.layout.food_list_item_child, null);
					TextView tv = (TextView) view
							.findViewById(R.id.list_fooditem_text_child);
					tv.setText(regionList.get(groupPosition).get(childPosition)
							.get(TAG_NAME));
				}

				return view;
			}

		};

		// Assign adapter to ListView
		listView = (ExpandableListView) findViewById(R.id.list_food);
		listView.setAdapter(mAdapter);
		listView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Constants.AT_FOOD_CUISINE = false;
				Bundle bundle = new Bundle();
				bundle.putBoolean("country", false);
				bundle.putString(
						"id",
						regionList.get(groupPosition).get(childPosition)
								.get(TAG_ID));
				bundle.putString(
						"name",
						regionList.get(groupPosition).get(childPosition)
								.get(TAG_NAME));
				Intent intent = new Intent(getParent(), FoodDishList.class);
				TabGroupBase parentActivity = (TabGroupBase) getParent();
				intent.putExtras(bundle);
				parentActivity.startChildActivity("FoodDishList", intent);
				return true;
			}
		});

		listView.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				if (topImage.getVisibility() != View.GONE) {
					Constants.FOOD_CUISINE = true;
					topImage.setVisibility(View.GONE);
					refreshAni.setVisibility(View.INVISIBLE);
					refreshBtn.setVisibility(View.INVISIBLE);
				}
				return false;
			}

		});

		ImageButton home_button = (ImageButton) findViewById(R.id.cellar_home_button);
		home_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		ImageButton mail_button = (ImageButton) findViewById(R.id.cellar_mail_button);
		mail_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getParent(),
						NotificationMainActivity.class);
				TabGroupBase parentActivity = (TabGroupBase) getParent();
				parentActivity.startChildActivity("MyCellarsMainCallByMail",
						intent);
			}
		});

	}

	protected void performRefresh() {
		refreshAni.setVisibility(View.VISIBLE);
		refreshBtn.setVisibility(View.INVISIBLE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove("json_food");
		editor.commit();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				onRestart();
			}
		}, 500);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Intent intent = new Intent(getParent(), FoodCuisineList.class);
		TabGroupBase parentActivity = (TabGroupBase) getParent();
		parentActivity.startChildActivityNotAddId("FoodCuisineList", intent);

	}

	public static Bitmap loadBitmap(String url) throws IOException {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(url)
					.getContent());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	public void onBackPressed() {
		if (topImage.getVisibility() == View.GONE
				|| Constants.FOOD_CUISINE == true) {
			Constants.FOOD_CUISINE = false;
			topImage.setVisibility(View.VISIBLE);
			refreshBtn.setVisibility(View.VISIBLE);
			for (int i = 0; i < cuisines.length(); i++) {
				listView.collapseGroup(i);
			}
		} else
			super.onBackPressed();
	}

	private class JsonTask extends AsyncTask<String, Void, String> {

		ProgressDialog pdia;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(getParent());
			pdia.setMessage("Loading...");
			pdia.show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mAdapter.notifyDataSetChanged();
			pdia.dismiss();
		}

		@Override
		protected String doInBackground(String... arg0) {
			sharedPreferences = getPreferences(MODE_PRIVATE);
			String strJson = sharedPreferences.getString("json_food", null);
			if (strJson != null) {
				try {
					json = new JSONObject(strJson);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} else {
				// Creating JSON Parser instance
				JSONParser jParser = new JSONParser();
				// getting JSON string from URL
				json = jParser.getJSONFromUrl(url);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putString("json_food", json.toString());
				editor.commit();
			}

			try {
				// Getting Array of Contacts
				cuisines = json.getJSONArray(TAG_LIST);

				// looping through All Contacts
				for (int i = 0; i < cuisines.length(); i++) {
					JSONObject c = cuisines.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_ID);
					String name = c.getString(TAG_NAME);
					String icon = c.getString(TAG_ICON);
					String sponsor = c.getString(TAG_SPONSOR);
					String use_sponsor = c.getString(TAG_USE_SPONSOR);

					// Provinces is again a JSON Object
					try {
						regions = c.getJSONArray(TAG_REGION);
					} catch (Exception e) {
						Log.e("Json Error",
								"province converting result " + e.toString());
					}

					List<Map<String, String>> children = new ArrayList<Map<String, String>>();
					for (int j = 0; j < regions.length(); j++) {
						JSONObject p = regions.getJSONObject(j);
						String regions_id = p.getString(TAG_ID);
						String regions_name = p.getString(TAG_NAME);

						// creating new HashMap
						HashMap<String, String> p_map = new HashMap<String, String>();
						p_map.put(TAG_ID, regions_id);
						p_map.put(TAG_NAME, regions_name);
						children.add(p_map);
					}
					if (use_sponsor.contains("1")) {
						HashMap<String, String> p_map = new HashMap<String, String>();
						p_map.put(TAG_ID, "30");
						p_map.put(TAG_SPONSOR, sponsor);
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
			return "";
		}
	}
}