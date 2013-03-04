package watsons.wine.mycellars;

import java.util.ArrayList;

import com.android.sqlite.DBHelper;
import com.android.sqlite.DbConstants;
import watsons.wine.R;
import watsons.wine.TabGroupBase;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyCellarsListIemsActivity extends Activity {
	String mode = "normal";
	String searchText = null;
	String searchRegion = null;
	 String searchBody = null;
	 String searchVintage = null;
	 String searchGrape = null;
	 String searchColour = null;
	 String searchSize = null;
	 String searchPrice = null;
	 String searchSweetness = null;
	 String searchPriceFrom = null;
	 String searchPriceTo = null;
	DBHelper dbhelper;
	ArrayList<MyCellarItemDetails> results = null;
	ArrayList<MyCellarItemDetails> instock_results_list = null;
	ArrayList<MyCellarItemDetails> wish_results_list = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_list_items_main);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
        	mode = "search";
        	searchText = bundle.getString("searchText");
        	Log.i("Osmands", "searchText.isEmpty() = "+searchText.isEmpty());
        	if (searchText.isEmpty()) {
        		  searchRegion = bundle.getString("searchRegion");
    		      searchBody = bundle.getString("searchBody");
        		  searchVintage = bundle.getString("searchVintage");
        		  searchGrape = bundle.getString("searchGrape");
        		  searchColour = bundle.getString("searchColour");
        		  searchSize = bundle.getString("searchSize");
        		  searchPriceFrom = bundle.getString("searchPriceFrom");
        		  searchPriceTo = bundle.getString("searchPriceTo");
        		  searchSweetness = bundle.getString("searchSweetness");
        	}
        }
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
		item_details.setWinePrice("$"+cursor.getString(i++));
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
		return item_details;
	}
    
    private void openDatabase(){
        dbhelper = new DBHelper(this); 
    }
    
    private Cursor getCursor(){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstants.MY_CELLAR_TABLE_NAME, null, null, null, null, null, DbConstants.MY_CELLAR_MODIFY_DATE+" DESC");
        startManagingCursor(cursor);

        return cursor;
    }
    
    private Cursor getCursor(String searchText){
      SQLiteDatabase db = dbhelper.getReadableDatabase();
      Cursor cursor = db.query(DbConstants.MY_CELLAR_TABLE_NAME, null, "LOWER("+DbConstants.MY_CELLAR_WINE_NAME+") LIKE LOWER('%"+ searchText+"%')", null, null, null, null);
      startManagingCursor(cursor);
      return cursor;
    }
    
    private Cursor getCursor(int searchPriceFrom, int searchPriceTo){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(DbConstants.MY_CELLAR_TABLE_NAME, null, "("+DbConstants.MY_CELLAR_PRICE+" <= "+searchPriceTo+" OR "+searchPriceTo+" = 5001) AND "+DbConstants.MY_CELLAR_PRICE+" >= "+searchPriceFrom , null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
      }
    
    @Override
	protected void onResume(){
    	super.onResume();
    	openDatabase();
    	Cursor cursor = null;
    	if (mode.equals("normal")) {
    		cursor = getCursor();
    	} else {
    		if (!searchText.isEmpty()) {
    			cursor = getCursor(searchText);
    		} else {
    			if (searchPriceFrom.equals("Price")) {
    				cursor = getCursor();
    			} else {
    				cursor = getCursor(Integer.valueOf(searchPriceFrom),Integer.valueOf(searchPriceTo));
    			}
    		}
    		
    		((LinearLayout)findViewById(R.id.cellar_all_instock_wish_bar)).setVisibility(View.GONE);
    		((ImageButton)findViewById(R.id.cellar_add_button)).setVisibility(View.GONE);
    		((ImageButton)findViewById(R.id.cellar_search_button)).setVisibility(View.GONE);
    	}
        results = new ArrayList<MyCellarItemDetails>();
        
        while(cursor.moveToNext()){
        	results.add(GetSearchResults(cursor));
        }
        
        if (mode.equals("search") && searchText.isEmpty()) {
        	ArrayList<MyCellarItemDetails> temp = new ArrayList<MyCellarItemDetails>();
			for (int i =0; i<results.size(); i++){
				boolean include = false;
				Log.i("Osmands", "searchRegion = "+searchRegion);
				if (searchRegion.equals("Region")) {
					Log.i("Osmands", "searchRegion.equals= region");
					include = true;
				} else if (results.get(i).getWineRegion().equals(searchRegion)) {
					Log.i("Osmands", "results.get(i).getWineRegion() = "+results.get(i).getWineRegion());
					include = true;
				}
				if (include) {
					temp.add(results.get(i));
				}
			}
			results.clear();
			results.addAll(temp);
			temp.clear();
			for (int i =0; i<results.size(); i++){
				boolean include = false;
				Log.i("Osmands", "searchVintage = "+searchVintage);
				if (!searchVintage.equals("Vintage")) {
					if (searchVintage.equals("<1990")) {
						Log.i("Osmands", "searchVintage<1990");
						int resultVintage = 0;
						try{
							resultVintage = Integer.valueOf(results.get(i).getWineVintage().toString());
						} catch(NumberFormatException e) {
							Log.i("Osmands", "NumberFormatException = "+e);
							if (results.get(i).getWineVintage().toString().equals("<1990")) {
								include = true;
							}
							resultVintage = 2999;
						}
						if (resultVintage < 1990){
							include = true;
						} 
						
					} else  {
						Log.i("Osmands", "searchVintage>1990");
						int resultVintage = 0;
						try{
							resultVintage = Integer.valueOf(results.get(i).getWineVintage().toString());
						} catch(NumberFormatException e) {
							Log.i("Osmands", "NumberFormatException = "+e);
							resultVintage = 2999;
						}
						if (resultVintage == Integer.valueOf(searchVintage)){
							include = true;
						} 
					}
				} else {
					include = true;
				}
				if (include) {
					temp.add(results.get(i));
				}
			}
			results.clear();
			results.addAll(temp);
			temp.clear();
			for (int i =0; i<results.size(); i++){
				boolean include = false;
				Log.i("Osmands", "searchGrape = "+searchGrape);
				if (!searchGrape.equals("Grape")) {
					if (results.get(i).getWineGrape().contains(searchGrape)){
						include = true;
					}
				} else {
					include = true;
				}
				if (include) {
					temp.add(results.get(i));
				}
			}
			results.clear();
			results.addAll(temp);
			temp.clear();
			for (int i =0; i<results.size(); i++){
				boolean include = false;
				Log.i("Osmands", "searchBody = "+searchBody);
				if (!searchBody.equals("Body")) {
					if (results.get(i).getWineBody().contains(searchBody)){
						include = true;
					}
				} else {
					include = true;
				}
				if (include) {
					temp.add(results.get(i));
				}
			}
			results.clear();
			results.addAll(temp);
			temp.clear();
			for (int i =0; i<results.size(); i++){
				boolean include = false;
				Log.i("Osmands", "searchColour = "+searchColour);
				if (!searchColour.equals("Colour")) {
					if (results.get(i).getWineColour().contains(searchColour)){
						include = true;
					}
				} else {
					include = true;
				}
				if (include) {
					temp.add(results.get(i));
				}
			}
			results.clear();
			results.addAll(temp);
			temp.clear();
			for (int i =0; i<results.size(); i++){
				boolean include = false;
				Log.i("Osmands", "searchSize = "+searchSize);
				if (!searchSize.equals("Size")) {
					if (results.get(i).getWineSize().contains(searchSize)){
						include = true;
					}
				} else {
					include = true;
				}
				if (include) {
					temp.add(results.get(i));
				}
			}
			results.clear();
			results.addAll(temp);
			temp.clear();
			for (int i =0; i<results.size(); i++){
				boolean include = false;
				Log.i("Osmands", "searchSweetness = "+searchSweetness);
				if (!searchSweetness.equals("Sweetness")) {
					if (results.get(i).getWineSweetness().contains(searchSweetness)){
						include = true;
					}
				} else {
					include = true;
				}
				if (include) {
					temp.add(results.get(i));
				}
			}
			results.clear();
			results.addAll(temp);
			temp.clear();
        }
        
        
        ListView list = (ListView) findViewById(R.id.cellars_all_list_View);  
  
        list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, results, true));
        list.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Log.i("Osmands", "position = "+position);
	    		Log.i("Osmands", "results.get(position).getId() = "+results.get(position).getId());
	    		Log.i("Osmands", "results.get(position).getWineStatus() = "+results.get(position).getWineStatus());
	    		Log.i("Osmands", "results.get(position).getServerId() = "+results.get(position).getServerId());
	    		ArrayList<String> temp = results.get(position).getAllToStringArray();
	    		for (int i =0; i<temp.size(); i++) {
	    			Log.i("Osmands", "temp("+i+") = "+temp.get(i));
	    		}
	    		Intent intent = null;
				Bundle b = new Bundle();
				b.putStringArrayList("wineDetail", results.get(position).getAllToStringArray());
				
				intent = new Intent(getParent(), MyCellarsWineDetail.class);
				intent.putExtras(b);
				TabGroupBase parentActivity = (TabGroupBase)getParent();
	        	parentActivity.startChildActivity("MyCellarsList", intent);
	    	}
    	});
        
     /*   ImageButton back_bn = (ImageButton) findViewById (R.id.cellar_back_button);
        back_bn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
        });
       */ 
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
		  
		        list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, instock_results_list, false)); 
		        list.setOnItemClickListener(new OnItemClickListener() {
			    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Log.i("Osmands", "position = "+position);
			    		Log.i("Osmands", "instock_results_list.get(position).getId() = "+instock_results_list.get(position).getId());
			    		Log.i("Osmands", "instock_results_list.get(position).getWineStatus() = "+instock_results_list.get(position).getWineStatus());
			    		Intent intent = null;
						Bundle b = new Bundle();
						b.putStringArrayList("wineDetail", instock_results_list.get(position).getAllToStringArray());
						
						intent = new Intent(getParent(), MyCellarsWineDetail.class);
						intent.putExtras(b);
						TabGroupBase parentActivity = (TabGroupBase)getParent();
			        	parentActivity.startChildActivity("MyCellarsList", intent);
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
				  
			    list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, results, true)); 
			    list.setOnItemClickListener(new OnItemClickListener() {
			    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Log.i("Osmands", "position = "+position);
			    		Log.i("Osmands", "results.get(position).getId() = "+results.get(position).getId());
			    		Log.i("Osmands", "results.get(position).getWineStatus() = "+results.get(position).getWineStatus());
			    		Intent intent = null;
						Bundle b = new Bundle();
						b.putStringArrayList("wineDetail", results.get(position).getAllToStringArray());
						
						intent = new Intent(getParent(), MyCellarsWineDetail.class);
						intent.putExtras(b);
						TabGroupBase parentActivity = (TabGroupBase)getParent();
			        	parentActivity.startChildActivity("MyCellarsList", intent);
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
				  
			    list.setAdapter(new CellarsListItemsAdapter(MyCellarsListIemsActivity.this, wish_results_list, false)); 
			    list.setOnItemClickListener(new OnItemClickListener() {
			    	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Log.i("Osmands", "position = "+position);
			    		Log.i("Osmands", "wish_results_list.get(position).getId() = "+wish_results_list.get(position).getId());
			    		Log.i("Osmands", "wish_results_list.get(position).getWineStatus() = "+wish_results_list.get(position).getWineStatus());
			    		Intent intent = null;
						Bundle b = new Bundle();
						b.putStringArrayList("wineDetail", wish_results_list.get(position).getAllToStringArray());
						
						intent = new Intent(getParent(), MyCellarsWineDetail.class);
						intent.putExtras(b);
						TabGroupBase parentActivity = (TabGroupBase)getParent();
			        	parentActivity.startChildActivity("MyCellarsListAdd", intent);
			    	}
		    	});
			}
        	
        });
        
        ((ImageButton)findViewById(R.id.cellar_add_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = null;
				intent = new Intent(MyCellarsListIemsActivity.this, MyCellarsUpdateItemsActivity.class);
				startActivity(intent);
				
			}
        	
        });
        
        ((ImageButton)findViewById(R.id.cellar_search_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = null;
				intent = new Intent(getParent(), MyCellarsSearchMain.class);
				TabGroupBase parentActivity = (TabGroupBase)getParent();
	        	parentActivity.startChildActivity("MyCellarsListSearch", intent);
				
			}
        	
        });
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
