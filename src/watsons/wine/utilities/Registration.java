package watsons.wine.utilities;

import static watsons.wine.utilities.CommonUtilities.SENDER_ID;

import com.google.android.gcm.GCMRegistrar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class Registration {
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	public void openDatabase(Context context){
    	WatsonWineDB watsonWineDB = new WatsonWineDB();
        SQLiteDatabase temp = watsonWineDB.createOrOpenNotificationCountTable(context);
		temp.close();
		temp = watsonWineDB.createOrOpenNotificationRecordTable(context);
		temp.close();
    }
	
	public AsyncTask<Void, Void, Void> register_GCM(Context context){
		
		 GCMRegistrar.checkDevice(context);
	        GCMRegistrar.checkManifest(context);

			
	        final String regId = GCMRegistrar.getRegistrationId(context);
	        if (regId.equals("")) {
	        	Log.v("Osmands", "regId.equals('')");
	          GCMRegistrar.register(context, SENDER_ID);
	        } else {
	        	Log.v("Osmands", "regId = "+regId);
	        	//GCMRegistrar.unregister(context);
	        	 // Device is already registered on GCM, check server.
	            if (GCMRegistrar.isRegisteredOnServer(context)) {
	                // Skips registration.
	            	Log.v("Osmands", "GCMRegistrar.isRegisteredOnServer()");
	               // mDisplay.append(getString(R.string.already_registered) + "\n");
	            } else {
	                // Try to register again, but not in the UI thread.
	                // It's also necessary to cancel the thread onDestroy(),
	                // hence the use of AsyncTask instead of a raw thread.
	                final Context context2 = context;
	                mRegisterTask = new AsyncTask<Void, Void, Void>() {

	                    @Override
	                    protected Void doInBackground(Void... params) {
	                    	Log.v("Osmands", "ServerUtilities.register");
	                        boolean registered = ServerUtilities.register(context2, regId);
	                        
	                        // At this point all attempts to register with the app
	                        // server failed, so we need to unregister the device
	                        // from GCM - the app will try to register again when
	                        // it is restarted. Note that GCM will send an
	                        // unregistered callback upon completion, but
	                        // GCMIntentService.onUnregistered() will ignore it.
	                        if (!registered) {
	                        	Log.v("Osmand", "GCMRegistrar.unregister");
	                            GCMRegistrar.unregister(context2);
	                        }
	                        return null;
	                    }

	                    @Override
	                    protected void onPostExecute(Void result) {
	                    	Log.v("Osmands", "onPostExecute");
	                        mRegisterTask = null;
	                    }

	                };
	                mRegisterTask.execute(null, null, null);
	                return mRegisterTask;
	            } 
	        }
	        return null;
	}
}