package watsons.wine;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser() {
 
    }
 
    public JSONObject getJSONFromUrl(String url) {
 
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = //"{\"countries\":[{\"id\":\"114\",\"name\":\"Bordeaux\",\"provinces\":[{\"id\":\"115\",\"name\":\"Pauillac\",\"product_count\":\"85\"},{\"id\":\"116\",\"name\":\"St. Julien\",\"product_count\":\"68\"},{\"id\":\"117\",\"name\":\"Margaux\",\"product_count\":\"64\"},{\"id\":\"118\",\"name\":\"St. Estephe\",\"product_count\":\"28\"},{\"id\":\"119\",\"name\":\"Pessac Leognan\",\"product_count\":\"46\"},{\"id\":\"121\",\"name\":\"St. Emilion\",\"product_count\":\"121\"},{\"id\":\"122\",\"name\":\"Pomerol\",\"product_count\":\"55\"},{\"id\":\"123\",\"name\":\"Others\",\"product_count\":\"21\"}],\"product_count\":\"488\"},{\"id\":\"131\",\"name\":\"Burgundy\",\"provinces\":[],\"product_count\":\"182\"},{\"id\":\"133\",\"name\":\"Rhone\",\"provinces\":[],\"product_count\":\"58\"},{\"id\":\"135\",\"name\":\"Loire\",\"provinces\":[],\"product_count\":\"13\"},{\"id\":\"137\",\"name\":\"South of France\",\"provinces\":[],\"product_count\":\"7\"},{\"id\":\"139\",\"name\":\"Alsace\",\"provinces\":[],\"product_count\":\"16\"},{\"id\":\"140\",\"name\":\"Italy\",\"provinces\":[],\"product_count\":\"97\"},{\"id\":\"143\",\"name\":\"Spain\",\"provinces\":[],\"product_count\":\"72\"},{\"id\":\"145\",\"name\":\"Portugal\",\"provinces\":[],\"product_count\":\"6\"},{\"id\":\"147\",\"name\":\"Germany\",\"provinces\":[],\"product_count\":\"37\"},{\"id\":\"149\",\"name\":\"Austria\",\"provinces\":[],\"product_count\":\"6\"},{\"id\":\"150\",\"name\":\"Australia\",\"provinces\":[{\"id\":\"154\",\"name\":\"Barossa\",\"product_count\":\"55\"},{\"id\":\"156\",\"name\":\"Mclaren Vale\",\"product_count\":\"26\"},{\"id\":\"158\",\"name\":\"Clare Valley\",\"product_count\":\"14\"},{\"id\":\"160\",\"name\":\"Adelaide Hills\",\"product_count\":\"6\"},{\"id\":\"162\",\"name\":\"Coonawarra\",\"product_count\":\"21\"},{\"id\":\"164\",\"name\":\"Victoria\",\"product_count\":\"31\"},{\"id\":\"166\",\"name\":\"Western Australia\",\"product_count\":\"31\"},{\"id\":\"168\",\"name\":\"New South Wales\",\"product_count\":\"2\"},{\"id\":\"170\",\"name\":\"Others\",\"product_count\":\"16\"}],\"product_count\":\"202\"},{\"id\":\"172\",\"name\":\"New Zealand\",\"provinces\":[],\"product_count\":\"59\"},{\"id\":\"174\",\"name\":\"Chile\",\"provinces\":[],\"product_count\":\"35\"},{\"id\":\"176\",\"name\":\"Argentina\",\"provinces\":[],\"product_count\":\"36\"},{\"id\":\"178\",\"name\":\"North America\",\"provinces\":[],\"product_count\":\"91\"},{\"id\":\"180\",\"name\":\"South Africa\",\"provinces\":[],\"product_count\":\"13\"},{\"id\":\"182\",\"name\":\"Champagne\",\"provinces\":[{\"id\":\"183\",\"name\":\"Vintage\",\"product_count\":\"23\"},{\"id\":\"184\",\"name\":\"Non-Vintage\",\"product_count\":\"33\"},{\"id\":\"185\",\"name\":\"Rose\",\"product_count\":\"17\"}],\"product_count\":\"73\"},{\"id\":\"186\",\"name\":\"Sparkling\",\"provinces\":[],\"product_count\":\"29\"},{\"id\":\"201\",\"name\":\"Rose\",\"provinces\":[],\"product_count\":\"10\"},{\"id\":\"202\",\"name\":\"Dessert\",\"provinces\":[],\"product_count\":\"60\"},{\"id\":\"204\",\"name\":\"Special Pack\",\"provinces\":[],\"product_count\":\"9\"},{\"id\":\"205\",\"name\":\"Beer\",\"provinces\":[],\"product_count\":\"9\"},{\"id\":\"206\",\"name\":\"Japan\",\"provinces\":[],\"product_count\":\"25\"},{\"id\":\"207\",\"name\":\"Cognac & Brandy\",\"provinces\":[],\"product_count\":\"11\"},{\"id\":\"209\",\"name\":\"Port & Sherry\",\"provinces\":[],\"product_count\":\"21\"},{\"id\":\"212\",\"name\":\"Spirits\",\"provinces\":[],\"product_count\":\"22\"},{\"id\":\"217\",\"name\":\"Whiskies\",\"provinces\":[],\"product_count\":\"56\"},{\"id\":\"218\",\"name\":\"Accessories\",\"provinces\":[{\"id\":\"219\",\"name\":\"Glassware\",\"product_count\":\"32\"},{\"id\":\"221\",\"name\":\"Others\",\"product_count\":\"52\"},{\"id\":\"224\",\"name\":\"Wine Fridge\",\"product_count\":\"38\"}],\"product_count\":\"122\"}]}";
            		sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return null;
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            return null;
        }
 
        // return JSON String
        return jObj;
 
    }
}