package watsons.wine;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupEvents extends TabGroupBase {
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("EventsTab", new Intent(this,EventsTab.class));	
	}
}