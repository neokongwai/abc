package watsons.wine;
 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
 
@SuppressLint("SetJavaScriptEnabled")
public class FoodDishWeb extends Activity implements OnTouchListener, Handler.Callback {
 
	private static String dish_url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/dish_info/";
	private WebView webView;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_dish_webview);
		
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        //Toast.makeText(ProductWebView.this, id, Toast.LENGTH_SHORT).show();
        String url = dish_url + id;
 
		webView = (WebView) findViewById(R.id.food_dish_webview);
		webView.loadUrl(url);
		webView.setOnTouchListener(this);
		WebSettings websetting = webView.getSettings();
		websetting.setJavaScriptEnabled(true);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}