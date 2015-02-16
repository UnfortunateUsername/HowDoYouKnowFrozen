package com.threeam.howdoyouknowfrozen;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.threeam.howdoyouknowfrozen.DBHelper.QuizHelper;

public class Question {
	private int id;
	private String title;
	private List<Answer> answers;
	
	public Question(int id, String title, List<Answer> answers) {
		this.setId(id);
		this.setTitle(title);
		this.setAnswers(answers);
	}
	
	public static List<Question> Get(QuizHelper db, int noOfQuestions) {
		SQLiteDatabase database = db.getWritableDatabase();
		Cursor questionCursor = db.fetchRandomQuestions(database, noOfQuestions);
		
		List<Question> questions = new ArrayList<Question>();
		Cursor answerCursor;
		
		// Iterate through the question cursor to create all the questions
		// and add them to the questions list
		do {
		answerCursor = db.fetchAnswersByQuestion(database, questionCursor.getInt(0));
		
		List<Answer> answers = new ArrayList<Answer>();
		 // Iterate through the answer cursor to create all the answers for this
		 // question and add them to the answer list
		 do {
			Answer answer = new Answer(
					answerCursor.getInt(0),
					answerCursor.getInt(1),
					answerCursor.getString(2),
					answerCursor.getString(3).equals("true"));
			
			answers.add(answer);
		} while (answerCursor.moveToNext());
		
		questions.add(new Question(		
				questionCursor.getInt(0),
				questionCursor.getString(1),
				answers));
		} while (questionCursor.moveToNext());
		
		return questions;
	}
	
	public static Question GetByID(QuizHelper db, int questionID) {
		SQLiteDatabase database = db.getWritableDatabase();
		Cursor questionCursor = db.fetchQuestionByID(database, questionID);
		
		Cursor answerCursor;
		
		// Iterate through the question cursor to create all the questions
		// and add them to the questions list
		answerCursor = db.fetchAnswersByQuestion(database, questionCursor.getInt(0));
		
		List<Answer> answers = new ArrayList<Answer>();
		 // Iterate through the answer cursor to create all the answers for this
		 // question and add them to the answer list
		 do {
			Answer answer = new Answer(
					answerCursor.getInt(0),
					answerCursor.getInt(1),
					answerCursor.getString(2),
					answerCursor.getInt(3)>0);
			
			answers.add(answer);
		} while (answerCursor.moveToNext());
		
		return new Question(		
				questionCursor.getInt(0),
				questionCursor.getString(1),
				answers);
		
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
