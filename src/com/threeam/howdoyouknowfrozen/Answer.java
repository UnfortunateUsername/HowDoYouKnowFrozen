package com.threeam.howdoyouknowfrozen;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.threeam.howdoyouknowfrozen.DBHelper.AnswerTable;
import com.threeam.howdoyouknowfrozen.DBHelper.QuizHelper;

public class Answer {
	private int id;
	private int question_id;
	private String title;
	private boolean isRight;
	
	public Answer(int id, int question_id, String title, boolean isRight) {
		this.setId(id);
		this.setQuestion_id(question_id);
		this.setTitle(title);
		this.setRight(isRight);
	}
	
	public static List<Answer> GetByQuestionID(QuizHelper db, int questionID) {
		SQLiteDatabase database = db.getWritableDatabase();
		Cursor c = db.fetchAnswersByQuestion(database, questionID);
		List<Answer> answers = new ArrayList<Answer>();
		
		while (c.moveToNext()) {
			int id = c.getInt(
				    c.getColumnIndexOrThrow(AnswerTable.COLUMN_ID));
			int question_id = c.getInt(
				    c.getColumnIndexOrThrow(AnswerTable.COLUMN_QUESTION_ID));
			String title = c.getString(
				    c.getColumnIndexOrThrow(AnswerTable.COLUMN_TITLE));
			boolean isRight = c.getString(
					c.getColumnIndexOrThrow(AnswerTable.COLUMN_IS_RIGHT)).equals("true");
					
			Answer a = new Answer(id, question_id, title, isRight);
			answers.add(a);
		}
		return answers;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
