package watsons.wine;

import watsons.wine.utilities.WatsonWineDB;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class WebAppInterface extends Activity {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    //@JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        
    }
    
    public void sendTwitter(String wineName, String prd_id, String tasting_note) {
    	String message1 = "I've added " + wineName + "into my wish list. Let's have a wine gathering and try together.";
    	String message2 = "";
    	String message3 = "";
    	
    	if(!prd_id.isEmpty()) {
    		message2 = "\n" + "https://www.watsonswine.com/WebShop/BrowseProductDetail.do?prdid=" + prd_id;
    	}
    	if(!tasting_note.isEmpty()) {
    		message3 = "\n" + "Tasting note: " + tasting_note;
    	}
    	
    	final String message = message1 + message2 + message3;
    	
    	Runnable runnable = new Runnable() {
    		public void run() {
    			String url = "https://twitter.com/intent/tweet?text="+message;
    			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    			mContext.startActivity(intent);
    		}
    	};
    	runOnUiThread(runnable);
    }
    
    public void sendmail(String wineName, String prd_id, String tasting_note) {
    	String message1 = "I've added " + wineName + "into my wish list. Let's have a wine gathering and try together.";
    	String message2 = "";
    	String message3 = "";
    	
    	if(!prd_id.isEmpty()) {
    		message2 = "\n" + "https://www.watsonswine.com/WebShop/BrowseProductDetail.do?prdid=" + prd_id;
    	}
    	if(!tasting_note.isEmpty()) {
    		message3 = "\n" + "Tasting note: " + tasting_note;
    	}
    	
    	final String message = message1 + message2 + message3;
    	final String subject = "Share wine to you";
    	
    	System.out.println("msg"+message);
    	
    	Runnable runnable = new Runnable() {
    		public void run() {
    			//send email operation
    	    	Intent email = new Intent(Intent.ACTION_SEND);
    	    	//email.putExtra(Intent.EXTRA_CC, new String[]{ to});
    	    	//email.putExtra(Intent.EXTRA_BCC, new String[]{to});
    	    	email.putExtra(Intent.EXTRA_SUBJECT, subject);
    	    	email.putExtra(Intent.EXTRA_TEXT, message);
    	    	//need this to prompts email client only
    	    	email.setType("message/rfc822");
    	    	mContext.startActivity(email);
    		}
    	};
    	
    	runOnUiThread(runnable);
    }
    
    public void addToCeller(String wineName, String region, String vintage, String grape, String colour, String body, String sweetness, String size, double price, String note, String wineImage) {
    	//make sure string not empty
    	if(wineName.isEmpty()) {
    		wineName = "-";
    	}
    	if(region.isEmpty()) {
    		region = "-";
    	}
    	if(vintage.isEmpty()) {
    		vintage = "-";
    	}
    	if(grape.isEmpty()) {
    		grape = "-";
    	}
    	if(colour.isEmpty()) {
    		colour = "-";
    	}
    	if(body.isEmpty()) {
    		body = "-";
    	}
    	if(sweetness.isEmpty()) {
    		sweetness = "-";
    	}
    	if(size.isEmpty()) {
    		size = "-";
    	}
    	if(note.isEmpty()) {
    		note = "-";
    	}
    	
    	WatsonWineDB wwdb = new WatsonWineDB();
    	System.out.println(wineName+" "+region+" "+vintage+" "+grape+" "+colour+" "+body+" "+sweetness+" "+size+" "+price+" "+note+" "+wineImage);
    	
    	Boolean return_bool = wwdb.addToMyCellerFromWineList(mContext, wineName, region, vintage, grape, colour, body, sweetness, size, price, note, wineImage);
    	if(return_bool) {
    		this.showToast("Wine is added into your celler");
    	}else {
    		this.showToast("Wine can't added into your celler");
    	}
    }
}