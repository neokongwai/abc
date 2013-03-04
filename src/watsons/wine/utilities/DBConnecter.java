package watsons.wine.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class DBConnecter {
	
	public JSONArray connection(String URL, ArrayList<NameValuePair> nameValuePairs) throws JSONException{
		String result = null;

		InputStream is = null;

		StringBuilder sb = null;

		// http post

		try {
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL);

			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();

			is = entity.getContent();

		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection" + e.toString());

		}

		try {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, "UTF8"), 8);

			sb = new StringBuilder();

			sb.append(reader.readLine() + "\n");

			String line = "0";

			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");

			}

			is.close();

			result = sb.toString();

		} catch (Exception e) {

			Log.e("log_tag", "Error converting result " + e.toString());

		}

		// paring data
		JSONArray jArray = null;
		try {

			jArray = new JSONArray(result);
			

		} catch (JSONException e1) {
			throw new JSONException(e1.toString());

		}
		return jArray;
	}
	
}