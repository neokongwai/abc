package watsons.wine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FoodTab extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Food & Wine Tab Content */
		TextView textView = new TextView(this);
		textView.setText("Third Tab");
		setContentView(textView);

	}
}
