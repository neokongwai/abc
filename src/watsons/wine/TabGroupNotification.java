package watsons.wine;

import watsons.wine.notification.NotificationMainActivity;
import android.content.Intent;
import android.os.Bundle;

public class TabGroupNotification extends TabGroupBase {
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("NotificationMainTab", new Intent(this,NotificationMainActivity.class));	
	}
}