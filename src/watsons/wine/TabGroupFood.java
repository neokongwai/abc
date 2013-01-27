package watsons.wine;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupFood extends TabGroupBase {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startChildActivity("FoodCuisineList", new Intent(this,FoodCuisineList.class));	
	}
	
	@Override
	public void onBackPressed() {
		if (Constants.FOOD_CUISINE == true && Constants.AT_FOOD_CUISINE == true)
		{
			Constants.FOOD_CUISINE = false;
			startChildActivity("FoodCuisineListAlter", new Intent(this,FoodCuisineList.class));	
		}
		else
		{
			Constants.AT_FOOD_CUISINE = false;
			super.onBackPressed();
		}
	}
}