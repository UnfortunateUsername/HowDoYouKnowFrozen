package com.threeam.howdoyouknowfrozen;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.threeam.howdoyouknowfrozen.DBHelper.QuizHelper;


public class Quiz {
	private List<Question> questions;
	private Question currentQuestion;
	private int questionPointer;
	private int noOfQuestions;
	private int score;
	private QuizHelper db;
	
	private static Quiz instance;
	
	private Quiz(Context context)
	{
		db = QuizHelper.getInstance(context);
		this.questions = Question.Get(this.db, 2);  // hard coded number of questions
		this.questionPointer = -1;
		this.noOfQuestions = this.questions.size();
		this.score = 0;
		this.next();
	}
	
	public static Quiz getInstance(Context context) {
		if (instance==null) {
			instance = new Quiz(context);
		}
		return instance;
	}
	
	public static void killInstance() {
		instance = null;
	}
	
	public Question next() {
		if(questionPointer < (this.noOfQuestions-1)) {
			this.questionPointer++;
			this.currentQuestion = this.questions.get(this.questionPointer);
			Log.d("next(): current question: ", this.currentQuestion.getTitle());
			this.getCorrectAnswer();
			return currentQuestion;
		}
		else {
			return null;
		}
	}
	
	public boolean checkAnswer(String guess) {
		boolean isCorrect = guess == this.getCorrectAnswer().getTitle();
		if(isCorrect) {
			this.score++;
		}
		return isCorrect;
	}

	private Answer getCorrectAnswer() {
		for (Answer answer : this.currentQuestion.getAnswers()) { 
			Log.d("getcorrectanswer - answer: ", answer.getTitle());
			  if (answer.isRight()) {  
			    return answer;  
			  }  
		}  
		return null;
	}
	
	public List<Question> getQuestions() {
		return this.questions;
	}
	
	public Question getCurrentQuestion() {
		return this.currentQuestion;
	}
	
//	public Answer getCurrentCorrectAnswer() {
//		return this.currentCorrectAnswer;
//	}
	
	public int getNoOfQuestions() {
		return this.noOfQuestions;
	}
	
	public int getScore() {
		return this.score;
	}

}

