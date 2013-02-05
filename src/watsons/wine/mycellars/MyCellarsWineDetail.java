package watsons.wine.mycellars;

import java.io.File;
import java.util.ArrayList;

import com.android.sqlite.DBHelper;
import com.android.sqlite.DbConstants;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import watsons.wine.R;
import watsons.wine.TabGroupBase;
import watsons.wine.utilities.WatsonWineDB;

import android.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
        ((TextView) findViewById(R.id.cellar_wine_name)).setText(wineDetail.get(1));
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
        
        ((TextView) findViewById(R.id.cellar_added_date)).setText("Added: "+wineDetail.get(17));
       
        
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
        
        ImageButton back_btn = (ImageButton) findViewById(R.id.cellar_details_back_button);
        back_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
				
			}
        	
        	
        });
        
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
				Bundle b = new Bundle();
				b.putStringArrayList("wineDetail", wineDetail);
				
				Intent intent = new Intent(MyCellarsWineDetail.this, MyCellarsUpdateItemsActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				
			}
        	
        	
        });
       
    }
    
    
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
			((ImageView) findViewById (R.id.cellar_update_wine_image)).setImageResource(R.drawable.bg_photo_container_camera);
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
        
        ((TextView) findViewById(R.id.cellar_added_date)).setText("Added: "+wineDetail.get(17));
       
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
