package watsons.wine;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class TabGroupBase extends ActivityGroup {

	private ArrayList<String> mIdList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mIdList == null) 
			mIdList = new ArrayList<String>();
	}
	
	@Override
	public void finishFromChild(Activity child) {
		LocalActivityManager manager = getLocalActivityManager();
		int index = mIdList.size()-1;
	
		if (index < 1) {
			finish();
			return;
		}
	
		manager.destroyActivity(mIdList.get(index), true);
		mIdList.remove(index);
		index--;
		String lastId = mIdList.get(index);
		Intent lastIntent = manager.getActivity(lastId).getIntent();
		Window newWindow = manager.startActivity(lastId, lastIntent);
		setContentView(newWindow.getDecorView());
	}
	
	public void startChildActivityNotAddId(String Id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(Id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			setContentView(window.getDecorView());
		}
	}
	
	public void startChildActivity(String Id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(Id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			mIdList.add(Id);
			setContentView(window.getDecorView());
		}
	}
	
	@Override
	public void onBackPressed () {
		int length = mIdList.size();
		//if ( length > 1) {
		if (mIdList.get(length-1).equals("FoodCuisineListAlter"))
		{
			Activity current = getLocalActivityManager().getActivity(mIdList.get(length-1));
			current.finish();
			length = mIdList.size();
		}
		Activity current = getLocalActivityManager().getActivity(mIdList.get(length-1));
		current.finish();	
		//}
	}
}