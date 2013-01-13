package watsons.wine;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class WatsonsWineActivity extends Activity {
	private static final int INITIAL_LATITUDE = 22420487;
	private static final int INITIAL_LONGITUDE = 114207216;
	private static final GeoPoint Central = new GeoPoint(INITIAL_LATITUDE, INITIAL_LONGITUDE);
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        /* remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                		WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        
        
        setContentView(R.layout.main);
        
        ImageView main_bg = (ImageView) findViewById(R.id.main_bg);
        main_bg.setOnClickListener(new View.OnClickListener()         
        {

            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), TabBarExample.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}