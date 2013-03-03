package watsons.wine;
//
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Wine {
    public Bitmap photo;
    public String name;
    public String size;
    public String rating_rp;
    public String rating_ws;
    public String rating_jh;
    public String original_price;
    public String promote_price;
    public String url;
    public Wine(){
        super();
    }
    
    public Wine(String url, String name, String size, String original_price, String promote_price, 
    		String rating_rp, String rating_ws, String rating_jh) throws IOException {
        super();
        this.url = url;
        this.photo = null;//loadBitmap(url);
        this.name = name;
        this.size = size;
        this.original_price = original_price;
        this.promote_price = promote_price;
        this.rating_rp = rating_rp;
        this.rating_ws = rating_ws;
        this.rating_jh = rating_jh;
    }
    
    public static Bitmap loadBitmap(String url) throws IOException {
    	Bitmap bitmap = null;
    	try 
    	{
    		bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
    	}
    	catch (MalformedURLException e) 
    	{
    		e.printStackTrace();
    	} 
    	catch (IOException e)
    	{
    		//e.printStackTrace();
    	}
    	return bitmap;
    }
}
