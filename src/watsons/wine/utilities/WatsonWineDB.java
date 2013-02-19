package watsons.wine.utilities;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import watsons.wine.mycellars.MyCellarsUpdateItemsActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.android.sqlite.DBHelper;
import com.android.sqlite.DbConstants;

/**
 * Helper class used to communicate with the demo server.
 */
public final class WatsonWineDB {
	Context context_temp;
	String wineName_temp, region_temp,  vintage_temp,  grape_temp,  colour_temp,  body_temp,  sweetness_temp,  size_temp,  note_temp,  wineImage_temp;
	double price_temp;
	
	public boolean addToMyCellerFromWineList(Context context, String wineName, String region, String vintage, String grape, String colour, String body, String sweetness, String size, double price, String note, String wineImage){
		context_temp = context;
		wineName_temp = wineName;
		region_temp = region;
		vintage_temp = vintage;
		grape_temp = grape;
		if(!size.equals("Red") && !size.equals("Rose") && !size.equals("White") ) {
			colour ="Other";
			colour_temp = "Other";
		} else {
			colour_temp = colour;	
		}
		
		body_temp = body;
		sweetness_temp = sweetness;
		if(!size.equals("37.5CL") && !size.equals("75CL") && !size.equals("150CL") ) {
			size = "Others";
			size_temp = "Others";
		} else {
			size_temp = size;
		}
		note_temp = note; 
		price_temp = price;
		String cache_image_path = "/storage/sdcard0/watsons_wine/MyCellarsChash/";
		int slashIndex = wineImage.lastIndexOf('/');
		String filenameWithExtension;
		filenameWithExtension = wineImage.substring(slashIndex + 1);
		wineImage_temp = filenameWithExtension;
		//return crateNewMyCellerRecord(context, wineName, region, vintage, grape, colour, body, sweetness, size, price, 1, note, 0, "-", "-", "N", wineImage, "N");
		boolean result = crateNewMyCellerRecord(context, wineName, region, vintage, grape, colour, body, sweetness, size, price, 1, note, 0, "-", "-", "N", filenameWithExtension, "N");
		if (result){ 
			File imageDirectory = new File(cache_image_path);
			// have the object build the directory
			// structure, if needed.
			boolean created = imageDirectory.mkdirs();
			// Log.i("Osmands", "Created = " + created);
			
			String temp = "not create file!";
			boolean fileExists = new File(cache_image_path+ filenameWithExtension).isFile();
				
			if (!fileExists) {
				//temp = httpGetImage("http://watsonwine.bull-b.com/CodeIgniter_2.1.3/uploads/wine/",filenameWithExtension);
				temp = httpGetImage("https://www.watsonswine.com/WebShop/asset/images/prd/",filenameWithExtension);
				
			}
			Log.i("Osmands", "Get image = "+temp);
			if(CommonUtilities.isConnectedNetwork(context)){
				Log.i("Osmands", "isConnectedNetwork");
				FileUploadTask fileuploadtask = new FileUploadTask();  
	            fileuploadtask.execute("insert");
			}
			return true;
		}
		return false;
	}
	
	public boolean crateNewMyCellerRecord(Context context, String wineName, String region, String vintage, String grape, String colour, String body, String sweetness, String size, double price, int quantity, String note, int rating, String tasting_date, String occasion, String instock, String wineImage, String up_to_cms){
		SQLiteDatabase watsonWineDB = createOrOpenMyCellarTable(context);
		if (watsonWineDB != null) {
			ContentValues cv = new ContentValues();
			cv.put(DbConstants.MY_CELLAR_WINE_NAME, wineName);
			cv.put(DbConstants.MY_CELLAR_REGION, region);
			cv.put(DbConstants.MY_CELLAR_VINTAGE, vintage);
			cv.put(DbConstants.MY_CELLAR_GRAPE, grape);
			cv.put(DbConstants.MY_CELLAR_COLOUR, colour);
			cv.put(DbConstants.MY_CELLAR_BODY, body);
			cv.put(DbConstants.MY_CELLAR_SWEETNESS, sweetness);
			cv.put(DbConstants.MY_CELLAR_SIZE, size);
			cv.put(DbConstants.MY_CELLAR_PRICE, price);
			cv.put(DbConstants.MY_CELLAR_QUANTITY, quantity);
			cv.put(DbConstants.MY_CELLAR_NOTE, note);
			cv.put(DbConstants.MY_CELLAR_RATING, rating);
			cv.put(DbConstants.MY_CELLAR_TASTING_DATE, tasting_date);
			cv.put(DbConstants.MY_CELLAR_OCCASION, occasion);
			cv.put(DbConstants.MY_CELLAR_INSTOCK, instock);
			cv.put(DbConstants.MY_CELLAR_IMAGE, wineImage);
			cv.put(DbConstants.MY_CELLAR_CREATE_DATE, getDateTime());
			cv.put(DbConstants.MY_CELLAR_MODIFY_DATE, getDateTime());
			cv.put(DbConstants.MY_CELLAR_UP_TO_CMS, up_to_cms);
			
			watsonWineDB.insert(DbConstants.MY_CELLAR_TABLE_NAME, null, cv);
			/*watsonWineDB.execSQL("INSERT INTO " + DbConstants.MY_CELLAR_TABLE_NAME 
					+" values (null, '"+wineName+"', '"+region+"', '"+ vintage+"', '"+ grape+"', '"+ colour+"', '"+ body
					+"', '"+ sweetness+"', '"+ size+"', '"+ price+"', '"+ quantity+"', '"+ note+"', '"
					+ rating+"', '"+ tasting_date+"', '"+ occasion+"', '"+ instock +"', '"+wineImage+"', '"+getDateTime()+"', '"+up_to_cms
					+"')", null);*/
			watsonWineDB.close();
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean updateNewMyCellerRecord(Context context, int id, String wineName, String region, String vintage, String grape, String colour, String body, String sweetness, String size, double price, int quantity, String note, int rating, String tasting_date, String occasion, String instock, String wineImage, String up_to_cms){
		SQLiteDatabase watsonWineDB = createOrOpenMyCellarTable(context);
		if (watsonWineDB != null) {
			ContentValues cv = new ContentValues();
			cv.put(DbConstants.MY_CELLAR_WINE_NAME, wineName);
			cv.put(DbConstants.MY_CELLAR_REGION, region);
			cv.put(DbConstants.MY_CELLAR_VINTAGE, vintage);
			cv.put(DbConstants.MY_CELLAR_GRAPE, grape);
			cv.put(DbConstants.MY_CELLAR_COLOUR, colour);
			cv.put(DbConstants.MY_CELLAR_BODY, body);
			cv.put(DbConstants.MY_CELLAR_SWEETNESS, sweetness);
			cv.put(DbConstants.MY_CELLAR_SIZE, size);
			cv.put(DbConstants.MY_CELLAR_PRICE, price);
			cv.put(DbConstants.MY_CELLAR_QUANTITY, quantity);
			cv.put(DbConstants.MY_CELLAR_NOTE, note);
			cv.put(DbConstants.MY_CELLAR_RATING, rating);
			cv.put(DbConstants.MY_CELLAR_TASTING_DATE, tasting_date);
			cv.put(DbConstants.MY_CELLAR_OCCASION, occasion);
			cv.put(DbConstants.MY_CELLAR_INSTOCK, instock);
			cv.put(DbConstants.MY_CELLAR_IMAGE, wineImage);
			cv.put(DbConstants.MY_CELLAR_CREATE_DATE, getDateTime());
			cv.put(DbConstants.MY_CELLAR_MODIFY_DATE, getDateTime());
			cv.put(DbConstants.MY_CELLAR_UP_TO_CMS, up_to_cms);
			
			watsonWineDB.update(DbConstants.MY_CELLAR_TABLE_NAME, cv, "_id = "+id, null);
			/*watsonWineDB.execSQL("INSERT INTO " + DbConstants.MY_CELLAR_TABLE_NAME 
					+" values (null, '"+wineName+"', '"+region+"', '"+ vintage+"', '"+ grape+"', '"+ colour+"', '"+ body
					+"', '"+ sweetness+"', '"+ size+"', '"+ price+"', '"+ quantity+"', '"+ note+"', '"
					+ rating+"', '"+ tasting_date+"', '"+ occasion+"', '"+ instock +"', '"+wineImage+"', '"+getDateTime()+"', '"+up_to_cms
					+"')", null);*/
			watsonWineDB.close();
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean deleteMyCellerRecord(Context context, int id){
		SQLiteDatabase watsonWineDB = createOrOpenMyCellarTable(context);
		if (watsonWineDB != null) {
			watsonWineDB.delete(DbConstants.MY_CELLAR_TABLE_NAME, "_id = "+id, null);
			watsonWineDB.close();
			return true;
		} else {
			return false;
		}
		
	}
	
	public SQLiteDatabase createOrOpenMyCellarTable(Context context){
		try{
			DBHelper temp = new DBHelper(context); 
			SQLiteDatabase sampleDB = temp.getWritableDatabase();
			sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + DbConstants.MY_CELLAR_TABLE_NAME 
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+DbConstants.MY_CELLAR_WINE_NAME+" VARCHAR, "+DbConstants.MY_CELLAR_REGION+" VARCHAR, "
						+DbConstants.MY_CELLAR_VINTAGE+" VARCHAR, "
						+DbConstants.MY_CELLAR_GRAPE+" VARCHAR, "+DbConstants.MY_CELLAR_COLOUR+" VARCHAR, "+DbConstants.MY_CELLAR_BODY+" VARCHAR, "
						+DbConstants.MY_CELLAR_SWEETNESS+" VARCHAR, "+DbConstants.MY_CELLAR_SIZE+" VARCHAR, "+DbConstants.MY_CELLAR_PRICE+" REAL, "
						+DbConstants.MY_CELLAR_QUANTITY+" INTEGER Default 1, "+DbConstants.MY_CELLAR_NOTE+" TEXT, "
						+DbConstants.MY_CELLAR_RATING+" INTEGER DEFAULT 0, "+DbConstants.MY_CELLAR_TASTING_DATE+" VARCHAR, "+DbConstants.MY_CELLAR_OCCASION+" TEXT, "
						+DbConstants.MY_CELLAR_INSTOCK+" VARCHAR default 'N', "+DbConstants.MY_CELLAR_IMAGE+" VARCHAR, "+DbConstants.MY_CELLAR_CREATE_DATE+" VARCHAR, "
						+DbConstants.MY_CELLAR_MODIFY_DATE+" VARCHAR, "+DbConstants.MY_CELLAR_UP_TO_CMS+" VARCHAR, "+DbConstants.MY_CELLAR_SERVER_ID+" INTEGER Default -1);");
			Log.i("Osmands", "Created watsonwine.db");
			return sampleDB;
		} catch(SQLiteException	e){
			Log.i("Osmands", "Can't create watsonwine.db error = "+e);
			return null;
		}catch(SQLException	e){
			Log.i("Osmands", "Create " + DbConstants.MY_CELLAR_TABLE_NAME + " SQL error = "+e);
			return null;
		}
	}
	
	public SQLiteDatabase createOrOpenNotificationCountTable(Context context){
		try{
			Log.i("Osmands", "createOrOpenNotificationCountTable()");
			DBHelper temp = new DBHelper(context); 
			SQLiteDatabase sampleDB = temp.getWritableDatabase();
			String INIT_TABLE = "CREATE TABLE IF NOT EXISTS " + DbConstants.NOTIFICATION_COUNT_TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " 
									+DbConstants.NOTIFICATION_COUNT + " INT DEFAULT 0 );"; 
			sampleDB.execSQL(INIT_TABLE);
			Log.i("Osmands", "Created NotificationCountTable.db");
			return sampleDB;
		} catch(SQLiteException	e){
			Log.i("Osmands", "Can't open watsonwine.db error = "+e);
			return null;
		}catch(SQLException	e){
			Log.i("Osmands", "Create " + DbConstants.NOTIFICATION_COUNT_TABLE_NAME + " SQL error = "+e);
			return null;
		}
	}
	
	public SQLiteDatabase createOrOpenNotificationRecordTable(Context context){
		try{
			Log.i("Osmands", "createOrOpenNotificationRecordTable()");
			DBHelper temp = new DBHelper(context); 
			SQLiteDatabase sampleDB = temp.getWritableDatabase();
			String INIT_TABLE = "CREATE TABLE IF NOT EXISTS " + DbConstants.NOTIFICATION_TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                  DbConstants.NOTIFICATION_TITLE + " VARCHAR, "+DbConstants.NOTIFICATION_BODY+" VARCHAR, "+DbConstants.NOTIFICATION_TIME+" VARCHAR, "+DbConstants.NOTIFICATION_READ+" VARCHAR(1) DEFAULT 'N' );"; 
			sampleDB.execSQL(INIT_TABLE);
			Log.i("Osmands", "Created NotificationRecordTable.db");
			return sampleDB;
		} catch(SQLiteException	e){
			Log.i("Osmands", "Can't open watsonwine.db error = "+e);
			return null;
		}catch(SQLException	e){
			Log.i("Osmands", "Create " + DbConstants.NOTIFICATION_TABLE_NAME + " SQL error = "+e);
			return null;
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public static String httpGetImage(String url, String fileName) {
		HttpClient client = MySSLSocketFactory.createMyHttpClient();
		HttpGet request = new HttpGet();
		try {
			File root = Environment.getExternalStorageDirectory();
			// Log.i("Test", "root path = " + root.getAbsolutePath());
			BufferedOutputStream bout = new BufferedOutputStream(
					new FileOutputStream(root.getAbsolutePath()
							+ "/watsons_wine/MyCellarsChash/" + fileName));

			request.setURI(new URI(url + fileName));
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			// textView1.append("status.getStatusCode(): " +
			// status.getStatusCode() + "\n");
			// Log.d("Test", "Statusline: " + status);
			// Log.d("Test", "Statuscode: " + status.getStatusCode());

			HttpEntity entity = response.getEntity();
			// textView1.append("length: " + entity.getContentLength() + "\n");
			// textView1.append("type: " + entity.getContentType() + "\n");
			// Log.d("Test", "Length: " + entity.getContentLength());
			// Log.d("Test", "type: " + entity.getContentType());

			entity.writeTo(bout);

			bout.flush();
			bout.close();
			// textView1.append("OK");
			return "OK";
		} catch (URISyntaxException e) {
			return e.toString();
			// TODO Auto-generated catch block
			// textView1.append("URISyntaxException");
		} catch (ClientProtocolException e) {
			return e.toString();
			// TODO Auto-generated catch block
			// textView1.append("ClientProtocolException");
		} catch (IOException e) {
			return e.toString();
			// TODO Auto-generated catch block
			// textView1.append("IOException");
		}
	}
	
class FileUploadTask extends AsyncTask<Object, Integer, Void> {  
    	
        @Override  
        protected void onPreExecute() {    
        	
        }  
  
        @Override  
        protected Void doInBackground(Object... arg0) {  
        	Log.i("Osmands", "FileUploadTask doInBackground");
        	String URL = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/watsonswine_application/models/insert_mycellars_record.php";
        	String ba1 ="";
        	if (!wineImage_temp.equals("-") && wineImage_temp != null) {
	        	String cache_image_path = "/storage/sdcard0/watsons_wine/MyCellarsChash/";
	        	String realImagePathFromURI = cache_image_path+wineImage_temp;
	        	Log.i("Osmands", "FileUploadTask realImagePathFromURI = "+realImagePathFromURI);
	        	BitmapFactory.Options options = new BitmapFactory.Options();
	        	options.inJustDecodeBounds = false;
	        	options.inSampleSize = 2;
	        	Log.d("Osmands", "decode start");
	        	Bitmap bitmapOrg = BitmapFactory.decodeFile(realImagePathFromURI, options);
	        	Log.d("Osmands", "decode end");
	
	    		ByteArrayOutputStream bao = new ByteArrayOutputStream();
	    		Log.d("Osmands", "compress start");
	    		bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
	    		byte [] ba = bao.toByteArray();
	    		ba1=Base64.encodeBytes(ba); 
	    		Log.d("Osmands", "compress end");
        	}
	    		
    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    		
    		if (!wineImage_temp.equals("-") && wineImage_temp != null) {
    			nameValuePairs.add(new BasicNameValuePair("image",ba1));
    			nameValuePairs.add(new BasicNameValuePair("image_name",wineImage_temp));
    		} else {
    			nameValuePairs.add(new BasicNameValuePair("image_name","-"));
    		}
        	
    		nameValuePairs.add(new BasicNameValuePair("name",wineName_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("region",region_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("size",size_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("price",String.valueOf(price_temp))); 
    		nameValuePairs.add(new BasicNameValuePair("vintage",vintage_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("color",colour_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("grape",grape_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("body",body_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("sweetness",sweetness_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("rating","0")); 
    		nameValuePairs.add(new BasicNameValuePair("tasting_note",note_temp)); 
    		nameValuePairs.add(new BasicNameValuePair("tasting_date","-")); 
    		nameValuePairs.add(new BasicNameValuePair("occasion","-")); 
    		nameValuePairs.add(new BasicNameValuePair("in_stock","N")); 
    		nameValuePairs.add(new BasicNameValuePair("quantity","1"));

    		DBConnecter connect = new DBConnecter();
    		try {
    			JSONArray jArray = connect.connection(URL, nameValuePairs);
	    			JSONObject json_data = null;
	    			int server_mycellar_id = -1;
	    			for (int i = 0; i < jArray.length(); i++) {
	    				json_data = jArray.getJSONObject(i);
	    				Log.i("Osmands", "json_data ="+json_data);
	    				server_mycellar_id = json_data.getInt("max_id");
	    				Log.i("Osmands", "server_mycellar_id ="+server_mycellar_id);
	    			}
	    			if (server_mycellar_id != -1) {
	    				SQLiteDatabase sampleDB = context_temp.openOrCreateDatabase("watsonwine.db", 0, null);
	    			    Cursor c = sampleDB.rawQuery("SELECT MAX(_id) FROM " +DbConstants.MY_CELLAR_TABLE_NAME, null);
	    			    if (c != null ) {
	    		        	Log.i("Osmands", "c != null  ");
	    		            if  (c.moveToFirst()) {
	    		                do {
	    		                   // String id = c.getString(c.getColumnIndex("_id"));
	    		                    int id = c.getInt(0);
	    		                    Log.i("Osmands", "id = "+id);
	    		                    try{   
	    		                    	sampleDB.execSQL("UPDATE " +DbConstants.MY_CELLAR_TABLE_NAME +" set server_id = "+server_mycellar_id+", up_to_cms = 'Y' where _id = "+id+";");
	    		                    	Log.i("Osmands", "UPDATE " +DbConstants.MY_CELLAR_TABLE_NAME +" set server_id = "+server_mycellar_id+", up_to_cms = 'Y' where _id = "+id+";");
	    		                    }catch(SQLException	e){
	    		            			Log.e("Osmands", "UPDATE " +DbConstants.MY_CELLAR_TABLE_NAME +" set server_id = "+server_mycellar_id+", up_to_cms = 'Y' where _id = "+id+"; SQL error = "+e);
	    		            		}
	    		                }while (c.moveToNext());
	    		            } 
	    		        }
	    			}

    		}catch(Exception e){
    			Log.e("Osmands", "Error in http connection "+e.toString());
    			//Toast.makeText(getBaseContext(), "Can't upload the image, please try again leter!",
    			//		Toast.LENGTH_LONG).show();
    		} 		
    		return null;
        }  
  
        @Override  
        protected void onProgressUpdate(Integer... progress) {  
        }  
  
        @Override  
        protected void onPostExecute(Void result) {  
            try {    
            } catch (Exception e) {  
            }  
        }  
  
    }
}