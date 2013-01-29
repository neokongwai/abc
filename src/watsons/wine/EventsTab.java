package watsons.wine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class EventsTab extends Activity implements
		CalendarView.OnCellTouchListener {
	
	// url to make request
    private static String url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/list_events/";
    
    // JSON Node names
    private static final String TAG_EVENT = "events";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_DATE = "event_date";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_TIME = "event_time";
    private static final String TAG_URL = "url";
    private static final String TAG_NOTE = "note";
    private static final String TAG_ENQUIRY = "enquiry";
    
    Context mContext = EventsTab.this;
    JSONArray events = null;
    List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
    //List<String> dateList = new ArrayList<String>();
    List<Integer> dateList = new ArrayList<Integer>();
	
	public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";
	CalendarView mView = null;
	TextView mHit;
	Handler mHandler = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events_tab);
		// Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
 
        // getting JSON string from URL
        JSONObject json = jParser.getJSONFromUrl(url);
                
        try {
            // Getting Array of Contacts
        	events = json.getJSONArray(TAG_EVENT);

            // looping through All Contacts
            for(int i = 0; i < events.length(); i++){
                JSONObject c = events.getJSONObject(i);
 
                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String date = c.getString(TAG_DATE);
              
                HashMap<String, String> map = new HashMap<String, String>();
                // adding each child node to HashMap key => value
                map.put(TAG_ID, id);
                map.put(TAG_NAME, name);
                map.put(TAG_DATE, date);
 
                // adding HashList to ArrayList
                eventList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
        for (int i=0;i<eventList.size();i++)
        {
        	if (!eventList.get(i).get(TAG_DATE).equals(""))
        	{
        		String date,tmp;
        		int index;
        		System.out.println(eventList.get(i).get(TAG_DATE));
        		
	        	date = eventList.get(i).get(TAG_DATE);
	        	tmp = date.substring(0, 4);
	        	System.out.println(tmp);
	        	dateList.add(Integer.parseInt(tmp));
	        	index = date.indexOf("-");
	        	date = date.substring(index+1);
	        	index =  date.indexOf("-");
	        	tmp = date.substring(0,index);
	        	System.out.println(tmp);
	        	dateList.add(Integer.parseInt(tmp));
	        	date = date.substring(index+1);
	        	dateList.add(Integer.parseInt(date));
	        	System.out.println(date);
        	}
        }
        
		
		
		mView = (CalendarView) findViewById(R.id.calendar_view);

		mView.setOnCellTouchListener(this);
		mHandler.post(new Runnable() {
			public void run() {
				mView.drawDate(dateList);
			}
		});
	}

	public void onTouch(Cell cell) {

		int year = mView.getYear();
		int month = mView.getMonth();
		int day = cell.getDayOfMonth();
		day = cell.getDayOfMonth();
		if (mView.firstDay(day))
			mView.previousMonth();
		else if (mView.lastDay(day))
			mView.nextMonth();
		else
			return;

		mHandler.post(new Runnable() {
			public void run() {
				Toast.makeText(
						EventsTab.this,
						DateUtils.getMonthString(mView.getMonth(),
								DateUtils.LENGTH_LONG) + " " + mView.getYear(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}