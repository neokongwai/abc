package watsons.wine.notification;

import watsons.wine.R;
import watsons.wine.R.id;
import watsons.wine.R.layout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class NotificationDetailActivity extends Activity {
	String id = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_detail);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        ((TextView)findViewById(R.id.detailBody)).setText(bundle.getString("body"));
        ((TextView)findViewById(R.id.detailTitle)).setText(bundle.getString("title"));
        ((TextView)findViewById(R.id.detailDate)).setText(bundle.getString("date"));
      
    }
    
    @Override
    public boolean onKeyDown(int i, KeyEvent event) {
		// TODO Auto-generated method stub
		if(i == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(getApplicationContext(),NotificationMainActivity.class);
			startActivity(intent);
			this.finish();
			return true;
		}
		return false;
	}
}
