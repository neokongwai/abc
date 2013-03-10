package com.android.sqlite;

import android.provider.BaseColumns;

public interface DbConstants extends BaseColumns {
	public static final String NOTIFICATION_TABLE_NAME = "notification_table";
    public static final String NOTIFICATION_COUNT_TABLE_NAME = "notification_count_table";
    public static final String MY_CELLAR_TABLE_NAME = "my_cellar_table";
	
	public static final String NOTIFICATION_COUNT = "count";
	
	public static final String NOTIFICATION_TITLE = "contentTitle";
	public static final String NOTIFICATION_BODY = "contentBody";
	public static final String NOTIFICATION_TIME = "messageTime";
	public static final String NOTIFICATION_READ = "read";
	
	
	public static final String MY_CELLAR_WINE_NAME = "wineName";//1
    public static final String MY_CELLAR_REGION = "region";
    public static final String MY_CELLAR_VINTAGE = "vintage";
    public static final String MY_CELLAR_GRAPE = "grape";
    public static final String MY_CELLAR_COLOUR = "colour";
    public static final String MY_CELLAR_BODY = "body";
    public static final String MY_CELLAR_SWEETNESS = "sweetness";
    public static final String MY_CELLAR_SIZE = "size";
    public static final String MY_CELLAR_PRICE = "price";
    public static final String MY_CELLAR_QUANTITY = "quantity";
    public static final String MY_CELLAR_NOTE = "note";
    public static final String MY_CELLAR_RATING = "rating";
    public static final String MY_CELLAR_TASTING_DATE = "tasting_date";
    public static final String MY_CELLAR_OCCASION = "occasion";
    public static final String MY_CELLAR_INSTOCK = "instock";
    public static final String MY_CELLAR_IMAGE = "image";
    public static final String MY_CELLAR_CREATE_DATE = "create_date";
    public static final String MY_CELLAR_MODIFY_DATE = "modify_date";
    public static final String MY_CELLAR_UP_TO_CMS = "up_to_cms";
    public static final String MY_CELLAR_SERVER_ID = "server_id";//20
    public static final String MY_CELLAR_WINE_URL_ID = "url_id";//21
     

}