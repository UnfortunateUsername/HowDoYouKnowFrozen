package com.threeam.howdoyouknowfrozen.DBHelper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AnswerTable {

	// Database table
	  public static final String TABLE_ANSWER = "answer";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_QUESTION_ID = "question_id";
	  public static final String COLUMN_TITLE = "title";
	  public static final String COLUMN_IS_RIGHT = "is_right";

	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
	      + TABLE_ANSWER
	      + "(" 
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_QUESTION_ID + " integer, "
	      + COLUMN_TITLE + " text not null, "
	      + COLUMN_IS_RIGHT + " text not null "
	      + ");";

	  public static void onCreate(SQLiteDatabase database) {
		  Log.d("answer sql", DATABASE_CREATE);
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(AnswerTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
	    onCreate(database);
	  }
}
