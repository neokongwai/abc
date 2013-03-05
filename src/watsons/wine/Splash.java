package watsons.wine;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Handler hd = new Handler();
		hd.postDelayed(new Runnable(){
			@Override
			public void run() {
				Intent intent = new Intent(Splash.this, WatsonsWineActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1500);
	}


}