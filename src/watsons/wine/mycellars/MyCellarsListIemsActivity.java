package watsons.wine.mycellars;

import java.util.ArrayList;

import com.android.sqlite.DBHelper;
import com.android.sqlite.DbConstants;
import watsons.wine.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyCellarsListIemsActivity extends Activity {
	
	DBHelper dbhelper;
	ArrayList<MyCellarItemDetails> results = null;
	ArrayList<MyCellarItemDetails> instock_results_list = null;
	ArrayList<MyCellarItemDetails> wish_results_list = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_list_items_main);
        
        openDatabase();
        Cursor cursor = getCursor();
        results = new ArrayList<MyCellarItemDetails>();
        
        while(cursor.moveToNext()){
        	results.add(GetSearchResults(cursor));

        }
        
        ListView list = (ListView) findViewById(R.id.cellars_all_list_View);  
  
        list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, results));
        list.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Log.i("Osmands", "position = "+position);
	    		Log.i("Osmands", "results.get(position).getId() = "+results.get(position).getId());
	    		Log.i("Osmands", "results.get(position).getWineStatus() = "+results.get(position).getWineStatus());
	    		Intent intent = null;
				Bundle b = new Bundle();
				b.putStringArrayList("wineDetail", results.get(position).getAllToStringArray());
				intent = new Intent(getApplicationContext(), MyCellarsWineDetail.class);
				intent.putExtras(b);
	    		startActivity(intent);
	    		finish();
	    	}
    	});
        
        ImageButton instock_bn = (ImageButton) findViewById (R.id.cellar_instock_button);
        instock_bn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				((ImageButton) findViewById (R.id.cellar_instock_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_on_instock));
				((ImageButton) findViewById (R.id.cellar_all_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_off_all));
				((ImageButton) findViewById (R.id.cellar_wish_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_off_winelist));
				((ListView) findViewById (R.id.cellars_wish_list_View)).setVisibility(View.GONE);
				((ListView) findViewById (R.id.cellars_all_list_View)).setVisibility(View.GONE);
				((ListView) findViewById (R.id.cellars_instock_list_View)).setVisibility(View.VISIBLE);
				instock_results_list = new ArrayList<MyCellarItemDetails>();
		        for (int i=0; i<results.size(); i++) {
		            if (results.get(i).getWineStatus().equals("Y")) {
		            	instock_results_list.add(results.get(i));
		            }
		        }
		        ListView list = (ListView) findViewById(R.id.cellars_instock_list_View);  
		  
		        list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, instock_results_list)); 
		        list.setOnItemClickListener(new OnItemClickListener() {
			    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Log.i("Osmands", "position = "+position);
			    		Log.i("Osmands", "instock_results_list.get(position).getId() = "+instock_results_list.get(position).getId());
			    		Log.i("Osmands", "instock_results_list.get(position).getWineStatus() = "+instock_results_list.get(position).getWineStatus());
			    	}
		    	});
			}
        	
        });
        ImageButton all_bn = (ImageButton) findViewById (R.id.cellar_all_button);
        all_bn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				((ImageButton) findViewById (R.id.cellar_instock_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_off_instock));
				((ImageButton) findViewById (R.id.cellar_all_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_on_all));
				((ImageButton) findViewById (R.id.cellar_wish_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_off_winelist));
				((ListView) findViewById (R.id.cellars_all_list_View)).setVisibility(View.VISIBLE);
				((ListView) findViewById (R.id.cellars_instock_list_View)).setVisibility(View.GONE);
				((ListView) findViewById (R.id.cellars_wish_list_View)).setVisibility(View.GONE);
				ListView list = (ListView) findViewById(R.id.cellars_all_list_View);  
				  
			    list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, results)); 
			    list.setOnItemClickListener(new OnItemClickListener() {
			    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Log.i("Osmands", "position = "+position);
			    		Log.i("Osmands", "results.get(position).getId() = "+results.get(position).getId());
			    		Log.i("Osmands", "results.get(position).getWineStatus() = "+results.get(position).getWineStatus());
			    	}
		    	});
			}
        	
        });
        
        ImageButton wish_bn = (ImageButton) findViewById (R.id.cellar_wish_button);
        wish_bn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				((ImageButton) findViewById (R.id.cellar_instock_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_off_instock));
				((ImageButton) findViewById (R.id.cellar_all_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_off_all));
				((ImageButton) findViewById (R.id.cellar_wish_button)).setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_cellar_on_winelist));
				((ListView) findViewById (R.id.cellars_all_list_View)).setVisibility(View.GONE);
				((ListView) findViewById (R.id.cellars_instock_list_View)).setVisibility(View.GONE);
				((ListView) findViewById (R.id.cellars_wish_list_View)).setVisibility(View.VISIBLE);
				wish_results_list = new ArrayList<MyCellarItemDetails>();
		        for (int i=0; i<results.size(); i++) {
		            if (results.get(i).getWineStatus().equals("N")) {
		            	wish_results_list.add(results.get(i));
		            }
		        }
				ListView list = (ListView) findViewById(R.id.cellars_wish_list_View);  
				  
			    list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, wish_results_list)); 
			    list.setOnItemClickListener(new OnItemClickListener() {
			    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Log.i("Osmands", "position = "+position);
			    		Log.i("Osmands", "wish_results_list.get(position).getId() = "+wish_results_list.get(position).getId());
			    		Log.i("Osmands", "wish_results_list.get(position).getWineStatus() = "+wish_results_list.get(position).getWineStatus());
			    	}
		    	});
			}
        	
        });
       
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
        Cursor cursor = db.query(DbConstants.MY_CELLAR_TABLE_NAME, null, null, null, null, null, DbConstants.MY_CELLAR_MODIFY_DATE);
        startManagingCursor(cursor);

        return cursor;
    }
    
    @Override
    protected void onDestroy() {
    	Log.v("Osmands", "onDestroy()");
        super.onDestroy();
    }
    
    /*@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		switch (arg1.getId()) {
	    	case R.id.cellars_all_list_View:
	    		Log.i("Osmands", "position = "+position);
	    		Log.i("Osmands", "results.get(position).getId() = "+results.get(position).getId());
	    		Log.i("Osmands", "results.get(position).getWineStatus() = "+results.get(position).getWineStatus());
	    		break;
	    	case R.id.cellars_instock_list_View:
	    		Log.i("Osmands", "position = "+position);
	    		Log.i("Osmands", "instock_results_list.get(position).getId() = "+instock_results_list.get(position).getId());
	    		Log.i("Osmands", "instock_results_list.get(position).getWineStatus() = "+instock_results_list.get(position).getWineStatus());
	    		break;
	    	case R.id.cellars_wish_list_View:
	    		Log.i("Osmands", "position = "+position);
	    		Log.i("Osmands", "wish_results_list.get(position).getId() = "+wish_results_list.get(position).getId());
	    		Log.i("Osmands", "wish_results_list.get(position).getWineStatus() = "+wish_results_list.get(position).getWineStatus());
	    		break;
	    	default:
	    		Log.i("Osmands", "default");
	    	
		}
		
	}*/
}
