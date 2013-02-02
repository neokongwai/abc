package watsons.wine.mycellars;

import java.util.ArrayList;
import watsons.wine.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyCellarsWineDetail extends Activity {
	
	int width;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_wine_detail);
        Display display = getWindowManager().getDefaultDisplay();
  		width = display.getWidth();
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> wineDetail = bundle.getStringArrayList("wineDetail");
        
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
        
        ((TextView) findViewById(R.id.cellar_added_date)).setText("Added: "+wineDetail.get(17));
        
        ImageButton tab = (ImageButton) findViewById(R.id.cellar_details_tab);
        tab.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP){
	                float x = event.getX();
	                if (x < width/3){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_wine_top));
	                }
	                if (x<width/3*2 && x > width/3){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_profile_top));
	                }
	                if (x>width/3*2){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_mynote_top));
	                }
	            }
	            return true;
			}

			
        	
        });
       
    }
    
    
    @Override
    protected void onDestroy() {
    	Log.v("Osmands", "onDestroy()");
        super.onDestroy();
    }
}
