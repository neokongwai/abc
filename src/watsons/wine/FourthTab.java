package watsons.wine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FourthTab extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Fourth Tab Content */
		TextView textView = new TextView(this);
		textView.setText("Fourth Tab");
		setContentView(textView);

	}
}
