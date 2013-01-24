package watsons.wine;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupFood extends TabGroupBase {
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("FoodTab", new Intent(this,FoodTab.class));	
	}
}