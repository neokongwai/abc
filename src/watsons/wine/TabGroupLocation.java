package watsons.wine;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupLocation extends TabGroupBase {
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("LocationTab", new Intent(this,LocationTab.class));	
	}
}