package watsons.wine;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabBarExample extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		/** TabHost will have Tabs */
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		/**
		 * TabSpec used to create a new tab. By using TabSpec only we can able
		 * to setContent to the tab. By using TabSpec setIndicator() we can set
		 * name to tab.
		 */

		/** tid1 is firstTabSpec Id. Its used to access outside. */
		TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
		TabSpec secondTabSpec = tabHost.newTabSpec("tid2");
		TabSpec thirdTabSpec = tabHost.newTabSpec("tid3");
		TabSpec fourthTabSpec = tabHost.newTabSpec("tid4");
		TabSpec fifthTabSpec = tabHost.newTabSpec("tid5");

		/** TabSpec setIndicator() is used to set name for the tab. */
		/** TabSpec setContent() is used to set content for a particular tab. */
		firstTabSpec.setIndicator("First Tab Name").setContent(
				new Intent(this, FirstTab.class));
		secondTabSpec.setIndicator("Second Tab Name").setContent(
				new Intent(this, SecondTab.class));
		thirdTabSpec.setIndicator("Third Tab Name").setContent(
				new Intent(this, ThirdTab.class));
		fourthTabSpec.setIndicator("Fourth Tab Name").setContent(
				new Intent(this, FourthTab.class));
		fifthTabSpec.setIndicator("Fifth Tab Name").setContent(
				new Intent(this, FifthTab.class));

		/** Add tabSpec to the TabHost to display. */
		tabHost.addTab(firstTabSpec);
		tabHost.addTab(secondTabSpec);
		tabHost.addTab(thirdTabSpec);
		tabHost.addTab(fourthTabSpec);
		tabHost.addTab(fifthTabSpec);

	}
}