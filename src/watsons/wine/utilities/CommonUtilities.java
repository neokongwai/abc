/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package watsons.wine.utilities;

import com.facebook.android.Facebook;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Environment;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {
	
	public static final String cashImagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/watsons_wine/MyCellarsChash/";
	
	public static final String twitter_consumer_key = "WUvAHsxs77isIJeQ7c9ZJw";
	public static final String twitter_secret_key = "5A9VVKWtchXB9mwlQdadyXSu1y1fKFzCLIUJwu63HU";
	
	//private final static String FB_APP_ID = "515948498450411";
	private final static String FB_APP_ID = "484643194904731";
	
	public final static Facebook facebook = new Facebook(CommonUtilities.FB_APP_ID);

    /**
     * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
     */
    static final String SERVER_URL = "http://watsonwine.bull-b.com/CodeIgniter_2.1.3/index.php/api";

    /**
     * Google API project id registered to use GCM.
     */
    public static final String SENDER_ID = "664332638586";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Osmands";

    /**
     * Intent used to display a message in the screen.
     */
    static final String DISPLAY_MESSAGE_ACTION =
            "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    static final String EXTRA_MESSAGE = "message";
    
    /**
     * wheel string
     */
    public static final String wheelMenuRegion[] = new String[]{"----- Bordeaux -----", "Pauillac", "St. Julien", "Margaux", "St. Estephe",  "Pessac Leognan", "St. Emilion", "Pomerol", "Others",//0-9
    															"-------------", "Burgundy", "Rhone", "Loire", "South of France", "Alsace", "Italy", "Spain", "Portugal", "Germany",//10-19
    															"Austria", "----- Australia -----", "Barossa", "Mclaren Vale", "Clare Valley", "Adelaide Hills", "Coonawarra", "Victoria", "Western Australia", "New South Wales",//20-29
    															"Other", "-------------", "New Zealand", "Chile", "Argentina", "North America", "South Afria", "----- Champagne -----", "Vintage", "Non-Vintage",//30-39
    															"Rose", "-------------", "Sparkling", "Dessert", "Special Pack", "Beer", "Japan", "Cognac & Brandy", "Port & Sherry", "Spirits",//40-49
    															"Whiskies", "----- Accessories -----", "Glassware", "Wine Fridge", "Others", "-------------"};//50-55
    public static final String wheelMenuVintage[] = new String[]{"<1990", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012"};
    public static final String wheelMenuGrape[] = new String[]{"Cabernet Franc", "Cabernet Sauvignon", "Chardonnary", "Chenin Blanc", "Gewurztraminer", "Grenache", "Gruner Veltliner", "Malbec", "Merlot", "Muscat", "Nebbiolo", "Pinotage", "Pinot Gris", "Pinot Grigio", "Pinot Noir", "Riesling", "Sangiovese", "Sauvignon Blanc", "Semillon", "Shiraz/Syrah", "Tempranillo", "Zinfandel"};
    public static final String wheelMenuBody[] = new String[]{"Light-Bodied", "Light to Medium-Bodied", "Medium-Bodied", "Medium to Full-Bodied", "Full-Bodied"};
    public static final String wheelMenuColour[] = new String[]{"Colour", "Red", "White", "Rose", "Other"};
    public static final String wheelMenuPrice[] = new String[]{"Price", "$1 - $100", "$101 - $200", "$201 - $500", "$501 - $1,000", "$1,001 - $2,000", "$2,001 - $5,000", ">$5000"};
    public static final String wheelMenuSize[] = new String[]{"Size", "37.5CL", "75CL", "150CL", "Others"};
    public static final String wheelMenuSweetness[] = new String[]{"Sweetness", "Brut", "Dry", "Off Dry", "Medium Dry", "Sweet"};
    
    //public static boolean wheelShowing = false;
    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    
    public static boolean isConnectedNetwork(Context context) {  
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        State wifi = State.DISCONNECTED;
        State mobile = State.DISCONNECTED;
        if (conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null) {
        	wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();	
        } 
        if (conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
        	mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
        }
        
        return (wifi.equals(State.CONNECTED) || mobile.equals(State.CONNECTED));
    }
}
