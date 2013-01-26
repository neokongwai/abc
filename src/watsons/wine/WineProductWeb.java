package watsons.wine;
 
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
 
public class WineProductWeb extends Activity {
 
	private static String product_url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/product_info/";
	private WebView webView;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_webview);
		
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        //Toast.makeText(ProductWebView.this, id, Toast.LENGTH_SHORT).show();
        String url = product_url + id;
 
		webView = (WebView) findViewById(R.id.product_webview);
		webView.loadUrl(url);
	}
}