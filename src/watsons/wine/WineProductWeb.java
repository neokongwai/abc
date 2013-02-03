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
import android.webkit.WebViewClient;
import android.widget.Toast;
 
public class WineProductWeb extends Activity implements OnTouchListener, Handler.Callback {
 
	private static String product_url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/product_info/";
	private WebView webView;
	private static final int CLICK_ON_WEBVIEW = 1;
	private static final int CLICK_ON_URL = 2;

	private final Handler handler = new Handler(this);

	private WebViewClient client;
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_webview);
		
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString("id");
        //Toast.makeText(ProductWebView.this, id, Toast.LENGTH_SHORT).show();
        String url = product_url + id;
 
        client = new WebViewClient(){ 
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) { 
                handler.sendEmptyMessage(CLICK_ON_URL);
                return false;
            } 
        }; 
		webView = (WebView) findViewById(R.id.product_webview);
		webView.loadUrl(url);
		webView.setOnTouchListener(this);
		WebSettings websetting = webView.getSettings();
		websetting.setJavaScriptEnabled(true);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	    if (v.getId() == R.id.product_webview && event.getAction() == MotionEvent.ACTION_DOWN){
	        handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
	    }
	    return false;
	}

	@Override
	public boolean handleMessage(Message msg) {
	    if (msg.what == CLICK_ON_URL){
	        handler.removeMessages(CLICK_ON_WEBVIEW);
	        return true;
	    }
	    if (msg.what == CLICK_ON_WEBVIEW){
	    	
	        Toast.makeText(this, "Icon clicked"+":"+msg.arg1+":"+msg.toString(), Toast.LENGTH_SHORT).show();
	        return true;
	    }
	    return false;
	}
}