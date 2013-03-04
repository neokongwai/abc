package watsons.wine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class AboutUs extends Activity {
	/** Called when the activity is first created. */

	AsyncTask<Void, Void, Void> mRegisterTask;
	ImageView icon_wine, icon_cellar, icon_food, icon_events, icon_location,
			icon_inbox, icon_aboutus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.aboutus);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}


}