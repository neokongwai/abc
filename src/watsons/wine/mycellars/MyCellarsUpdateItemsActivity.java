package watsons.wine.mycellars;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import watsons.wine.R;
import watsons.wine.TabGroupBase;
import watsons.wine.utilities.WatsonWineDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.AlertDialog.Builder;

public class MyCellarsUpdateItemsActivity extends Activity implements View.OnClickListener{
	
	String mode = "add";
	int width;
	
	int row;
	int output_row;
	int whellMenuNumber;
	private boolean wheelScrolled = false;
	String wheelMenuRegion[] = new String[]{"Region1", "Region2", "Region3", "Region4", "Region5", "Region6"};
	String wheelMenuVintage[] = new String[]{"Vintage1", "Vintage2", "Vintage3", "Vintage4", "Vintage5", "Vintage6"};
	String wheelMenuGrape[] = new String[]{"Grape1", "Grape2", "Grape3", "Grape4", "Grape5", "Grape6"};
	String wheelMenuBody[] = new String[]{"Body1", "Body2", "Body3", "Body4", "Body5", "Body6"};
	
	MyCellarsTO myCellarsTO = null;
	
	int[] details_table_view_id = new int[]{R.id.tableRow0, R.id.tableRow1, R.id.tableRow2, R.id.tableRow3, R.id.tableRow4, R.id.tableRow5, R.id.tableRow6, R.id.tableRow7, R.id.tableRow8, R.id.tableRow9};
	HashMap<Integer, String> display_details_output_id_map;
	HashMap<Integer, String> display_output_id_map;
	int[] details_table_view_output_id = new int[]{R.id.tableRow0_1, R.id.tableRow1_1, R.id.tableRow2_1, R.id.tableRow3_1, R.id.tableRow4_1, R.id.tableRow5_1, R.id.tableRow7_1, R.id.tableRow8_1, R.id.tableRow9_1};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_update);
        Display display = getWindowManager().getDefaultDisplay();
  		width = display.getWidth();
  		
  		myCellarsTO = new MyCellarsTO();
  		myCellarsTO.initMyCellarsTO();
  		
  		
  		display_details_output_id_map = new HashMap<Integer, String>();
  		for (int i =0; i< details_table_view_output_id.length; i++) {
  			display_details_output_id_map.put(details_table_view_output_id[i], "false");
  		}
  		
  		display_output_id_map = new HashMap<Integer, String>();
  		display_output_id_map.put(R.id.tableRow10_1, "false");
  		display_output_id_map.put(R.id.tableRow11_1, "false");
  		display_output_id_map.put(R.id.tableRow12_1, "false");
  		display_output_id_map.put(R.id.tableRow13_1, "false");
  		
  		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setTag("OFF");
		
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setTag("OFF");
		
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setTag("OFF");
		((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setTag("OFF");
		
		//For add mode init, set wineImage = "-"
		myCellarsTO.setWineImage("-");

		Bundle bundle = getIntent().getExtras();
  		ArrayList<String> wineDetail = new ArrayList<String>();
  		if (bundle != null) {
  			mode = "edit";
  			wineDetail = bundle.getStringArrayList("wineDetail");
  			mapBundleToMyCellarsTO(wineDetail);
  			
  		}
        
  		//********go to selection view************
  		TableRow temp = (TableRow)findViewById(R.id.tableRow0);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow1);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow2);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow3);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow5);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow8);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow9);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow10);
        temp.setOnClickListener(this);
        
      //********not go to selection view************
        temp = (TableRow)findViewById(R.id.tableRow4);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow7);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow11);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow12);
        temp.setOnClickListener(this);
        temp = (TableRow)findViewById(R.id.tableRow13);
        temp.setOnClickListener(this);
        
        ImageView wineImageView = (ImageView)findViewById(R.id.cellar_update_wine_image);
        wineImageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "imageView onClick");
				getAlertDialogGalleryCamera("Please select the image source").show();
				
			}
        	
        });
        
        ImageButton wish_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_wish_list_btn);
        if(mode.equals("add")){
	        wish_btn.setTag("ON");
	        myCellarsTO.setInstock("N");
        }
        wish_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ImageButton wish_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_wish_list_btn);
				if(wish_btn.getTag().toString().equals("OFF")) {
					wish_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_in_stock_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick_form));
					wish_btn.setTag("ON");
					myCellarsTO.setInstock("N");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_in_stock_btn)).setTag("OFF");
				}
			}
        	
        });
        
        ImageButton inStock_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_in_stock_btn);
        if(mode.equals("add")){
        	inStock_btn.setTag("OFF");
        }
        inStock_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ImageButton inStock_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_in_stock_btn);
				if(inStock_btn.getTag().toString().equals("OFF")) {
					inStock_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_wish_list_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick_form));
					inStock_btn.setTag("ON");
					myCellarsTO.setInstock("Y");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_wish_list_btn)).setTag("OFF");
				}
			}
        	
        });
        
        ImageButton cancel_update_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_cancel_update_button);
        cancel_update_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
        	
        });
        
        ImageButton save_button = (ImageButton) findViewById (R.id.cellar_save_button);
        save_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Log.i("Osmands", "save onClick()");
				if (!myCellarsTO.getWineName().equals("-")) {
					setMyCellarsTO(R.id.tableRow4_1, -1);
					setMyCellarsTO(R.id.tableRow7_1, -1);
					setMyCellarsTO(R.id.tableRow13_1, -1);
					WatsonWineDB db = new WatsonWineDB();
					boolean result = false;
					if (mode.equals("add")) {
						result = db.crateNewMyCellerRecord(MyCellarsUpdateItemsActivity.this, myCellarsTO.getWineName(),
								myCellarsTO.getRegion(), myCellarsTO.getVintage(), myCellarsTO.getGrape(), 
								myCellarsTO.getColour(), myCellarsTO.getBody(), myCellarsTO.getSweetness(),
								myCellarsTO.getSize(), myCellarsTO.getPrice(), myCellarsTO.getQuantity(), 
								myCellarsTO.getNote(), myCellarsTO.getRating(), myCellarsTO.getTasting_date(),
								myCellarsTO.getOccasion(), myCellarsTO.getInstock(), myCellarsTO.getWineImage(), 
								myCellarsTO.getUp_to_cms());
					} else {
						result = db.updateNewMyCellerRecord(MyCellarsUpdateItemsActivity.this, myCellarsTO.getId(), myCellarsTO.getWineName(),
								myCellarsTO.getRegion(), myCellarsTO.getVintage(), myCellarsTO.getGrape(), 
								myCellarsTO.getColour(), myCellarsTO.getBody(), myCellarsTO.getSweetness(),
								myCellarsTO.getSize(), myCellarsTO.getPrice(), myCellarsTO.getQuantity(), 
								myCellarsTO.getNote(), myCellarsTO.getRating(), myCellarsTO.getTasting_date(),
								myCellarsTO.getOccasion(), myCellarsTO.getInstock(), myCellarsTO.getWineImage(), 
								myCellarsTO.getUp_to_cms());
					}
					if (result){
						getAlertDialog("Changes saved").show();
					}
				} else {
					getAlertDialog("Error\nPlease give this wine a name.").show();
				}
			}
        	
        });
        
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
	                	for(int i =0; i<details_table_view_output_id.length; i++){
	                		if (display_details_output_id_map.get(details_table_view_output_id[i]).equals("true")) {
			                	 TableRow temp = (TableRow)findViewById(details_table_view_output_id[i]);
			                	 temp.setVisibility(View.VISIBLE);
	                		}
	                	 }
	                	if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).getTag().toString().equals("ON") ||
	                			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).getTag().toString().equals("ON") ||
	                			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).getTag().toString().equals("ON") ||
	                			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).getTag().toString().equals("ON")) {
	                		display_details_output_id_map.put(R.id.tableRow4_1, "true");
	                		TableRow temp = (TableRow)findViewById(R.id.tableRow4_1);
		                	 temp.setVisibility(View.VISIBLE);
                		} else {
                			display_details_output_id_map.put(R.id.tableRow4_1, "false");
                			TableRow temp = (TableRow)findViewById(R.id.tableRow4_1);
		                	 temp.setVisibility(View.GONE);
                		}
	                	
	                	if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).getTag().toString().equals("ON") ||
	                			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).getTag().toString().equals("ON") ||
	                			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).getTag().toString().equals("ON") ||
	                			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).getTag().toString().equals("ON")) {
	                		display_details_output_id_map.put(R.id.tableRow7_1, "true");
	                		TableRow temp = (TableRow)findViewById(R.id.tableRow7_1);
		                	 temp.setVisibility(View.VISIBLE);
                		} else {
                			display_details_output_id_map.put(R.id.tableRow7_1, "false");
                			TableRow temp = (TableRow)findViewById(R.id.tableRow7_1);
		                	 temp.setVisibility(View.GONE);
                		}
	                	 TableRow temp = (TableRow)findViewById(R.id.tableRow10);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow12);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow10_1);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11_1);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow12_1);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13_1);
	                	 temp.setVisibility(View.GONE);
	                	 ((TextView)findViewById(R.id.cellar_table_rating_bad)).setVisibility(View.GONE);
	    				 ((TextView)findViewById(R.id.cellar_table_rating_good)).setVisibility(View.GONE); 
	                }
	                if (x<width/3*2 && x > width/3){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_profile_top));
	                	 for(int i =0; i<details_table_view_id.length; i++){
		                	 TableRow temp = (TableRow)findViewById(details_table_view_id[i]);
		                	 temp.setVisibility(View.INVISIBLE);
	                	 }
	                	 for(int i =0; i<details_table_view_output_id.length; i++){
		                	 TableRow temp = (TableRow)findViewById(details_table_view_output_id[i]);
		                	 temp.setVisibility(View.GONE);
	                	 }
	                	 TableRow temp = (TableRow)findViewById(R.id.tableRow10);
	                	 temp.setVisibility(View.VISIBLE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11);
	                	 temp.setVisibility(View.VISIBLE);
	                	 if (display_output_id_map.get(R.id.tableRow10_1).equals("true")) {
		                	 temp = (TableRow)findViewById(R.id.tableRow10_1);
		                	 temp.setVisibility(View.VISIBLE);
	                	 } else {
	                		 temp = (TableRow)findViewById(R.id.tableRow10_1);
		                	 temp.setVisibility(View.GONE);
	                	 }
	                	 Log.i("Osmands", "display_output_id_map.get(R.id.tableRow11_1) = "+display_output_id_map.get(R.id.tableRow11_1));
	                	 if (display_output_id_map.get(R.id.tableRow11_1).equals("true")) {
	                		 temp = (TableRow)findViewById(R.id.tableRow11_1);
		                	 temp.setVisibility(View.VISIBLE);
	                	 } else {
	                		 temp = (TableRow)findViewById(R.id.tableRow11_1);
		                	 temp.setVisibility(View.GONE);
	                	 }
	                	 temp = (TableRow)findViewById(R.id.tableRow12);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow12_1);
	                	 temp.setVisibility(View.GONE);
	                	 ((TextView)findViewById(R.id.cellar_table_rating_bad)).setVisibility(View.GONE);
	    				 ((TextView)findViewById(R.id.cellar_table_rating_good)).setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13_1);
	                	 temp.setVisibility(View.GONE);
	                }
	                if (x>width/3*2){
	                	((ImageButton) findViewById(R.id.cellar_details_tab)).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_cellar_table_mynote_top));
	                	 for(int i =0; i<details_table_view_id.length; i++){
		                	 TableRow temp = (TableRow)findViewById(details_table_view_id[i]);
		                	 temp.setVisibility(View.INVISIBLE);
	                	 }
	                	 for(int i =0; i<details_table_view_output_id.length; i++){
		                	 TableRow temp = (TableRow)findViewById(details_table_view_output_id[i]);
		                	 temp.setVisibility(View.GONE);
	                	 }
	                	 TableRow temp = (TableRow)findViewById(R.id.tableRow10);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow10_1);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow11_1);
	                	 temp.setVisibility(View.GONE);
	                	 temp = (TableRow)findViewById(R.id.tableRow12);
	                	 temp.setVisibility(View.VISIBLE);
	                	 temp = (TableRow)findViewById(R.id.tableRow13);
	                	 temp.setVisibility(View.VISIBLE);
	                	 if (display_output_id_map.get(R.id.tableRow12_1).equals("true")) {
		                	 temp = (TableRow)findViewById(R.id.tableRow12_1);
		                	 temp.setVisibility(View.VISIBLE);
	                	 } else {
	                		 temp = (TableRow)findViewById(R.id.tableRow12_1);
		                	 temp.setVisibility(View.GONE);
	                	 }
	                	 temp = (TableRow)findViewById(R.id.tableRow13_1);
		                 temp.setVisibility(View.VISIBLE);
	                	 if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).getTag().toString().equals("ON") ||
	                			 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).getTag().toString().equals("ON")||
	                			 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).getTag().toString().equals("ON")||
	                			 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).getTag().toString().equals("ON")||
	                			 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).getTag().toString().equals("ON")||
	                			 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).getTag().toString().equals("ON")) {
		                	// temp = (TableRow)findViewById(R.id.tableRow13_1);
		                	// temp.setVisibility(View.VISIBLE);
	                		 ((TextView)findViewById(R.id.cellar_table_rating_bad)).setVisibility(View.VISIBLE);
	        				 ((TextView)findViewById(R.id.cellar_table_rating_good)).setVisibility(View.VISIBLE);
	                	 } else {
	                		 //temp = (TableRow)findViewById(R.id.tableRow13_1);
		                	 //temp.setVisibility(View.GONE);
	                		 ((TextView)findViewById(R.id.cellar_table_rating_bad)).setVisibility(View.GONE);
	        				 ((TextView)findViewById(R.id.cellar_table_rating_good)).setVisibility(View.GONE);
	                	 }
	                }
	            }
	            return true;
			}

			
        	
        });
       
    }
    
    private AlertDialog getAlertDialog(String message){

        Builder builder = new AlertDialog.Builder(MyCellarsUpdateItemsActivity.this);

        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
            	finish();
            }

        });

        return builder.create();

    }
    
    private AlertDialog getAlertDialogGalleryCamera(String message){

        Builder builder = new AlertDialog.Builder(MyCellarsUpdateItemsActivity.this);

        builder.setMessage(message);

        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
            	Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            	startActivityForResult(takePicture, 0);
            }

        });
        
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {
            	Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            	startActivityForResult(pickPhoto , 1);
            }

        });

        return builder.create();

    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
    	super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
    	Log.i("Osmands", "onActivityResult");
    	switch(requestCode) {
	    	case 0:
	    	    if(resultCode == RESULT_OK){  
	    	        //Uri selectedImage = imageReturnedIntent.getData();
	    	        Bitmap selectedImage = (Bitmap) imageReturnedIntent.getExtras().get("data"); 
	    	        //Log.i("Osmands", "selectedImage.getPath() = "+selectedImage.getPath());
	    	        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    	        selectedImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
	                Random randomGenerator = new Random();
	                
	                String newimagename = randomGenerator.nextInt()+".jpg";
	                String cache_image_path = "/storage/sdcard0/watsons_wine/MyCellarsChash/";
	                File f = new File(cache_image_path+ newimagename);
	                try {
	                    f.createNewFile();
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	                //write the bytes in file
	                FileOutputStream fo = null;
	                try {
	                	fo = new FileOutputStream(f.getAbsoluteFile());
	                } catch (FileNotFoundException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	                try {
	                    fo.write(bytes.toByteArray());
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	                Log.i("Osmands", "f.getAbsolutePath() = "+f.getAbsolutePath());
	    			myCellarsTO.setWineImage(newimagename);
	    			
    				Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
    				((ImageView) findViewById (R.id.cellar_update_wine_image)).setImageBitmap(myBitmap);

	    	        //imageview.setImageURI(selectedImage);
	    	    }
	
	    	break; 
	    	case 1:
	    	    if(resultCode == RESULT_OK){  
	    	        Uri selectedImage = imageReturnedIntent.getData();
	    	        Log.i("Osmands", "selectedImage.getPath() = "+selectedImage.getPath());
	                String selectedImagePath = getPath(selectedImage);
	                Log.i("Osmands", "selectedImagePath = "+selectedImagePath);
	                int slashIndex = selectedImagePath.lastIndexOf('/');
	        		String filenameWithExtension;
	        		filenameWithExtension = selectedImagePath.substring(slashIndex + 1);
	                //myCellarsTO.setWineImage(filenameWithExtension);
	                //Log.i("Osmands", "filenameWithExtension = "+filenameWithExtension);
	                try {
	                    FileInputStream fileis=new FileInputStream(selectedImagePath);
	                    BitmapFactory.Options options=new BitmapFactory.Options();
	                    options.inSampleSize = 8;
	                    Bitmap preview_bitmap=BitmapFactory.decodeStream(fileis,null,options);
	                    
	                    //BufferedInputStream bufferedstream=new BufferedInputStream(fileis);
	                    //byte[] bMapArray= new byte[bufferedstream.available()];
	                    //bufferedstream.read(bMapArray);
	                    //Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
	                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	                    preview_bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
		                Random randomGenerator = new Random();
		                
		                String newimagename = randomGenerator.nextInt()+".jpg";
		                String cache_image_path = "/storage/sdcard0/watsons_wine/MyCellarsChash/";
		                File f = new File(cache_image_path+ newimagename);
		                try {
		                    f.createNewFile();
		                } catch (IOException e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }
		                //write the bytes in file
		                FileOutputStream fo = null;
		                try {
		                	fo = new FileOutputStream(f.getAbsoluteFile());
		                } catch (FileNotFoundException e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }
		                try {
		                    fo.write(bytes.toByteArray());
		                } catch (IOException e) {
		                    // TODO Auto-generated catch block
		                    e.printStackTrace();
		                }
		                Log.i("Osmands", "f.getAbsolutePath() = "+f.getAbsolutePath());
		    			myCellarsTO.setWineImage(newimagename);
		    			
	    				Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
	    				    
	 			    	myBitmap = resizeBitmap(myBitmap, 80,80);
	    				((ImageView) findViewById (R.id.cellar_update_wine_image)).setImageBitmap(myBitmap);
	                } catch (FileNotFoundException e) {                 
	                    e.printStackTrace();
	                } catch (IOException e) {                   
	                    e.printStackTrace();
	                }  
	    	    }
	    	break;
    	}
    }
    
    public String getPath(Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    int column_index = cursor
	            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    
    public Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

        int originWidth  = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        // no need to resize
        if (originWidth < maxWidth && originHeight < maxHeight) {
            return bitmap;
        }

        int width  = originWidth;
        int height = originHeight;
        if (originWidth > maxWidth) {
            width = maxWidth;

            double i = originWidth * 1.0 / maxWidth;
            height = (int) Math.floor(originHeight / i);

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }

        return bitmap;
    }
	
   
    private void initWheel(int id, String[] wheel_data )

    {
    	((WheelView) findViewById(R.id.wheel)).setVisibility(View.VISIBLE);
		 ((LinearLayout)findViewById(R.id.wheel_date)).setVisibility(View.GONE);
        WheelView wheel = (WheelView) findViewById(id);
        wheel.setViewAdapter(new ArrayWheelAdapter(this, wheel_data));
        wheel.setVisibleItems(2);
        wheel.setCurrentItem(0);
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);

    }
    
    private void initDateWheel()

    {
    	((WheelView) findViewById(R.id.wheel)).setVisibility(View.GONE);
		 ((LinearLayout)findViewById(R.id.wheel_date)).setVisibility(View.VISIBLE);
    	final WheelView year = (WheelView) findViewById(R.id.wheel_year);
		year.setViewAdapter(new NumericWheelAdapter(this, 1901, 2100));
        year.setCyclic(true);

		final WheelView month = (WheelView) findViewById(R.id.wheel_month);
		month.setViewAdapter(new NumericWheelAdapter(this, 1, 12, "%02d"));
		month.setCyclic(true);

		final WheelView day = (WheelView) findViewById(R.id.wheel_day);
		day.setViewAdapter(new NumericWheelAdapter(this, 1, 31, "%02d"));
		day.setCyclic(true);
		
		Calendar c = Calendar.getInstance(); 
		int curYear = 112;
        int curMonth = c.get(Calendar.MONTH);  
        int curDay = c.get(Calendar.DAY_OF_MONTH);  
        year.setCurrentItem(curYear);
        month.setCurrentItem(curMonth);
        day.setCurrentItem(curDay);
        
        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				// do nothing
			}

			public void onScrollingFinished(WheelView wheel) {
				int currYearIndex = year.getCurrentItem();
				int currMonthIndex = month.getCurrentItem();
				int currDayIndex = day.getCurrentItem();
				((TextView)findViewById(R.id.cellars_input_text)).setText(indexConversion(currYearIndex, currMonthIndex, currDayIndex));
			}
		};
		
		year.addScrollingListener(scrollListener);
		month.addScrollingListener(scrollListener);
		day.addScrollingListener(scrollListener);
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
	
	public String indexConversion(int currYearIndex, int currMonthIndex, int currDayIndex) {
		int year = 1901 + currYearIndex;
		int month = 01 + currMonthIndex;
		int day = 01 + currDayIndex;

		String month_str = (String) ((month < 10)? "0" + month : ""+month);
		String day_str = (String) ((day/10 == 0)? "0" + day : ""+day);

		return year + "-" + month_str + "-" + day_str;
	}

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
		//LayoutInflater mInflater = getLayoutInflater();
        //View layout = mInflater.inflate(R.layout.my_cellars_selection, null);
		if (whellMenuNumber == 1) {
			((EditText) findViewById(R.id.cellars_input_text)).setText(wheelMenuRegion[getWheel(R.id.wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == 2) {
			((EditText) findViewById(R.id.cellars_input_text)).setText(wheelMenuVintage[getWheel(R.id.wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == 3) {
			((EditText) findViewById(R.id.cellars_input_text)).setText(wheelMenuGrape[getWheel(R.id.wheel).getCurrentItem()]);
		}
		if (whellMenuNumber == 5) {
			((EditText) findViewById(R.id.cellars_input_text)).setText(wheelMenuBody[getWheel(R.id.wheel).getCurrentItem()]);
		}

	}
	
	 private WheelView getWheel(int id)
     {
         return (WheelView) findViewById(id);
     }
    
    /*protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
    	super.onActivityResult(requestCode, resultCode, data);
    	Log.i("Osmands", "onActivityResult result code = "+resultCode);
		switch (resultCode)
		{
			case RESULT_OK:
				default:
			Log.i("Osmands", "onActivityResult result code = "+resultCode);
			Bundle b = data.getExtras();
			String input = b.getString("input");
			String who = b.getString("who");
			if (who.equals("name")){
				TableRow temp = (TableRow)findViewById(R.id.tableRow1_1);
				temp.setVisibility(View.VISIBLE);
				((TextView)findViewById(R.id.cellar_table_region_1)).setText(input);
			}
		}
	}*/

    
    
    @Override
    protected void onDestroy() {
    	Log.v("Osmands", "onDestroy()");
        super.onDestroy();
    }

	@Override
	public void onClick(View v) {
		if(v.getId() != R.id.tableRow4 && v.getId() != R.id.tableRow13 && v.getId() != R.id.tableRow7) {
			((RelativeLayout)findViewById(R.id.cellar_details_header)).setVisibility(View.GONE);
			((ScrollView)findViewById(R.id.cellar_details_home)).setVisibility(View.GONE);
			((RelativeLayout)findViewById(R.id.cellar_details_header)).setVisibility(View.GONE);
			((LinearLayout)findViewById(R.id.cellar_selection_layout)).setVisibility(View.VISIBLE);
		}
		
		switch(v.getId()) {
			case R.id.tableRow0:
				 ((TextView) findViewById(R.id.cellar_selection_title)).setText("Name");
				 ((WheelView) findViewById(R.id.wheel)).setVisibility(View.GONE);
				 ((LinearLayout) findViewById(R.id.wheel_date)).setVisibility(View.GONE);
				 row = R.id.tableRow0_1;
				 output_row = R.id.cellar_table_name_1;
				 break;
			case R.id.tableRow1:
				((TextView) findViewById(R.id.cellar_selection_title)).setText("Region");
				initWheel(R.id.wheel, wheelMenuRegion);
				row = R.id.tableRow1_1;
				output_row = R.id.cellar_table_region_1;
				whellMenuNumber = 1;
				break;
			case R.id.tableRow2:
				((TextView) findViewById(R.id.cellar_selection_title)).setText("Vintage");
				initWheel(R.id.wheel, wheelMenuVintage);
				row = R.id.tableRow2_1;
				output_row = R.id.cellar_table_vintage_1;
				whellMenuNumber = 2;
				break;
			case R.id.tableRow3:
				((TextView) findViewById(R.id.cellar_selection_title)).setText("Grape");
				initWheel(R.id.wheel, wheelMenuGrape);
				row = R.id.tableRow3_1;
				output_row = R.id.cellar_table_grape_1;
				whellMenuNumber = 3;
				break;
			case R.id.tableRow5:
				((TextView) findViewById(R.id.cellar_selection_title)).setText("Body");
				initWheel(R.id.wheel, wheelMenuBody);
				row = R.id.tableRow5_1;
				output_row = R.id.cellar_table_body_1;
				whellMenuNumber = 5;
				break;
			case R.id.tableRow8:
				((TextView) findViewById(R.id.cellar_selection_title)).setText("Price");
				 ((WheelView) findViewById(R.id.wheel)).setVisibility(View.GONE);
				 ((LinearLayout) findViewById(R.id.wheel_date)).setVisibility(View.GONE);
				 row = R.id.tableRow8_1;
				 output_row = R.id.cellar_table_price_1;
				 break;
			case R.id.tableRow9:
				((TextView) findViewById(R.id.cellar_selection_title)).setText("Quantity");
				 ((WheelView) findViewById(R.id.wheel)).setVisibility(View.GONE);
				 ((LinearLayout) findViewById(R.id.wheel_date)).setVisibility(View.GONE);
				 row = R.id.tableRow9_1;
				 output_row = R.id.cellar_table_quantity_1;
				 break;
			case R.id.tableRow10:
				((TextView) findViewById(R.id.cellar_selection_title)).setText("Testing Date");
				 initDateWheel();
				 row = R.id.tableRow10_1;
				 output_row = R.id.cellar_table_testing_date_1;
				 break;
			case R.id.tableRow4:
				 ((TableRow)findViewById(R.id.tableRow4_1)).setVisibility(View.VISIBLE);
				 row = R.id.tableRow4_1;
				 break;
			case R.id.tableRow7:
				 ((TableRow)findViewById(R.id.tableRow7_1)).setVisibility(View.VISIBLE);
				 row = R.id.tableRow7_1;
				 break;
			case R.id.tableRow11:
				 ((TableRow)findViewById(R.id.tableRow11_1)).setVisibility(View.VISIBLE);
				 ((WheelView) findViewById(R.id.wheel)).setVisibility(View.GONE);
				 ((LinearLayout)findViewById(R.id.wheel_date)).setVisibility(View.GONE);
				 ((EditText)findViewById(R.id.cellars_input_text)).setMinLines(5);
				 row = R.id.tableRow11_1;
				 output_row = R.id.cellar_table_occasion_1;
				 break;
			case R.id.tableRow12:
				 ((TableRow)findViewById(R.id.tableRow12_1)).setVisibility(View.VISIBLE);
				 ((WheelView) findViewById(R.id.wheel)).setVisibility(View.GONE);
				 ((LinearLayout)findViewById(R.id.wheel_date)).setVisibility(View.GONE);
				 ((EditText)findViewById(R.id.cellars_input_text)).setMinLines(5);
				 row = R.id.tableRow12_1;
				 output_row = R.id.cellar_table_note_1;
				 break;
			case R.id.tableRow13:
				 //((TableRow)findViewById(R.id.tableRow13_1)).setVisibility(View.VISIBLE);
				 ((TextView)findViewById(R.id.cellar_table_rating_bad)).setVisibility(View.VISIBLE);
				 ((TextView)findViewById(R.id.cellar_table_rating_good)).setVisibility(View.VISIBLE);
				 row = R.id.tableRow13_1;
				 break;
				
		}
		
		setColorButtonDisplay();
		setRatingButtonDisplay();
		setSizeButtonDisplay();
		ImageButton done_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_done_button);
        done_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String input = ((EditText) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellars_input_text)).getText().toString();
				TableRow temp = (TableRow)findViewById(row);
				temp.setVisibility(View.VISIBLE);
				((TextView)findViewById(output_row)).setText(input);
				((TextView)findViewById(R.id.cellar_wine_name)).setText(((TextView)findViewById(R.id.cellar_table_name_1)).getText().toString());
				((RelativeLayout)findViewById(R.id.cellar_details_header)).setVisibility(View.VISIBLE);
				((ScrollView)findViewById(R.id.cellar_details_home)).setVisibility(View.VISIBLE);
				((LinearLayout)findViewById(R.id.cellar_selection_layout)).setVisibility(View.GONE);
				((EditText) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellars_input_text)).setText("");
				((EditText)findViewById(R.id.cellars_input_text)).setMinLines(1);
				Log.i("Osmands", "row = "+row);
				String setTrue = display_details_output_id_map.put(row, "true");
				Log.i("Osmands", "setTrue ="+setTrue);
				setTrue = display_output_id_map.put(row, "true");
				Log.i("Osmands", "setTrue2 ="+setTrue);
				setMyCellarsTO(row, output_row);
			}
        	
        });
        
        ImageButton cancel_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_cancel_button);
        cancel_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				((EditText) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellars_input_text)).setText("");
				 ((EditText)findViewById(R.id.cellars_input_text)).setMinLines(1);
				 ((RelativeLayout)findViewById(R.id.cellar_details_header)).setVisibility(View.VISIBLE);
				((ScrollView)findViewById(R.id.cellar_details_home)).setVisibility(View.VISIBLE);
				((LinearLayout)findViewById(R.id.cellar_selection_layout)).setVisibility(View.GONE);
				
				
			}
        	
        });
		
	}
	
	public void mapBundleToMyCellarsTO(ArrayList<String> wineDetail){
		myCellarsTO.setId(Integer.valueOf(wineDetail.get(0)));
		myCellarsTO.setWineName(wineDetail.get(1));
		if (!wineDetail.get(1).equals("-")) {
			((TextView)findViewById(R.id.cellar_wine_name)).setText(wineDetail.get(1));
			display_details_output_id_map.put(R.id.tableRow0_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow0_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 ((TextView)findViewById(R.id.cellar_table_name_1)).setText(wineDetail.get(1));
		}
		if (wineDetail.get(2).equals("N")) {
			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_wish_list_btn)).setTag("ON");
			myCellarsTO.setInstock("N");
			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_in_stock_btn)).setTag("OFF");
			((ImageButton) findViewById (R.id.cellar_wish_list_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
			((ImageButton) findViewById (R.id.cellar_in_stock_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick_form));
		}else {
			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_wish_list_btn)).setTag("OFF");
			myCellarsTO.setInstock("Y");
			((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_in_stock_btn)).setTag("ON");
			((ImageButton) findViewById (R.id.cellar_in_stock_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick));
			((ImageButton) findViewById (R.id.cellar_wish_list_btn)).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_tick_form));
		}
		
		if (wineDetail.get(4).equals("-") || wineDetail.get(4) == null) {
			myCellarsTO.setWineImage("-");
			((ImageView) findViewById (R.id.cellar_update_wine_image)).setImageResource(R.drawable.bg_photo_container_camera);
		} else {
			String cache_image_path = "/storage/sdcard0/watsons_wine/MyCellarsChash/";
			myCellarsTO.setWineImage(wineDetail.get(4));
			File imgFile = new File(cache_image_path+wineDetail.get(4));
			if (imgFile.exists()) {
				//holder.itemImageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_photo_container_small));
				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				((ImageView) findViewById (R.id.cellar_update_wine_image)).setImageBitmap(myBitmap);
				//holder.newsImage.setBackgroundDrawable(new BitmapDrawable(myBitmap));
				Log.i("osmand", "setImageBitmap");
			}
			
		}
	
		
		myCellarsTO.setRegion(wineDetail.get(5));
		if (!wineDetail.get(5).equals("-")) {
			display_details_output_id_map.put(R.id.tableRow1_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow1_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 	((TextView)findViewById(R.id.cellar_table_region_1)).setText(wineDetail.get(5));
		}
		myCellarsTO.setVintage(wineDetail.get(6));
		if (!wineDetail.get(6).equals("-")) {
			display_details_output_id_map.put(R.id.tableRow2_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow2_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 ((TextView)findViewById(R.id.cellar_table_vintage_1)).setText(wineDetail.get(6));
		}
		myCellarsTO.setGrape(wineDetail.get(7));
		if (!wineDetail.get(7).equals("-")) {
			display_details_output_id_map.put(R.id.tableRow3_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow3_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 ((TextView)findViewById(R.id.cellar_table_grape_1)).setText(wineDetail.get(7));
		}
		myCellarsTO.setColour(wineDetail.get(8));
		if (!wineDetail.get(8).equals("-")) {
			display_details_output_id_map.put(R.id.tableRow4_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow4_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 //((TextView)findViewById(R.id.cellar_table_colour_1)).setText(wineDetail.get(8));
       	 	if (wineDetail.get(8).equals("Red")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setImageResource(R.drawable.icon_tick);
       	 	}
	       	if (wineDetail.get(8).equals("Rose")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setImageResource(R.drawable.icon_tick);
	   	 	}
	       	if (wineDetail.get(8).equals("White")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setImageResource(R.drawable.icon_tick);
	  	 	}
	       	if (wineDetail.get(8).equals("Other")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setImageResource(R.drawable.icon_tick);
	  	 	}
		}
		myCellarsTO.setBody(wineDetail.get(9));
		if (!wineDetail.get(9).equals("-")) {
			display_details_output_id_map.put(R.id.tableRow5_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow5_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 ((TextView)findViewById(R.id.cellar_table_body_1)).setText(wineDetail.get(9));
		}
		myCellarsTO.setSize(wineDetail.get(11));
		if (!wineDetail.get(11).equals("-")) {
			display_details_output_id_map.put(R.id.tableRow7_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow7_1);
       	 	temp.setVisibility(View.VISIBLE);
       	  //((TextView)findViewById(R.id.cellar_table_size_1)).setText(wineDetail.get(11));
       	 	if (wineDetail.get(8).equals("37.5CL")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setImageResource(R.drawable.icon_tick);
	   	 	}
	       	if (wineDetail.get(8).equals("75CL")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setImageResource(R.drawable.icon_tick);
	   	 	}
	       	if (wineDetail.get(8).equals("150CL")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setImageResource(R.drawable.icon_tick);
	  	 	}
	       	if (wineDetail.get(8).equals("Other")) {
		       	 ((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setTag("ON");
		       	((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setImageResource(R.drawable.icon_tick);
	  	 	}
		}
		myCellarsTO.setPrice(Double.valueOf(wineDetail.get(12)));
		if (Double.valueOf(wineDetail.get(12)) != 0) {
			display_details_output_id_map.put(R.id.tableRow8_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow8_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 ((TextView)findViewById(R.id.cellar_table_price_1)).setText("$"+wineDetail.get(12));
		}
		
		myCellarsTO.setQuantity(Integer.valueOf(wineDetail.get(13)));
		if (Integer.valueOf(wineDetail.get(13)) != 0) {
			display_details_output_id_map.put(R.id.tableRow9_1, "true");
			TableRow temp = (TableRow)findViewById(R.id.tableRow9_1);
       	 	temp.setVisibility(View.VISIBLE);
       	 	((TextView)findViewById(R.id.cellar_table_quantity_1)).setText(wineDetail.get(13));
		}
		
		myCellarsTO.setTasting_date(wineDetail.get(15));
		if (!wineDetail.get(15).equals("-")) {
			display_output_id_map.put(R.id.tableRow10_1, "true");
			//TableRow temp = (TableRow)findViewById(R.id.tableRow10_1);
       	 	//temp.setVisibility(View.VISIBLE);
       	 	((TextView)findViewById(R.id.cellar_table_testing_date_1)).setText(wineDetail.get(15));
		}
		
		myCellarsTO.setOccasion(wineDetail.get(16));
		Log.i("Osmands", "wineDetail.get(16) = "+wineDetail.get(16));
		if (!wineDetail.get(16).equals("-")) {
			display_output_id_map.put(R.id.tableRow11_1, "true");
			//TableRow temp = (TableRow)findViewById(R.id.tableRow11_1);
       	 	//temp.setVisibility(View.VISIBLE);
       	 	((TextView)findViewById(R.id.cellar_table_occasion_1)).setText(wineDetail.get(16));
		}
		
		myCellarsTO.setNote(wineDetail.get(14));
		if (!wineDetail.get(14).equals("-")) {
			display_output_id_map.put(R.id.tableRow12_1, "true");
			//TableRow temp = (TableRow)findViewById(R.id.tableRow12_1);
       	 	//temp.setVisibility(View.VISIBLE);
       	 	((TextView)findViewById(R.id.cellar_table_note_1)).setText(wineDetail.get(14));
		}
		
		int favourite = Integer.valueOf(wineDetail.get(3));
		myCellarsTO.setRating(favourite);
		ArrayList<ImageButton> itemView = new ArrayList<ImageButton>();
		itemView.add(((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)));
		itemView.add(((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)));
		itemView.add(((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)));
		itemView.add(((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)));
		itemView.add(((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)));
		itemView.add(((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)));
		for (int i=0; i <favourite; i++) {
			itemView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_wine_glass_full));
			itemView.get(i).setTag("ON");
		}
		for (int i=favourite; i <6; i++) {
			itemView.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_wine_glass));
			itemView.get(i).setTag("OFF");
		}
		
		
	}
	
	public void setMyCellarsTO(int id, int get_output_id){
		String temp = null;
		if (((TextView)findViewById(get_output_id)) != null){
			temp = ((TextView)findViewById(get_output_id)).getText().toString();
		}
		switch(id) {
			case R.id.tableRow0_1:
				 myCellarsTO.setWineName(temp);
				 break;
			case R.id.tableRow1_1:
				 myCellarsTO.setRegion(temp);
				 break;
			case R.id.tableRow2_1:
				myCellarsTO.setVintage(temp);
				break;
			case R.id.tableRow3_1:
				myCellarsTO.setGrape(temp);
				break;
			case R.id.tableRow4_1:
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).getTag().equals("ON")){
					myCellarsTO.setColour("Red");
					 break;	
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).getTag().equals("ON")){
					myCellarsTO.setColour("White");
					 break;	
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).getTag().equals("ON")){
					myCellarsTO.setColour("Rose");
					 break;	
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).getTag().equals("ON")){
					myCellarsTO.setColour("Other");
					 break;	
				}
				myCellarsTO.setColour("-");
				break;
			case R.id.tableRow5_1:
				myCellarsTO.setBody(temp);
				break;
			case R.id.tableRow6:
				myCellarsTO.setSweetness(temp);
				 break;
			case R.id.tableRow7_1:
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).getTag().toString().equals("ON")) {
					myCellarsTO.setSize("37.5CL");
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).getTag().toString().equals("ON")) {
					myCellarsTO.setSize("75CL");
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).getTag().toString().equals("ON")) {
					myCellarsTO.setSize("150CL");
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).getTag().toString().equals("ON")) {
					myCellarsTO.setSize("Others");
					break;
				}
				myCellarsTO.setSize("-");
				break;
			case R.id.tableRow8_1:
				myCellarsTO.setPrice(temp.isEmpty()? 0.00 : Double.valueOf(temp));
				 break;
			case R.id.tableRow9_1:
				myCellarsTO.setQuantity(Integer.valueOf(temp));
				 break;
			case R.id.tableRow10_1:
				myCellarsTO.setTasting_date(temp);
				 break;
			case R.id.tableRow11_1:
				myCellarsTO.setOccasion(temp);
				 break;
			case R.id.tableRow12_1:
				myCellarsTO.setNote(temp);
				 break;
			case R.id.tableRow13_1:
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).getTag().toString().equals("ON")) {
					myCellarsTO.setRating(6);
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).getTag().toString().equals("ON")) {
					myCellarsTO.setRating(5);
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).getTag().toString().equals("ON")) {
					myCellarsTO.setRating(4);
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).getTag().toString().equals("ON")) {
					myCellarsTO.setRating(3);
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).getTag().toString().equals("ON")) {
					myCellarsTO.setRating(2);
					break;
				}
				if (((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).getTag().toString().equals("ON")) {
					myCellarsTO.setRating(1);
					break;
				}
				myCellarsTO.setRating(0);
				 break;
				
		}
	}
	
	public void setColorButtonDisplay(){
		ImageButton colour_red_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn);
		//colour_red_btn.setTag("OFF");
		colour_red_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				if (v.getTag().toString().equals("OFF")) {
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setTag("ON");
				} else {
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setTag("OFF");
				}
			}
        	
        });
		
		ImageButton colour_white_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn);
		//colour_white_btn.setTag("OFF");
		colour_white_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				ImageButton button = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn);
				if (v.getTag().toString().equals("OFF")) {
					button.setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setTag("OFF");
					button.setTag("ON");
				} else {
					button.setImageResource(android.R.color.transparent);
					button.setTag("OFF");
				}
			}
        	
        });
		
		ImageButton colour_rose_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn);
		//colour_rose_btn.setTag("OFF");
		colour_rose_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				ImageButton button = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn);
				if (v.getTag().toString().equals("OFF")) {
					button.setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn)).setTag("OFF");
					button.setTag("ON");
				} else {
					button.setImageResource(android.R.color.transparent);
					button.setTag("OFF");
				}
			}
        	
        });
		
		ImageButton colour_other_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn);
		//colour_other_btn.setTag("OFF");
		colour_other_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				ImageButton button = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_other_btn);
				if (v.getTag().toString().equals("OFF")) {
					button.setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_red_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_rose_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_colour_white_btn)).setTag("OFF");
					button.setTag("ON");
				} else {
					button.setImageResource(android.R.color.transparent);
					button.setTag("OFF");
				}
			}
        	
        });
	}
	
	public void setSizeButtonDisplay(){
		ImageButton size_37_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn);
		//colour_red_btn.setTag("OFF");
		size_37_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				if (v.getTag().toString().equals("OFF")) {
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setTag("ON");
				} else {
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setTag("OFF");
				}
			}
        	
        });
		
		ImageButton size_75_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn);
		//colour_white_btn.setTag("OFF");
		size_75_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				ImageButton button = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn);
				if (v.getTag().toString().equals("OFF")) {
					button.setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setTag("OFF");
					button.setTag("ON");
				} else {
					button.setImageResource(android.R.color.transparent);
					button.setTag("OFF");
				}
			}
        	
        });
		
		ImageButton colour_rose_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn);
		//colour_rose_btn.setTag("OFF");
		colour_rose_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				ImageButton button = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn);
				if (v.getTag().toString().equals("OFF")) {
					button.setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn)).setTag("OFF");
					button.setTag("ON");
				} else {
					button.setImageResource(android.R.color.transparent);
					button.setTag("OFF");
				}
			}
        	
        });
		
		ImageButton size_other_btn = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn);
		//colour_other_btn.setTag("OFF");
		size_other_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
				ImageButton button = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_other_btn);
				if (v.getTag().toString().equals("OFF")) {
					button.setImageResource(R.drawable.icon_tick);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setImageResource(android.R.color.transparent);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_37_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_75_btn)).setTag("OFF");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_size_150_btn)).setTag("OFF");
					button.setTag("ON");
				} else {
					button.setImageResource(android.R.color.transparent);
					button.setTag("OFF");
				}
			}
        	
        });
	}
	
	public void setRatingButtonDisplay(){
		ImageButton rating_btn_0 = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0);
		rating_btn_0.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setTag("ON");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setImageResource(R.drawable.icon_wine_glass);
			}
        	
        });
		
		ImageButton rating_btn_1 = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1);
		rating_btn_1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setTag("ON");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setImageResource(R.drawable.icon_wine_glass);
			}
        	
        });
		
		ImageButton rating_btn_2 = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2);
		rating_btn_2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setTag("ON");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setImageResource(R.drawable.icon_wine_glass);
			}
        	
        });
		
		ImageButton rating_btn_3 = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3);
		rating_btn_3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setTag("ON");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setImageResource(R.drawable.icon_wine_glass);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setImageResource(R.drawable.icon_wine_glass);
			}
        	
        });
		
		ImageButton rating_btn_4 = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4);
		rating_btn_4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setTag("ON");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setImageResource(R.drawable.icon_wine_glass);
			}
        	
        });
		
		ImageButton rating_btn_5 = (ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5);
		rating_btn_5.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Log.i("Osmands", "v.getTag().toString() = "+v.getTag().toString());
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setTag("ON");
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_0)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_1)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_2)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_3)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_4)).setImageResource(R.drawable.icon_wine_glass_full);
					((ImageButton) MyCellarsUpdateItemsActivity.this.findViewById(R.id.cellar_table_rating_5)).setImageResource(R.drawable.icon_wine_glass_full);
			}
        	
        });
	}
}
