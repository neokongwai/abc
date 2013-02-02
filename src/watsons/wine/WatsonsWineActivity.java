package watsons.wine;

import watsons.wine.utilities.WatsonWineDB;

import watsons.wine.utilities.Registration;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class WatsonsWineActivity extends Activity {
    /** Called when the activity is first created. */
	
	AsyncTask<Void, Void, Void> mRegisterTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //**********Create My Cellars Table*************
        WatsonWineDB temp = new WatsonWineDB();
        temp.createOrOpenMyCellarTable(this);
        //**********Create My Cellars Table*************
        
        //**********Create Notification and notification count Table*************
        Registration reg = new Registration();
        reg.openDatabase(this);
        //**********Create Notification and notification count Table*************
        //**********Reqister GCM*************
        mRegisterTask = reg.register_GCM(this);
        //**********Reqister GCM*************

        ImageView main_bg = (ImageView) findViewById(R.id.main_bg);
        main_bg.setOnClickListener(new View.OnClickListener()         
        {

            public void onClick(View v) {
            	// Perform action on click
            	Intent myIntent = new Intent(v.getContext(), TabGroupView.class);
                startActivity(myIntent);
            }
        });
    }
    
    @Override
    protected void onDestroy() {
    	Log.v("Osmands", "onDestroy()");
        if (mRegisterTask != null) {
        	boolean temp = mRegisterTask.cancel(true);
        	Log.v("Osmands", "mRegisterTask.cancel = "+temp);
           
        }
        //GCMRegistrar.onDestroy(this);
        super.onDestroy();
    }
}