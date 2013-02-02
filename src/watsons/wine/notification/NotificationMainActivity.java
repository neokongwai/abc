package watsons.wine.notification;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.android.sqlite.DBHelper;
import com.android.sqlite.DbConstants;
import watsons.wine.notification.ItemListBaseAdapter;
import watsons.wine.R;
import watsons.wine.R.id;
import watsons.wine.R.layout;

public class NotificationMainActivity extends Activity {

	AsyncTask<Void, Void, Void> mRegisterTask;
	TextView mDisplay;
	DBHelper dbhelper;
	ArrayList<ItemDetails> results = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);
        clearNotification(0); 
        openDatabase();
        updateNotificationCount();
        Cursor cursor = getCursor();
        results = new ArrayList<ItemDetails>();
        while(cursor.moveToNext()){
        	String id = cursor.getString(0);
            String title = cursor.getString(1);
            String body = cursor.getString(2);
            String time = cursor.getString(3);
            String read = cursor.getString(4);
            Log.i("Osmands", "read = "+read);
            results.add(GetSearchResults(id, body, time, title, read));
        }
        ListView list = (ListView) findViewById(R.id.MyListView);  
  
        list.setAdapter(new ItemListBaseAdapter(NotificationMainActivity.this, results));  
        list.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Log.i("Osmands", "position = "+position);
	    		Log.i("Osmands", "results.get(position).getTitle() = "+results.get(position).getTitle());
	    		Log.i("Osmands", "results.get(position).getDate() = "+results.get(position).getDate());
	    		Log.i("Osmands", "results.get(position).getBody() = "+results.get(position).getBody());
	    		
	    		updateNotificationRead(results.get(position).getId());
	    		
				Intent intent = null;
				Bundle b = new Bundle();
	            b.putString("title", results.get(position).getTitle());
	            b.putString("body", results.get(position).getBody());  
	            b.putString("date",results.get(position).getDate());
	            b.putString("id",results.get(position).getId());
				intent = new Intent(getApplicationContext(), NotificationDetailActivity.class);
				intent.putExtras(b);
	    		startActivity(intent);
	    		NotificationMainActivity.this.finish();
	    	}
    	});
       
    }
    
    /*protected void onActivityResult(int requestCode, int resultCode, Intent data){  
		switch (resultCode){  
			case RESULT_OK:  
				Bundle b = data.getExtras();  
	            String id = b.getString("id");
				updateNotificationRead(id);
				getCursor();
		}  
	} */
    
    private ItemDetails GetSearchResults(String id, String body, String date, String title, String read) {
		Log.i("Osmands", "GetSearchResults");
		ItemDetails item_details = new ItemDetails();
		item_details.setId(id);
		item_details.setBody(body);
		item_details.setDate(date);
		item_details.setTitle(title);
		if (read.equals("Y")) {
			item_details.setRead(true);
		} else {
			item_details.setRead(false);
		}
		
		return item_details;
	}
    
    private void openDatabase(){
        dbhelper = new DBHelper(this); 
    }
    
    private Cursor getCursor(){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] columns = {DbConstants.NOTIFICATION_TITLE, DbConstants.NOTIFICATION_TIME, DbConstants.NOTIFICATION_READ};

        Cursor cursor = db.query(DbConstants.NOTIFICATION_TABLE_NAME, null, null, null, null, null, DbConstants.NOTIFICATION_TIME);
        startManagingCursor(cursor);

        return cursor;
    }
    
    private void updateNotificationCount(){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues(); 
        values.put(DbConstants.NOTIFICATION_COUNT, 0); 
        db.update(DbConstants.NOTIFICATION_COUNT_TABLE_NAME, values, null, null);
    }
    
    private void updateNotificationRead(String id){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues(); 
        values.put(DbConstants.NOTIFICATION_READ, "Y"); 
        db.update(DbConstants.NOTIFICATION_TABLE_NAME, values, "_id = "+id, null);
    }
    
    private void clearNotification(int notification_id){
  	  String ns = Context.NOTIFICATION_SERVICE;
  	  NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);
  	  mNotificationManager.cancel(notification_id);
  	 }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDatabase();
    }
    
    private void closeDatabase(){
        dbhelper.close(); 
    }
}
