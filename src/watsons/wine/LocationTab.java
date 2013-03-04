package watsons.wine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import watsons.wine.notification.NotificationMainActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationTab extends MapActivity {
	private static final int INITIAL_ZOOM_LEVEL = 14;
	private static final int INITIAL_LATITUDE = 22290000;
	private static final int INITIAL_LONGITUDE = 114170000;
	private static final GeoPoint Central = new GeoPoint(INITIAL_LATITUDE, INITIAL_LONGITUDE);
	
	private static String url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/list_shops";
	
	// JSON Node names
    private static final String TAG_SHOP = "shops";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_DISTRICT = "district";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_LAT = "latitude";
    private static final String TAG_LON = "longitude";
    private static final String TAG_TEL = "tel";
    private static final String TAG_FAX = "fax";
    private static final String TAG_OPEN_HR = "opening_hours";
    
    JSONArray shops = null;
    final List<Map<String, String>> shopList = new ArrayList<Map<String, String>>();
    final ArrayList<String> idList = new ArrayList<String>();
    final ArrayList<String> nameList = new ArrayList<String>();
    final ArrayList<String> districtList = new ArrayList<String>();
    
    Context mContext = LocationTab.this;
    OverlayItem oli = null;
    MyItemizedOverlay miol = null;
    MapView mv = null;
    MapController mc = null;
    ImageView iv = null;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Location Tab Content */
		setContentView(R.layout.location_tab);
		
		mv = (MapView) findViewById(R.id.mapview);
	    mv.setBuiltInZoomControls(true);
	    
	    mc = mv.getController();
	    mc.setZoom(INITIAL_ZOOM_LEVEL);
		mc.setCenter(Central);
		mv.getMapCenter();
		
		iv = (ImageView) findViewById(R.id.title_location_list_btn);
		iv.setOnClickListener(list_btn_listner);
		
		List<Overlay> overlay = mv.getOverlays();
			
		// Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
        
        JSONObject json = jParser.getJSONFromUrl(url);
        
        try {
            // Getting Array of Shops
        	shops = json.getJSONArray(TAG_SHOP);
            // looping through All Contacts
            for(int i = 0; i < shops.length(); i++){
                JSONObject s = shops.getJSONObject(i);
                
                // creating new HashMap
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                
                // adding each child node to HashMap key => value
                map.put(TAG_ID, s.getString(TAG_ID));
                map.put(TAG_NAME, s.getString(TAG_NAME));
                map.put(TAG_DISTRICT, s.getString(TAG_DISTRICT));
                map.put(TAG_ADDRESS, s.getString(TAG_ADDRESS));
                map.put(TAG_LAT, s.getString(TAG_LAT));
                map.put(TAG_LON, s.getString(TAG_LON));
                //map.put(TAG_TEL, s.getString(TAG_TEL));
                //map.put(TAG_FAX, s.getString(TAG_FAX));
                //map.put(TAG_OPEN_HR, s.getString(TAG_OPEN_HR));
                idList.add(s.getString(TAG_ID));    
                nameList.add(s.getString(TAG_NAME));
                districtList.add(s.getString(TAG_DISTRICT));
                // adding HashList to ArrayList
                shopList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        for (int i=0;i<shopList.size();i++)
        {
        	double lat = Double.parseDouble(shopList.get(i).get(TAG_LAT));
            double lon = Double.parseDouble(shopList.get(i).get(TAG_LON));
            GeoPoint point = new GeoPoint(
                (int) (lat * 1E6), 
                (int) (lon * 1E6));
			String name = shopList.get(i).get(TAG_NAME);
			String address = shopList.get(i).get(TAG_ADDRESS);
			int id = Integer.parseInt(shopList.get(i).get(TAG_ID));
			oli = new OverlayItem(point,name,address);
			Drawable pushpin = this.getResources().getDrawable(R.drawable.icon_location_pin);
			miol = new MyItemizedOverlay(pushpin,mv,id)
			{
				@Override
				protected boolean onBalloonTap(int index, OverlayItem item, MapView mapView) {			
					Bundle bundle = new Bundle();
			    	bundle.putInt("id", this.GetId());
			    	Intent intent = new Intent(getParent(), LocationWebView.class);
		        	TabGroupBase parentActivity = (TabGroupBase)getParent();
		        	intent.putExtras(bundle);
		        	parentActivity.startChildActivity("LocationWebView", intent);
					return true;
				}
			};
			overlay.add(miol);
			miol.addOverlay(oli);
        }
        
        mv.invalidate();
        
        ImageButton home_button = (ImageButton)findViewById(R.id.cellar_home_button);
        home_button.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				
 				finish();
 				
 			}
     	   
        });
        
        ImageButton mail_button = (ImageButton)findViewById(R.id.cellar_mail_button);
        mail_button.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				
 				Intent intent = new Intent(getParent(), NotificationMainActivity.class);
				TabGroupBase parentActivity = (TabGroupBase)getParent();
	        	parentActivity.startChildActivity("MyCellarsMainCallByMail", intent);
 				
 			}
     	   
        });
	}

	OnClickListener list_btn_listner = new OnClickListener() {
	    public void onClick(View v) {
	    	Bundle bundle = new Bundle();
	    	bundle.putStringArrayList("idList", idList);
	    	bundle.putStringArrayList("nameList", nameList);
	    	bundle.putStringArrayList("districtList", districtList);
	    	
	    	Intent intent = new Intent(getParent(), LocationList.class);
        	TabGroupBase parentActivity = (TabGroupBase)getParent();
        	intent.putExtras(bundle);
        	parentActivity.startChildActivity("LocationList", intent);
	    }
	};

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
