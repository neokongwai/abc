package watsons.wine;

import android.content.Context;
import android.widget.Toast;
import watsons.wine.utilities.*;

public class WebAppInterface {
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