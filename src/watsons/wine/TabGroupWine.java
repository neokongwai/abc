package watsons.wine;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupWine extends TabGroupBase {
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("WineListTab", new Intent(this,WineListTab.class));	
	}
}