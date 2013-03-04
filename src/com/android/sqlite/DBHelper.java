package com.android.sqlite;

import static com.android.sqlite.DbConstants.NOTIFICATION_TABLE_NAME;
import static android.provider.BaseColumns._ID;
import static com.android.sqlite.DbConstants.NOTIFICATION_COUNT;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "watsonwine.db";
    private final static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.i("Osmand", "DBHelper onCreate");
        /*final String INIT_TABLE = "CREATE TABLE " + NOTIFICATION_TABLE_NAME + " (" +
                                  _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                  NOTIFICATION_COUNT + " INT DEFAULT 0 );"; 
        db.execSQL(INIT_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + NOTIFICATION_TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

}