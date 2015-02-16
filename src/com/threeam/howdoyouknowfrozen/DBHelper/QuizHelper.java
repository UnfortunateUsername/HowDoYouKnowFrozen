package com.threeam.howdoyouknowfrozen.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "frozenquiz.db";
	private static final int DATABASE_VERSION = 8;

	private static QuizHelper instance;

    private QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public static QuizHelper getInstance(Context context) {
    	if (instance==null) {
    		instance = new QuizHelper(context.getApplicationContext());
    	}
    	return instance;
    }

    
    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
      QuestionTable.onCreate(database);
      AnswerTable.onCreate(database);
      initialiseDB(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
        int newVersion) {
      QuestionTable.onUpgrade(database, oldVersion, newVersion);
      AnswerTable.onUpgrade(database, oldVersion, newVersion);
      initialiseDB(database);
    }
    
    private void initialiseDB(SQLiteDatabase database) {
    	Log.w(QuizHelper.class.getName(), "Re-initialising database");
    	
        	
		makeQuestion(database, 
				"How did we make frozen?", 
				new String[]{"I wish I knew", "true"}, 
				new String[]{"Slugs and snails and puppy dog tails", "false"}, 
				new String[]{"Disney magic", "false"});
		
		makeQuestion(database, 
				"Who has ice magic?", 
				new String[]{"I wish I knew", "false"}, 
				new String[]{"Elsa", "true"}, 
				new String[]{"Anna", "false"});
			
		makeQuestion(database, 
				"What's the name of the snowman?", 
				new String[]{"I wish I knew", "false"}, 
				new String[]{"Sven", "false"}, 
				new String[]{"Olaf", "true"});
		
		makeQuestion(database, 
				"Do you wanna build a ..?", 
				new String[]{"I wish I knew", "false"}, 
				new String[]{"Snowman", "true"}, 
				new String[]{"Fish farm", "false"});
		
		makeQuestion(database, 
				"The duke is like an agile ..?", 
				new String[]{"I wish I knew", "false"}, 
				new String[]{"Slug", "false"}, 
				new String[]{"Peacock", "false"});
		
		makeQuestion(database, 
				"Put me in summer and I'll be a ..?", 
				new String[]{"I wish I knew", "false"}, 
				new String[]{"Slug", "false"}, 
				new String[]{"Happy snowman!", "false"});
		
    }


	private void makeQuestion(SQLiteDatabase database, String question,
			String[] answerOne, String[] answerTwo, String[] answerThree) {
		long questionID;
		questionID = this.createQuestion(database, question);
		this.createAnswer(database, questionID, answerOne);
		this.createAnswer(database, questionID, answerTwo);
		this.createAnswer(database, questionID, answerThree);
	}
    
    private long createQuestion(SQLiteDatabase database, String question) {
    	// add the question
        ContentValues questionValues = new ContentValues();
        questionValues.put(QuestionTable.COLUMN_TITLE, question);
        Log.d("table", QuestionTable.TABLE_QUESTION);
        Log.d("questionValues", questionValues.toString());
        Log.d("db", database.toString());
        return database.insert(QuestionTable.TABLE_QUESTION, null, questionValues);
    }
    
    private void createAnswer(SQLiteDatabase database, long questionID, String[] answer) {
        // Add the answers
        ContentValues answerValues = new ContentValues();
        answerValues.put(AnswerTable.COLUMN_QUESTION_ID, questionID);
        answerValues.put(AnswerTable.COLUMN_TITLE, answer[0]);
        answerValues.put(AnswerTable.COLUMN_IS_RIGHT, answer[1]);
        database.insert(AnswerTable.TABLE_ANSWER, null, answerValues);
    }
    
   public Cursor fetchRandomQuestions(SQLiteDatabase database, int noOfQuestions) {
	// Define a projection that specifies which columns from the database
	// you will actually use after this query.
	String[] projection = {
		Integer.toString(noOfQuestions),
	    };
	
	Cursor cursor = database.rawQuery("SELECT * FROM "
			+ QuestionTable.TABLE_QUESTION + " ORDER BY RANDOM() LIMIT ?", 
			projection);
	
	cursor.moveToFirst();
	return cursor;
   }
   
   public Cursor fetchQuestionByID(SQLiteDatabase database, int questionID) {
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    QuestionTable.COLUMN_ID,
		    QuestionTable.COLUMN_TITLE,
		    };
		
		String[] whereValues = {
				Integer.toString(questionID)
		};

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
		    QuestionTable.COLUMN_TITLE + " DESC";

		Cursor cursor = database.query(
		    QuestionTable.TABLE_QUESTION,  // The table to query
		    projection,                               // The columns to return
		    QuestionTable.COLUMN_ID + "=?",         // The columns for the WHERE clause
		    whereValues,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
		    );
		
		cursor.moveToFirst();
		return cursor;
	   }
   
   public Cursor fetchAnswersByQuestion(SQLiteDatabase database, int questionID) {
		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    AnswerTable.COLUMN_ID,
		    AnswerTable.COLUMN_QUESTION_ID,
		    AnswerTable.COLUMN_TITLE,
		    AnswerTable.COLUMN_IS_RIGHT
		    };
		
		String[] whereValues = {
				Integer.toString(questionID)
		};

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
		    AnswerTable.COLUMN_TITLE + " DESC";

		Cursor cursor = database.query(
		    AnswerTable.TABLE_ANSWER,  // The table to query
		    projection,                               // The columns to return
		    AnswerTable.COLUMN_QUESTION_ID + "=?",      // The columns for the WHERE clause
		    whereValues,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
		    );
		
		cursor.moveToFirst();
		String data = cursor.getString(cursor.getColumnIndex("title"));
		Log.d("answer title", data);
		return cursor;
	   }
}
