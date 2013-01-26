package watsons.wine;
 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
 
@SuppressLint("SetJavaScriptEnabled")
public class LocationWebView extends Activity {
 
	private static String shop_url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/shop_info/";
	private WebView webView;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_web);
		
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        int id = bundle.getInt("id");
        String url = shop_url + id;
 
		webView = (WebView) findViewById(R.id.location_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
	}
}