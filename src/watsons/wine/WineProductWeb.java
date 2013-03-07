package watsons.wine;
 
import com.facebook.android.DialogError;
import com.facebook.android.FacebookError;

import watsons.wine.utilities.BaseDialogListener;
import watsons.wine.utilities.CommonUtilities;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
 
public class WineProductWeb extends Activity implements OnTouchListener, Handler.Callback {
 
	private static String product_url = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api/product_info/";
	private WebView webView;
 
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_webview);
		
		// Receive Parameter
        Bundle bundle = this.getIntent().getExtras();
        final String id = bundle.getString("id");
        final String name = bundle.getString("name");
        final String note = bundle.getString("note");
        final String photo = bundle.getString("photo");
        //Toast.makeText(ProductWebView.this, id, Toast.LENGTH_SHORT).show();
        String url = product_url + id;
 
		webView = (WebView) findViewById(R.id.product_webview);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				System.out.println("xxx");
			}
			
			@Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		    	System.out.println("url=>>>>"+url);
		    	System.out.println("Host Url=>>>>"+Uri.parse(url).getHost());
		        if (Uri.parse(url).getHost().equals("watsonwine.bull-b.com")) {
		            // This is my web site, so do not override; let my WebView load the page
		        	System.out.println("Inside fb.com");
		        	webView.requestFocus(View.FOCUS_DOWN);
		        	Bundle params = new Bundle();
		            params.putString("description", "I've added "+name+" into my wish list. Let's have a wine gathering and try together."+url+" Tasting note: "+note);
		            params.putString("name", name);
		            if (!photo.equals("-") && photo != null) {
		               params.putString("picture", "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/uploads/wine/"+photo);
		            }
		            params.putString("link", url);
		            CommonUtilities.facebook.dialog(WineProductWeb.this.getParent(), "feed", params, new UpdateStatusListener());
		        	return true;
		        }
		        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
		        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		        startActivity(intent);
		        return true;
		    }
		});
		webView.setOnTouchListener(this);
		webView.addJavascriptInterface(new WebAppInterface(this), "Android");
		WebSettings websetting = webView.getSettings();
		websetting.setJavaScriptEnabled(true);
	}
	
	public class UpdateStatusListener extends BaseDialogListener {
	    @Override
	    public void onComplete(Bundle values) {
	        final String postId = values.getString("post_id");
	        if (postId != null) {
	        } else {
	        }
	    }

	    @Override
	    public void onFacebookError(FacebookError error) {
	    Log.i("Osmands", "Facebook Error: " + error.getMessage());
	    }

	    @Override
	    public void onCancel() {
	    }
	
		@Override
		public void onError(DialogError e) {
		// TODO Auto-generated method stub
		}
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
	
	private class MyWebViewClient extends WebViewClient {
	}
}