package com.threeam.howdoyouknowfrozen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		 // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            
            // Create a new Fragment to be placed in the activity layout
            QuestionFragment firstFragment = new QuestionFragment();
                        
            // Add the fragment to the 'fragment_container' FrameLayout
            this.getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onRadioButtonClicked(View view) {
		Quiz quiz = Quiz.getInstance(this);
	    // Is the button now checked?
		RadioButton current = (RadioButton) view;
		
	    if(quiz.checkAnswer(current.getText().toString())
	    		&& current.isChecked()) {
	    	Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
	    }
	    else {
	    	Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
	    }
	    
	    Fragment nextDisplay;
	    
	    if (quiz.next()!=null) {
	        // Create a new Fragment to be placed in the activity layout
	        nextDisplay = new QuestionFragment();
	    }
	    else {
	    	// Create a new Fragment to be placed in the activity layout
	        nextDisplay = new ScoreFragment();
	    }
	    // Add the fragment to the 'fragment_container' FrameLayout
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextDisplay).commit();
	}
	
	 /** Called when the user clicks the Send button */
    public void ButtonRestartClicked(View view) {
    	Quiz.killInstance();
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
	
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class QuestionFragment extends Fragment {

		public QuestionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			
			Quiz quiz = Quiz.getInstance(getActivity());
			Question question = quiz.getCurrentQuestion();
		    
		    TextView t = (TextView) rootView.findViewById(R.id.text_question_title);
		    t.setText(question.getTitle());
		    	    
		    // Set up the radio buttons for answers		    
		    RadioButton answerOne = (RadioButton) rootView.findViewById(R.id.radio_answer_one);
		    answerOne.setText(question.getAnswers().get(0).getTitle());
		    
		    RadioButton answerTwo = (RadioButton) rootView.findViewById(R.id.radio_answer_two);
		    answerTwo.setText(question.getAnswers().get(1).getTitle());
		    
		    RadioButton answerThree = (RadioButton) rootView.findViewById(R.id.radio_answer_three);
		    answerThree.setText(question.getAnswers().get(2).getTitle());

			return rootView;
		}
		
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ScoreFragment extends Fragment {

		public ScoreFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_score,
					container, false);
			
			Quiz quiz = Quiz.getInstance(this.getActivity());
			
		    TextView t = (TextView) rootView.findViewById(R.id.text_final_score);
		    
		    String scoreText = "Congratulations!  You scored " 
		    		+ quiz.getScore() + " out of " + quiz.getNoOfQuestions(); 
		    
		    t.setText(scoreText);
		    	    
			return rootView;
		}
		
		
	}

}
