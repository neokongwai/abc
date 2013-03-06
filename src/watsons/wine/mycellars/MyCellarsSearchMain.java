package watsons.wine.mycellars;

import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import com.android.sqlite.DBHelper;
import watsons.wine.R;
import watsons.wine.TabGroupBase;
import watsons.wine.utilities.CommonUtilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCellarsSearchMain extends Activity implements View.OnClickListener  {
	
	DBHelper dbhelper;
	ArrayList<MyCellarItemDetails> results = null;
	ArrayList<MyCellarItemDetails> instock_results_list = null;
	ArrayList<MyCellarItemDetails> wish_results_list = null;
	private boolean wheelScrolled = false;
	int whellMenuNumber;
	private boolean wheelShowing =false;
	
	private String searchRegion = "Region";
	private String searchBody = "Body";
	private String searchVintage = "Vintage";
	private String searchGrape = "Grape";
	private String searchColour = "Colour";
	private String searchSize = "Size";
	private String searchPriceFrom = "Price";
	private String searchPriceTo = "Price";
	private String searchSweetness = "Sweetness";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_search_main);
        
        initBtn();
        
        ((ImageButton)findViewById(R.id.cellar_search_name_btn)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String searchText = ((EditText)findViewById(R.id.cellar_search_text)).getText().toString();
				
				if (!searchText.trim().isEmpty()) {
			        Intent intent = null;
					intent = new Intent(getParent(), MyCellarsListIemsActivity.class);
					Bundle b = new Bundle();
					b.putString("searchText", searchText);
					intent.putExtras(b);
					TabGroupBase parentActivity = (TabGroupBase)getParent();
		        	parentActivity.startChildActivity("MyCellarsListSearchResult", intent);
				}
				
			}
        	
        });
        
        ((ImageButton)findViewById(R.id.cellar_search_btn)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
			        Intent intent = null;
					intent = new Intent(getParent(), MyCellarsListIemsActivity.class);
					Bundle b = new Bundle();
					b.putString("searchText", "");
					b.putString("searchRegion", searchRegion);
					b.putString("searchBody", searchBody);
					b.putString("searchGrape", searchGrape);
					b.putString("searchVintage", searchVintage);
					b.putString("searchColour", searchColour);
					b.putString("searchSize", searchSize);
					b.putString("searchPriceFrom", searchPriceFrom);
					b.putString("searchPriceTo", searchPriceTo);
					b.putString("searchSweetness", searchSweetness);
					intent.putExtras(b);
					TabGroupBase parentActivity = (TabGroupBase)getParent();
		        	parentActivity.startChildActivity("MyCellarsListSearchResult", intent);
				
			}
        	
        });
        
      /*  ((ImageButton)findViewById(R.id.cellar_back_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
        	
        });
        */
        
        ((ImageButton)findViewById(R.id.cellar_reset_btn)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				initBtn();
			}
        	
        });
        
        ((RelativeLayout)findViewById(R.id.cellar_search_region)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.cellar_search_body)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.cellar_search_colour)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.cellar_search_grape)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.cellar_search_price)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.cellar_search_size)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.cellar_search_sweetness)).setOnClickListener(this);
        ((RelativeLayout)findViewById(R.id.cellar_search_vintage)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.cellar_search_done_btn)).setOnClickListener(this);
        
       
    }

	@Override
	public void onClick(View v) {
		whellMenuNumber = v.getId();
		switch(v.getId()){
			case R.id.cellar_search_region:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuRegion);
				break;
			case R.id.cellar_search_body:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuBody);
				break;
			case R.id.cellar_search_colour:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuColour);
				break;
			case R.id.cellar_search_grape:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuGrape);
				break;
			case R.id.cellar_search_price:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuPrice);
				break;
			case R.id.cellar_search_size:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuSize);
				break;
			case R.id.cellar_search_sweetness:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuSweetness);
				break;
			case R.id.cellar_search_vintage:
				initWheel(R.id.cellar_search_wheel, CommonUtilities.wheelMenuVintage);
				break;
			case R.id.cellar_search_done_btn:
				((LinearLayout) findViewById(R.id.cellar_search_wheel_layout)).setVisibility(View.GONE);
				break;
		}
		
	}
	
	private void initWheel(int id, String[] wheel_data )

    {
		wheelShowing = true;
    	((LinearLayout) findViewById(R.id.cellar_search_wheel_layout)).setVisibility(View.VISIBLE);
        WheelView wheel = (WheelView) findViewById(id);
        wheel.setViewAdapter(new ArrayWheelAdapter(this, wheel_data));
        wheel.setVisibleItems(2);
        wheel.setCurrentItem(0);
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);

    }
	
	 OnWheelScrollListener scrolledListener = new OnWheelScrollListener()
		{

			@Override
			public void onScrollingStarted(WheelView wheel) {
				wheelScrolled = true;
				
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				wheelScrolled = false;
				updateStatus();
				
			}
		};
	
	private final OnWheelChangedListener changedListener = new OnWheelChangedListener()
	{
		public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				if (!wheelScrolled)
					{
						updateStatus();
					}
			}
	};
	
	private void updateStatus()
	{
		if (whellMenuNumber == R.id.cellar_search_region) {
			Log.i("Osmands", "getWheel(R.id.cellar_search_wheel).getCurrentItem() = "+getWheel(R.id.cellar_search_wheel).getCurrentItem());
			/*if (getWheel(R.id.cellar_search_wheel).getCurrentItem() == 1 || getWheel(R.id.cellar_search_wheel).getCurrentItem() == 10) {
				searchRegion = "Bordeaux";
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() >= 2 && getWheel(R.id.cellar_search_wheel).getCurrentItem() <= 9) {
					searchRegion = "Bordeaux, "+CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()];	
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() >= 11 && getWheel(R.id.cellar_search_wheel).getCurrentItem() <= 20) {
				searchRegion = CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() == 20 || getWheel(R.id.cellar_search_wheel).getCurrentItem() == 31) {
				searchRegion = "Australia";
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() >= 21 || getWheel(R.id.cellar_search_wheel).getCurrentItem() <= 30) {
				searchRegion = "Australia, "+CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() >= 32 || getWheel(R.id.cellar_search_wheel).getCurrentItem() <= 36) {
				searchRegion = CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() == 37 || getWheel(R.id.cellar_search_wheel).getCurrentItem() == 41) {
				searchRegion = "Champagne";
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() >= 38 || getWheel(R.id.cellar_search_wheel).getCurrentItem() <= 40) {
				searchRegion = "Champagne, "+CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() >= 42 || getWheel(R.id.cellar_search_wheel).getCurrentItem() <= 50) {
				searchRegion = CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			} else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() == 51 || getWheel(R.id.cellar_search_wheel).getCurrentItem() == 55) {
				searchRegion = "Accessories";
			}  else if (getWheel(R.id.cellar_search_wheel).getCurrentItem() >= 52 || getWheel(R.id.cellar_search_wheel).getCurrentItem() <= 54) {
				searchRegion = "Accessories, "+CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			}*/
			int currentItem = getWheel(R.id.cellar_search_wheel).getCurrentItem();
			Log.i("Osmands", "currentItem = "+currentItem);
			String Region = "Region";
			if (currentItem == 0 ) {
				searchRegion = "Bordeaux";
			} else if (currentItem >= 1 && currentItem <= 8) {
				searchRegion = "Bordeaux, "+CommonUtilities.wheelMenuRegion[currentItem];	
			} else if (currentItem >= 10 && currentItem <= 19) {
				searchRegion = CommonUtilities.wheelMenuRegion[currentItem];
			} else if (currentItem == 20 ) {
				searchRegion = "Australia";
			} else if (currentItem > 20 && currentItem <= 29) {
				searchRegion = "Australia, "+CommonUtilities.wheelMenuRegion[currentItem];
			} else if (currentItem >= 31 && currentItem <= 35) {
				searchRegion = CommonUtilities.wheelMenuRegion[currentItem];
			} else if (currentItem == 36 ) {
				searchRegion = "Champagne";
			} else if (currentItem >= 37 && currentItem <= 39) {
				searchRegion = "Champagne, "+CommonUtilities.wheelMenuRegion[currentItem];
			} else if (currentItem >= 41 && currentItem <= 49) {
				searchRegion = CommonUtilities.wheelMenuRegion[currentItem];
			} else if (currentItem == 50) {
				searchRegion = "Accessories";
			}  else if (currentItem >= 51 && currentItem <= 53) {
				searchRegion = "Accessories, "+CommonUtilities.wheelMenuRegion[currentItem];
			}
			((TextView) findViewById(R.id.cellar_search_region_text)).setText(CommonUtilities.wheelMenuRegion[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == R.id.cellar_search_vintage) {
				searchVintage = CommonUtilities.wheelMenuVintage[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			
			((TextView) findViewById(R.id.cellar_search_vintage_text)).setText(CommonUtilities.wheelMenuVintage[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == R.id.cellar_search_grape) {
			searchGrape = CommonUtilities.wheelMenuGrape[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			((TextView) findViewById(R.id.cellar_search_grape_text)).setText(CommonUtilities.wheelMenuGrape[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == R.id.cellar_search_body) {
			searchBody = CommonUtilities.wheelMenuBody[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			((TextView) findViewById(R.id.cellar_search_body_text)).setText(CommonUtilities.wheelMenuBody[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == R.id.cellar_search_colour) {
			searchColour = CommonUtilities.wheelMenuColour[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			((TextView) findViewById(R.id.cellar_search_colour_text)).setText(CommonUtilities.wheelMenuColour[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == R.id.cellar_search_size) {
			searchSize = CommonUtilities.wheelMenuSize[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			((TextView) findViewById(R.id.cellar_search_size_text)).setText(CommonUtilities.wheelMenuSize[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == R.id.cellar_search_sweetness) {
			searchSweetness = CommonUtilities.wheelMenuSweetness[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			((TextView) findViewById(R.id.cellar_search_sweetness_text)).setText(CommonUtilities.wheelMenuSweetness[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == R.id.cellar_search_price) {
			//searchPrice = CommonUtilities.wheelMenuPrice[getWheel(R.id.cellar_search_wheel).getCurrentItem()];
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==0) {
				searchPriceFrom = "Price";
				searchPriceTo = "Price";
			}
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==1) {
				searchPriceFrom = "1";
				searchPriceTo = "100";
			}
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==2) {
				searchPriceFrom = "101";
				searchPriceTo = "200";
			}
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==3) {
				searchPriceFrom = "201";
				searchPriceTo = "500";
			}
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==4) {
				searchPriceFrom = "501";
				searchPriceTo = "1000";
			}
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==5) {
				searchPriceFrom = "1001";
				searchPriceTo = "2000";
			}
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==6) {
				searchPriceFrom = "2001";
				searchPriceTo = "5000";
			}
			if (getWheel(R.id.cellar_search_wheel).getCurrentItem() ==7) {
				searchPriceFrom = "5000";
				searchPriceTo = "5001";
			}
			((TextView) findViewById(R.id.cellar_search_price_text)).setText(CommonUtilities.wheelMenuPrice[getWheel(R.id.cellar_search_wheel).getCurrentItem()]);
		}

	}
	
	private void initBtn(){
		searchRegion = "Region";
		searchBody = "Body";
		searchVintage = "Vintage";
		searchGrape = "Grape";
		searchColour = "Colour";
		searchSize = "Size";
		searchPriceFrom = "Price";
		searchPriceTo = "Price";
		searchSweetness = "Sweetness";
		((EditText)findViewById(R.id.cellar_search_text)).setText("");
	
		((TextView) findViewById(R.id.cellar_search_region_text)).setText(searchRegion);

		((TextView) findViewById(R.id.cellar_search_vintage_text)).setText(searchVintage);

		((TextView) findViewById(R.id.cellar_search_grape_text)).setText(searchGrape);

		((TextView) findViewById(R.id.cellar_search_body_text)).setText(searchBody);
		
		((TextView) findViewById(R.id.cellar_search_colour_text)).setText(searchColour);

		((TextView) findViewById(R.id.cellar_search_size_text)).setText(searchSize);

		((TextView) findViewById(R.id.cellar_search_price_text)).setText(searchPriceFrom);

		((TextView) findViewById(R.id.cellar_search_sweetness_text)).setText(searchSweetness);
	}
	
	 private WheelView getWheel(int id)
     {
         return (WheelView) findViewById(id);
     }
	 
	 /*public void goneWheel(){
		 ((WheelView)findViewById(R.id.cellar_search_wheel)).setVisibility(View.GONE);
	 }
	 
	 @Override
	 public boolean dispatchKeyEvent(KeyEvent event) {
		 Log.i("Osmands", "dispatchKeyEvent");
		if(event.getKeyCode() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK && wheelShowing){  
			Log.i("Osmands", "onKeyDown");
			wheelShowing = false;
	    	((WheelView) findViewById(R.id.cellar_search_wheel)).setVisibility(View.GONE);
			return true;
	    }  
    	return super.dispatchKeyEvent(event);
	 }
		public boolean onKeyUp(int i, KeyEvent event) {
			// TODO Auto-generated method stub
			if(i == KeyEvent.KEYCODE_BACK && wheelShowing) {
				Log.i("Osmands", "onKeyDown = "+onKeyDown);
				wheelShowing = false;
		    	((WheelView) findViewById(R.id.cellar_search_wheel)).setVisibility(View.GONE);
				return true;
			}
			return false;
		}*/
    
    
    
//    private void openDatabase(){
//        dbhelper = new DBHelper(this); 
//    }
//    
//    private Cursor getCursor(String searchText){
//        SQLiteDatabase db = dbhelper.getReadableDatabase();
//        Cursor cursor = db.query(DbConstants.MY_CELLAR_TABLE_NAME, null, "LOWER("+DbConstants.MY_CELLAR_WINE_NAME+") = LOWER("+ searchText+")", null, null, null, null);
//        startManagingCursor(cursor);
//        return cursor;
//    }
//   
//    private MyCellarItemDetails GetSearchResults(Cursor cursor) {
//		Log.i("Osmands", "GetSearchResults");
//    	MyCellarItemDetails item_details = new MyCellarItemDetails();
//    	int i =0;
//    	item_details.setId(cursor.getString(i++));
//		item_details.setWineName(cursor.getString(i++));
//		item_details.setWineRegion(cursor.getString(i++));
//		item_details.setWineVintage(cursor.getString(i++));
//		item_details.setWineGrape(cursor.getString(i++));
//		item_details.setWineColour(cursor.getString(i++));
//		item_details.setWineBody(cursor.getString(i++));
//		item_details.setWineSweetness(cursor.getString(i++));
//		item_details.setWineSize(cursor.getString(i++));
//		item_details.setWinePrice(cursor.getString(i++));
//		item_details.setWineQuantity(cursor.getString(i++));
//		item_details.setWineNote(cursor.getString(i++));
//		item_details.setWinefavourite(cursor.getString(i++));
//		item_details.setWineTastingDate(cursor.getString(i++));
//		item_details.setWineOccasion(cursor.getString(i++));
//		item_details.setWineStatus(cursor.getString(i++));
//		item_details.setWineImage(cursor.getString(i++));
//		item_details.setWineCreateDate(cursor.getString(i++));
//		item_details.setWineModifyDate(cursor.getString(i++));
//		item_details.setWineUpToCms(cursor.getString(i++));
//		return item_details;
//	}
    
}
