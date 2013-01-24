package watsons.wine;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupCellar extends TabGroupBase {
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("CellarTab", new Intent(this,CellarTab.class));	
	}
}