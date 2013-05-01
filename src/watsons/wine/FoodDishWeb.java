package watsons.wine;
 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
 
@SuppressLint("SetJavaScriptEnabled")
public class FoodDishWeb extends Activity implements OnTouchListener, Handler.Callback {
 
	private static String dish_url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/dish_info/";
	private WebView webView;
 
	String name;
	Bitmap icon;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_dish_webview);
		
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        //Toast.makeText(ProductWebView.this, id, Toast.LENGTH_SHORT).show();
        String url = dish_url + id;
        TextView tv = (TextView)findViewById(R.id.title_dish_text);
        name = bundle.getString("name");
        tv.setText(name);
        ImageView iv = (ImageView)findViewById(R.id.title_dish_img);
        icon = (Bitmap)bundle.getParcelable("image");
        iv.setImageBitmap(icon);
        
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),
				"fonts/DroidSerif-Bold.ttf");
		tv.setTypeface(tf);
		tv.setTextColor(Color.parseColor("#490000"));
		tv.setTextSize(16);
        
 
		webView = (WebView) findViewById(R.id.food_dish_webview);
		webView.loadUrl(url);
		webView.setOnTouchListener(this);
		webView.addJavascriptInterface(new WebAppInterface(this), "Android");
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