package watsons.wine.mycellars;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.android.sqlite.DBHelper;
import com.android.sqlite.DbConstants;
import com.facebook.android.DialogError;
import com.facebook.android.FacebookError;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import watsons.wine.R;
import watsons.wine.TabGroupBase;
import watsons.wine.utilities.BaseDialogListener;
import watsons.wine.utilities.CommonUtilities;
import watsons.wine.utilities.TwitterApp;
import watsons.wine.utilities.TwitterApp.TwDialogListener;
import watsons.wine.utilities.WatsonWineDB;

import android.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.ResolveInfo;

public class MyCellarsWineDetail extends Activity{
	
	DBHelper dbhelper;
	int windID;
	int width;
	
	int row;
	int output_row;
	int whellMenuNumber;
	private boolean wheelScrolled = false;
	ArrayList<String> wineDetail = null;
	String wheelMenu1[] = new String[]{"Right Arm", "Left Arm", "R-Abdomen", "L-Abdomen", "Right Thigh", "Left Thigh"};
	String wheelMenuVintage[] = new String[]{"1", "2", "3", "4", "5", "6"};
	
	int[] details_table_view_id = new int[]{R.id.tableRow1, R.id.tableRow2, R.id.tableRow3, R.id.tableRow4, R.id.tableRow5, R.id.tableRow6, R.id.tableRow7, R.id.tableRow8, R.id.tableRow9};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_wine_detail);
        Display display = getWindowManager().getDefaultDisplay();
  		width = display.getWidth();
        Bundle bundle = getIntent().getExtras();
        wineDetail = bundle.getStringArrayList("wineDetail");
        windID = Integer.valueOf(wineDetail.get(0));
      /*  ((TextView) findViewById(R.id.cellar_wine_name)).setText(wineDetail.get(1));
        ((TextView) findViewById(R.id.cellar_table_region)).setText(wineDetail.get(5));
        ((TextView) findViewById(R.id.cellar_table_vintage)).setText(wineDetail.get(6));
        ((TextView) findViewById(R.id.cellar_table_grape)).setText(wineDetail.get(7));
        ((TextView) findViewById(R.id.cellar_table_colour)).setText(wineDetail.get(8));
        ((TextView) findViewById(R.id.cellar_table_body)).setText(wineDetail.get(9));
        ((TextView) findViewById(R.id.cellar_table_sweetness)).setText(wineDetail.get(10));
        ((TextView) findViewById(R.id.cellar_table_size)).setText(wineDetail.get(11));
        ((TextView) findViewById(R.id.cellar_table_price)).setText(wineDetail.get(12));
        ((TextView) findViewById(R.id.cellar_table_quantity)).setText(wineDetail.get(13));
        ((TextView) findViewById(R.id.cellar_table_testing_date)).setText(wineDetail.get(15));
        ((TextView) findViewById(R.id.cellar_table_occasion)).setText(wineDetail.get(16));
        ((TextView) findViewById(R.id.cellar_table_note)).setText(wineDetail.get(14));
        int favourite = Integer.valueOf(wineDetail.get(3));
        ArrayList<ImageView> itemView = new ArrayList<ImageView>();
        itemView.add((ImageView) findViewById(R.id.cellar_table_rating_0));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_1));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_2));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_3));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_4));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_5));
		
		if (wineDetail.get(2).equals("N")) {
			((ImageButton) findViewById (R.id.cellar_wish_list_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
		}else {
			((ImageButton) findViewById (R.id.cellar_in_stock_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
		}
		
		for (int i=0; i <favourite; i++) {
			itemView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_wine_glass_full));
		}
		for (int i=favourite; i <6; i++) {
			itemView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_wine_glass));
		}
		
		if (wineDetail.get(4).equals("-") || wineDetail.get(4) == null) {
			((ImageView) findViewById (R.id.cellar_details_wine_image)).setImageResource(R.drawable.bg_photo_container_camera);
		} else {
			String cache_image_path = "/storage/sdcard0/watsons_wine/MyCellarsChash/";
			File imgFile = new File(cache_image_path+wineDetail.get(4));
			if (imgFile.exists()) {
				//holder.itemImageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_photo_container_small));
				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				((ImageView) findViewById (R.id.cellar_details_wine_image)).setImageBitmap(myBitmap);
				//holder.newsImage.setBackgroundDrawable(new BitmapDrawable(myBitmap));
				Log.i("osmand", "setImageBitmap");
			}
			
		}
        
        ((TextView) findViewById(R.id.cellar_added_date)).setText("Added: "+wineDetail.get(17).substring(0, wineDetail.get(17).length()-3));*/
       
        
        ImageButton tab = (ImageButton) findViewById(R.id.cellar_details_tab);
        tab.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP){
	                float x = event.getX();
	                if (x < width/3){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_wine_top));
	                	for(int i =0; i<details_table_view_id.length; i++){
		                	 TableRow temp = (TableRow)findViewById(details_table_view_id[i]);
		                	 temp.setVisibility(View.VISIBLE);
	                	 }
	                	 TableRow temp = (TableRow)findViewById(R.id.tableRow10);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow12);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13);
	                	 temp.setVisibility(View.GONE);
	                }
	                if (x<width/3*2 && x > width/3){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_profile_top));
	                	 for(int i =0; i<details_table_view_id.length; i++){
		                	 TableRow temp = (TableRow)findViewById(details_table_view_id[i]);
		                	 temp.setVisibility(View.INVISIBLE);
	                	 }
	                	 TableRow temp = (TableRow)findViewById(R.id.tableRow10);
	                	 temp.setVisibility(View.VISIBLE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11);
	                	 temp.setVisibility(View.VISIBLE);
	                	 temp = (TableRow)findViewById(R.id.tableRow12);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13);
	                	 temp.setVisibility(View.GONE);
	                }
	                if (x>width/3*2){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_mynote_top));
	                	 for(int i =0; i<details_table_view_id.length; i++){
		                	 TableRow temp = (TableRow)findViewById(details_table_view_id[i]);
		                	 temp.setVisibility(View.INVISIBLE);
	                	 }
	                	 TableRow temp = (TableRow)findViewById(R.id.tableRow10);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow12);
	                	 temp.setVisibility(View.VISIBLE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13);
	                	 temp.setVisibility(View.VISIBLE);
	                }
	            }
	            return true;
			}

			
        	
        });
        
        ImageButton email_btn = (ImageButton) findViewById(R.id.cellar_email_icon);
        email_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "My tasting note");
				i.putExtra(Intent.EXTRA_TEXT   , "I've tasted "+wineDetail.get(1)+" recently and here is my tasting note to share: "+wineDetail.get(14));
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(MyCellarsWineDetail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}
        	
        	
        });
        
        ImageButton twitter_btn = (ImageButton) findViewById(R.id.cellar_twitter_icon);
        twitter_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				final TwitterApp mTwitter = new TwitterApp(MyCellarsWineDetail.this.getParent(), CommonUtilities.twitter_consumer_key,CommonUtilities.twitter_secret_key);
				mTwitter.setListener(mTwLoginDialogListener);
				if (mTwitter.hasAccessToken()) {
					
					new Thread() {
			            @Override
			            public void run() {
			                int what = 0;
			 
			                try {
			                    mTwitter.updateStatus("I've tasted "+wineDetail.get(1)+" into my wish list. Let's have a wine gathering. "+"https://www.watsonswine.com");
			                    mHandler.sendMessage(mHandler.obtainMessage(0));
			                } catch (Exception e) {
			                	Log.e("Osmands", "Twitter updateStatus error = "+e);
			                    if (e.getMessage().toString().contains("duplicate")) {
			                    	mHandler.sendMessage(mHandler.obtainMessage(2));
			                    } else {
			                    	mHandler.sendMessage(mHandler.obtainMessage(1));
			                    }
			                }
			 
			                
			            }
			        }.start();
					
					/*try {
	                    mTwitter.updateStatus("I've tasted "+wineDetail.get(1)+" recently."+"https://www.watsonswine.com");
	                } catch (Exception e) {
	                    Log.i("Osmands", "Twitter updateStatus error = "+e);
	                }*/
					/*try{
					    Intent intent = new Intent(Intent.ACTION_SEND);
					    intent.putExtra(Intent.EXTRA_TEXT, "I've tasted "+wineDetail.get(1)+" recently."+"https://www.watsonswine.com");
					    intent.setType("text/plain");
					    final PackageManager pm = MyCellarsWineDetail.this.getParent().getPackageManager();
					    final List activityList = pm.queryIntentActivities(intent, 0);
					        int len =  activityList.size();
					    for (int i = 0; i < len; i++) {
					        final ResolveInfo app = (ResolveInfo) activityList.get(i);
					        if ("com.twitter.android.PostActivity".equals(app.activityInfo.name)) {
					            final ActivityInfo activity=app.activityInfo;
					            final ComponentName name=new ComponentName(activity.applicationInfo.packageName, activity.name);
					            intent=new Intent(Intent.ACTION_SEND);
					            intent.addCategory(Intent.CATEGORY_LAUNCHER);
					            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					            intent.setComponent(name);
					            intent.putExtra(Intent.EXTRA_TEXT, "I've tasted "+wineDetail.get(1)+" recently."+"https://www.watsonswine.com");
					            MyCellarsWineDetail.this.getParent().startActivity(intent);
					            break;
					        }
					    }
					}
					catch(final ActivityNotFoundException e) {
					    Log.i("Osmands", "no twitter native",e );
					}*/
		        } else {
		 
		            mTwitter.authorize();
		        }
			}
        	
        	
        });
        
     /*   ImageButton back_btn = (ImageButton) findViewById(R.id.cellar_details_back_button);
        back_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
				
			}
        	
        	
        });
       */ 
        ImageButton delete_btn = (ImageButton) findViewById(R.id.cellar_delete_button);
        delete_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				getAlertDialog("Confirm\nDo you want to delete?").show();
				
			}
        	
        	
        });
        
        ImageButton edit_btn = (ImageButton) findViewById(R.id.cellar_edit_button);
        edit_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				for(int i =0; i<wineDetail.size();i++) {
					Log.i("Osmands", "wineDetail.get("+i+")"+wineDetail.get(i));
				}
				Bundle b = new Bundle();
				b.putStringArrayList("wineDetail", wineDetail);
				
				Intent intent = new Intent(MyCellarsWineDetail.this, MyCellarsUpdateItemsActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				
			}
        	
        	
        });
        
        ImageButton facebook_btn = (ImageButton) findViewById(R.id.cellar_facebook_icon);
        facebook_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Bundle params = new Bundle();
				String desc = wineDetail.get(14);
				if (desc.length() > 180) {
					desc = wineDetail.get(14).substring(0, 180)+"...";
				}
				//params.putString("message", "I've added "+wineDetail.get(1)+" into my wish list. Let's have a wine gathering and try together.  Tasting note: "+desc);
				params.putString("description", "I've added "+wineDetail.get(1)+" into my wish list. Let's have a wine gathering and try together.  Tasting note: "+desc);
				params.putString("name", wineDetail.get(1));
				/*if (!wineDetail.get(4).equals("-") && wineDetail.get(4) != null) {
					params.putString("picture", "https://www.watsonswine.com/WebShop/asset/images/prd/"+wineDetail.get(4));
				}*/
				//params.putString("link", "https://www.watsonswine.com");
				params.putString("picture", "https://www.watsonswine.com");
				if (!wineDetail.get(21).equals("-1")) {
					params.putString("link", "https://www.watsonswine.com/WebShop/BrowseProductDetail.do?prdid="+wineDetail.get(21));
				} else {
					params.putString("link", "https://www.watsonswine.com");
				}
				CommonUtilities.facebook.dialog(MyCellarsWineDetail.this.getParent(), "feed", params, new UpdateStatusListener());
				
			}
        	
        	
        });
       
    }
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = "";
            switch(msg.what){
	            case 0: text = "Posted to Twitter"; break;
	            case 1: text = "Post to Twitter failed"; break;
	            case 2: text = "Posting Failed because of duplicate message"; break;
            }
 
            Toast.makeText(MyCellarsWineDetail.this.getParent(), text, Toast.LENGTH_SHORT).show();
        }
    };
    
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
	       // JWMessageDialog.show("error",NewsOffersDetailsActivity.this, "Facebook Error: " + error.getMessage(), NewsOffersDetailsActivity.this);
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
    
    private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {
        @Override
        public void onComplete(String value) {
            Log.i("Osmands", "Twitter onComplete = "+value);
        }
 
        @Override
        public void onError(String value) {
 
            //Toast.makeText(TestPost.this, "Twitter connection failed", Toast.LENGTH_LONG).show();
        	Log.i("Osmands", "Twitter onError = "+value);
        }
    };
    
    
    @Override
   	protected void onResume(){
       	super.onResume();
       	openDatabase();
        Cursor cursor = getCursor();
        ArrayList<MyCellarItemDetails> results = new ArrayList<MyCellarItemDetails>();
        
        while(cursor.moveToNext()){
        	results.add(GetSearchResults(cursor));

        }
        
        wineDetail = results.get(0).getAllToStringArray();
        windID = Integer.valueOf(wineDetail.get(0));
        ((TextView) findViewById(R.id.cellar_wine_name)).setText(wineDetail.get(1));
        ((TextView) findViewById(R.id.cellar_table_region)).setText(wineDetail.get(5));
        ((TextView) findViewById(R.id.cellar_table_vintage)).setText(wineDetail.get(6));
        ((TextView) findViewById(R.id.cellar_table_grape)).setText(wineDetail.get(7));
        ((TextView) findViewById(R.id.cellar_table_colour)).setText(wineDetail.get(8));
        ((TextView) findViewById(R.id.cellar_table_body)).setText(wineDetail.get(9));
        ((TextView) findViewById(R.id.cellar_table_sweetness)).setText(wineDetail.get(10));
        ((TextView) findViewById(R.id.cellar_table_size)).setText(wineDetail.get(11));
        if(!wineDetail.get(12).isEmpty() && !wineDetail.get(12).equals("-")) {
			java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
			String temp = myformat.format(Double.valueOf(wineDetail.get(12)));
			 ((TextView) findViewById(R.id.cellar_table_price)).setText("$"+temp);
		} else {
			 ((TextView) findViewById(R.id.cellar_table_price)).setText(wineDetail.get(12));
		}
        ((TextView) findViewById(R.id.cellar_table_quantity)).setText(wineDetail.get(13));
        ((TextView) findViewById(R.id.cellar_table_testing_date)).setText(wineDetail.get(15));
        ((TextView) findViewById(R.id.cellar_table_occasion)).setText(wineDetail.get(16));
        ((TextView) findViewById(R.id.cellar_table_note)).setText(wineDetail.get(14));
        
        TableRow.LayoutParams lp = new TableRow.LayoutParams((int)((2*width/3))-45,LinearLayout.LayoutParams.WRAP_CONTENT);
        ((TextView) findViewById(R.id.cellar_table_note)).setLayoutParams(lp);
        int favourite = Integer.valueOf(wineDetail.get(3));
        ArrayList<ImageView> itemView = new ArrayList<ImageView>();
        itemView.add((ImageView) findViewById(R.id.cellar_table_rating_0));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_1));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_2));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_3));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_4));
		itemView.add((ImageView) findViewById(R.id.cellar_table_rating_5));
		for (int i=0; i <favourite; i++) {
			itemView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_wine_glass_full));
		}
		for (int i=favourite; i <6; i++) {
			itemView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_wine_glass));
		}
		if (wineDetail.get(2).equals("N")) {
			((ImageButton) findViewById (R.id.cellar_wish_list_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
			((ImageButton) findViewById (R.id.cellar_in_stock_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick_form));
		}else {
			((ImageButton) findViewById (R.id.cellar_in_stock_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
			((ImageButton) findViewById (R.id.cellar_wish_list_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick_form));
		}
		
		if (wineDetail.get(4).equals("-") || wineDetail.get(4) == null) {
			//((ImageView) findViewById (R.id.cellar_details_wine_image)).setImageResource(R.drawable.bg_photo_container_camera);
			((ImageView) findViewById (R.id.cellar_details_wine_image)).setBackgroundDrawable(MyCellarsWineDetail.this.getResources().getDrawable(R.drawable.bg_photo_container_camera));
		} else {
			String cache_image_path = CommonUtilities.cashImagePath;
			//String cache_image_path = "/storage/sdcard0/watsons_wine/MyCellarsChash/";
			File imgFile = new File(cache_image_path+wineDetail.get(4));
			if (imgFile.exists()) {
				//holder.itemImageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_photo_container_small));
				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				((ImageView) findViewById (R.id.cellar_details_wine_image)).setBackgroundDrawable(MyCellarsWineDetail.this.getResources().getDrawable(R.drawable.bg_photo_container));
				((ImageView) findViewById (R.id.cellar_details_wine_image)).setImageBitmap(myBitmap);
				//holder.newsImage.setBackgroundDrawable(new BitmapDrawable(myBitmap));
				Log.i("osmand", "setImageBitmap");
			}
			
		}
        
        ((TextView) findViewById(R.id.cellar_added_date)).setText("Added: "+wineDetail.get(17).substring(0, wineDetail.get(17).length()-3));
       
    }
    
    private MyCellarItemDetails GetSearchResults(Cursor cursor) {
		Log.i("Osmands", "GetSearchResults");
    	MyCellarItemDetails item_details = new MyCellarItemDetails();
    	int i =0;
    	item_details.setId(cursor.getString(i++));
		item_details.setWineName(cursor.getString(i++));
		item_details.setWineRegion(cursor.getString(i++));
		item_details.setWineVintage(cursor.getString(i++));
		item_details.setWineGrape(cursor.getString(i++));
		item_details.setWineColour(cursor.getString(i++));
		item_details.setWineBody(cursor.getString(i++));
		item_details.setWineSweetness(cursor.getString(i++));
		item_details.setWineSize(cursor.getString(i++));
		item_details.setWinePrice(cursor.getString(i++));
		item_details.setWineQuantity(cursor.getString(i++));
		item_details.setWineNote(cursor.getString(i++));
		item_details.setWinefavourite(cursor.getString(i++));
		item_details.setWineTastingDate(cursor.getString(i++));
		item_details.setWineOccasion(cursor.getString(i++));
		item_details.setWineStatus(cursor.getString(i++));
		item_details.setWineImage(cursor.getString(i++));
		item_details.setWineCreateDate(cursor.getString(i++));
		item_details.setWineModifyDate(cursor.getString(i++));
		item_details.setWineUpToCms(cursor.getString(i++));
		item_details.setServerId(cursor.getString(i++));
		item_details.setUrlId(cursor.getString(i++));
		return item_details;
	}
    
    private void openDatabase(){
        dbhelper = new DBHelper(this); 
    }
    
    private Cursor getCursor(){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstants.MY_CELLAR_TABLE_NAME, null, "_id ="+windID, null, null, null, DbConstants.MY_CELLAR_MODIFY_DATE);
        startManagingCursor(cursor);

        return cursor;
    }
    
    private AlertDialog getAlertDialog(String message){

        Builder builder = new AlertDialog.Builder(MyCellarsWineDetail.this.getParent());

        builder.setMessage(message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
            	WatsonWineDB db = new WatsonWineDB();
            	boolean result = db.deleteMyCellerRecord(getParent(), windID);
            	if (result) {
            		finish();
            	}
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
            		//do nothing
            }

        });

        return builder.create();

    }
    
    @Override
    protected void onDestroy() {
    	Log.v("Osmands", "onDestroy()");
        super.onDestroy();
    }


}
