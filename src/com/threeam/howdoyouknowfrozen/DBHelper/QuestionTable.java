package com.threeam.howdoyouknowfrozen.DBHelper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestionTable {

	// Database table
	  public static final String TABLE_QUESTION = "question";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_TITLE = "title";

	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
	      + TABLE_QUESTION
	      + "(" 
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_TITLE + " text not null "
	      + ");";

	  public static void onCreate(SQLiteDatabase database) {
		  Log.d("question sql", DATABASE_CREATE);
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(QuestionTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
	    onCreate(database);
	  }
}
