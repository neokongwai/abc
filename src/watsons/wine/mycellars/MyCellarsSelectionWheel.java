package watsons.wine.mycellars;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import watsons.wine.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class MyCellarsSelectionWheel extends Activity {
	
	int width;
	
	private boolean wheelScrolled = false;
	EditText text1;
	
	int[] details_table_view_id = new int[]{R.id.tableRow1, R.id.tableRow2, R.id.tableRow3, R.id.tableRow4, R.id.tableRow5, R.id.tableRow6, R.id.tableRow7, R.id.tableRow8, R.id.tableRow9};
	
	String wheelMenu1[] = new String[]{"Right Arm", "Left Arm", "R-Abdomen", "L-Abdomen", "Right Thigh", "Left Thigh"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_selection);
        initWheel1(R.id.wheel);
        text1 = (EditText) this.findViewById(R.id.cellars_input_text);
        ImageButton done_btn = (ImageButton) this.findViewById(R.id.cellar_done_button);
        done_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle b = new Bundle();
				b.putString("input", text1.getText().toString());
				b.putString("who", "name");
				intent.putExtras(b);
				setResult(RESULT_OK, intent);  
				finish();
				
			}
        	
        });
        //final WheelView year = (WheelView) findViewById(R.id.wheel);
		//year.setViewAdapter(new ArrayWheelAdapter());
        //year.setCyclic(true);
       
    }
    
    
    private void initWheel1(int id)

    {
        WheelView wheel = (WheelView) findViewById(id);
        wheel.setViewAdapter(new ArrayWheelAdapter(this, wheelMenu1));
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

	// Wheel changed listener
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
			text1.setText(wheelMenu1[getWheel(R.id.wheel).getCurrentItem()]);

		}
		
		 private WheelView getWheel(int id)
         {
             return (WheelView) findViewById(id);
         }

    
    @Override
    protected void onDestroy() {
    	Log.v("Osmands", "onDestroy()");
        super.onDestroy();
    }
}
