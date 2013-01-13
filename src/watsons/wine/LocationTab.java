package watsons.wine;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.os.Bundle;

public class LocationTab extends MapActivity {
	private static final int INITIAL_ZOOM_LEVEL = 14;
	private static final int INITIAL_LATITUDE = 22290000;
	private static final int INITIAL_LONGITUDE = 114170000;
	private static final GeoPoint Central = new GeoPoint(INITIAL_LATITUDE, INITIAL_LONGITUDE);
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Location Tab Content */
		setContentView(R.layout.location_tab);

		MapView mv = (MapView) findViewById(R.id.mapview);
	    mv.setBuiltInZoomControls(true);
	    
	    MapController mc = mv.getController();
	    mc.setZoom(INITIAL_ZOOM_LEVEL);
		mc.setCenter(Central);
		mv.getMapCenter();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
