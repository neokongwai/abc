package watsons.wine.mycellars;

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
import android.widget.ImageButton;
import android.widget.TextView;

public class MyCellarsMainActivity extends Activity {
	
	DBHelper dbhelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cellars_main);
        //**********test*************
       // WatsonWineDB temp = new WatsonWineDB();
       // temp.createOrOpenMyCellarTable(this);
       // temp.addToMyCellerFromWineList(this, "wineName", "region", "vintage", "grape", "colour", "body", "sweetness", "size",0.9, "note", "wineImage");
        //**********test*************
        openDatabase();
        Cursor cursor = getCursor();
        while(cursor.moveToNext()){
        	String total = cursor.getString(0);
            String last_add = cursor.getString(1);
            String favourite = cursor.getString(2);
            String in_stock = cursor.getString(3);
            String avg = cursor.getString(4) == null ? "0.00" : cursor.getString(4);
            
            ((TextView) findViewById (R.id.cellar_last_added)).setText(last_add);
            ((TextView) findViewById (R.id.cellar_favourite_wine)).setText(favourite);
            ((TextView) findViewById (R.id.cellar_total_wine)).setText("You have "+total+" wines in My Cellar");
            ((TextView) findViewById (R.id.cellar_in_stock_wine)).setText("You have "+in_stock+" wines in stock");
            ((TextView) findViewById (R.id.cellar_wish_wine)).setText("You have "+(Integer.valueOf(total) -Integer.valueOf(in_stock))+" wines in wish");
            ((TextView) findViewById (R.id.cellar_avg_wine)).setText("You have average price per bottle is "+avg);

        }
       ImageButton go_button = (ImageButton)findViewById(R.id.cellar_go_button);
       go_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(MyCellarsMainActivity.this, MyCellarsListIemsActivity.class);
				Intent intent = new Intent(getParent(), MyCellarsListIemsActivity.class);
				TabGroupBase parentActivity = (TabGroupBase)getParent();
	        	parentActivity.startChildActivity("MyCellarsMain", intent);
				//startActivity(intent);
				//finish();
				
			}
    	   
       });
    }
    
    private void openDatabase(){
        dbhelper = new DBHelper(this); 
    }
    
    private Cursor getCursor(){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(1), (SELECT b."+DbConstants.MY_CELLAR_WINE_NAME+" FROM "+DbConstants.MY_CELLAR_TABLE_NAME
        		+" b WHERE b._id = (SELECT MAX(_id) FROM "+DbConstants.MY_CELLAR_TABLE_NAME+")) last_add, (SELECT c."+DbConstants.MY_CELLAR_WINE_NAME+" FROM "+DbConstants.MY_CELLAR_TABLE_NAME+" c WHERE c."
        		+DbConstants.MY_CELLAR_RATING+" = (SELECT MAX(a."+DbConstants.MY_CELLAR_RATING+") FROM "+DbConstants.MY_CELLAR_TABLE_NAME+") LIMIT 1) favourite, (SELECT COUNT(1) FROM "
        		+DbConstants.MY_CELLAR_TABLE_NAME+" d WHERE d."+DbConstants.MY_CELLAR_INSTOCK+" = 'Y') in_stock, AVG("+DbConstants.MY_CELLAR_PRICE+") avg  FROM "
        		+DbConstants.MY_CELLAR_TABLE_NAME+" a",  null);
        
        //String[] columns = {DbConstants.NOTIFICATION_TITLE, DbConstants.NOTIFICATION_TIME, DbConstants.NOTIFICATION_READ};

        //Cursor cursor = db.query(DbConstants.NOTIFICATION_TABLE_NAME, null, null, null, null, null, DbConstants.NOTIFICATION_TIME);
        startManagingCursor(cursor);

        return cursor;
    }
    
  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
               super.onActivityResult(requestCode, resultCode, data);
    }*/
    
    @Override
    protected void onDestroy() {
    	Log.v("Osmands", "onDestroy()");
        super.onDestroy();
    }
}
