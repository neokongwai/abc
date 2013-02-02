package watsons.wine;

import static watsons.wine.utilities.CommonUtilities.SENDER_ID;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import com.android.sqlite.DbConstants;
import watsons.wine.notification.NotificationMainActivity;
import watsons.wine.utilities.ServerUtilities;
import watsons.wine.R;
import com.google.android.gcm.GCMBaseIntentService;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
       // displayMessage(context, getString(R.string.gcm_registered));
        ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
       // displayMessage(context, getString(R.string.gcm_unregistered));
        /*if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }*/
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        //String message = getString(R.string.gcm_message);
      //  displayMessage(context, message);
        // notifies user
        generateNotification(context, "", intent);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
       // String message = getString(R.string.gcm_deleted, total);
      //  displayMessage(context, message);
        // notifies user
        generateNotification(context, "", null);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
       // displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
       // displayMessage(context, getString(R.string.gcm_recoverable_error,errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message, Intent gcm_intent) {
    	JSONObject jsonObject;
    	Log.i("Osmands", "gcm_intent = "+gcm_intent);
    	Bundle extras = gcm_intent.getExtras();
    	Log.i("Osmands", "extras = "+extras);

        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        String title ="";
        SQLiteDatabase sampleDB = context.openOrCreateDatabase("watsonwine.db", MODE_PRIVATE, null);
        Cursor c = sampleDB.rawQuery("SELECT _id, count FROM " +DbConstants.NOTIFICATION_COUNT_TABLE_NAME, null);
        if (c != null ) {
        	Log.i("Osmands", "c != null  ");
            if  (c.moveToFirst()) {
                do {
                   // String id = c.getString(c.getColumnIndex("_id"));
                    int count = c.getInt(c.getColumnIndex(DbConstants.NOTIFICATION_COUNT));
                    Log.i("Osmands", "count = "+count);
                    count++;
                    title = count + " New Messages";
                    try{
                    	sampleDB.execSQL("UPDATE " +DbConstants.NOTIFICATION_COUNT_TABLE_NAME +" set count = "+count+";");
                    	Log.i("Osmands", "UPDATE " +DbConstants.NOTIFICATION_COUNT_TABLE_NAME +" set count = "+count+";");
                    }catch(SQLException	e){
            			Log.i("Osmands", "Insert into " + DbConstants.NOTIFICATION_COUNT_TABLE_NAME + "(count) Values ("+count+"); SQL error = "+e);
            		}
                }while (c.moveToNext());
            } else {
            	try{
            		 title = "1 New Message";
	            	sampleDB.execSQL("INSERT INTO " +DbConstants.NOTIFICATION_COUNT_TABLE_NAME +" (count) Values (1);");
	            	Log.i("Osmands", "Can INSERT INTO " +DbConstants.NOTIFICATION_COUNT_TABLE_NAME +" (count) Values (1);");
	            }catch(SQLException	e){
	    			Log.i("Osmands", "Insert into " + DbConstants.NOTIFICATION_COUNT_TABLE_NAME + "(count) Values (1); SQL error = "+e);
	    		}
            }
        }
		
		message = extras.getString("contentTitle");
		try{
			sampleDB.execSQL("INSERT INTO " +DbConstants.NOTIFICATION_TABLE_NAME +" ("+DbConstants.NOTIFICATION_TITLE +", "+DbConstants.NOTIFICATION_BODY +", "+DbConstants.NOTIFICATION_TIME +") Values ('"+message+"', '"+extras.getString("contentText")+"', '"+extras.getString("messageTime")+"');");
			Log.i("Osmands", "INSERT INTO " +DbConstants.NOTIFICATION_TABLE_NAME +" ("+DbConstants.NOTIFICATION_TITLE +", "+DbConstants.NOTIFICATION_BODY +", "+DbConstants.NOTIFICATION_TIME +") Values ('"+message+"', '"+extras.getString("contentText")+"', '"+extras.getString("messageTime")+"');");
		} catch(SQLException e){
			Log.i("Osmands", "Insert into " + DbConstants.NOTIFICATION_TABLE_NAME +" ("+DbConstants.NOTIFICATION_TITLE +", "+DbConstants.NOTIFICATION_BODY +", "+DbConstants.NOTIFICATION_TIME +") Values ('"+message+"', '"+extras.getString("contentText")+"', '"+extras.getString("messageTime")+"'); SQL error = "+e);
		}
		sampleDB.close();
        
        Intent notificationIntent = new Intent(context, NotificationMainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }
    
    /*private static Cursor getCursor(Context context){
    	DBHelper dbhelper = new DBHelper(context); 
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] columns = {_ID, NOTIFICATION_COUNT};

        Cursor cursor = db.query(NOTIFICATION_TABLE_NAME, columns, null, null, null, null, null);

        return cursor;
    }*/

}