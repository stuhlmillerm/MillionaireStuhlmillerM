package com.csit551.millionairestuhlmillerm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MillionaireGame game;
    int answerChosen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new MillionaireGame(getApplicationContext());
        // Check to see if they've won the game
        if (game.isWinner()) {
            // Go to the winner screen.
            Intent intent=new Intent(MainActivity.this,WinActivity.class);
            startActivity(intent);
            finish();
        }

        // Otherwise, prepare the screen first with the current score
        TextView scoreView = findViewById(R.id.curScore);
        scoreView.setText(game.getCurrentValue());

        // Then with the info about the next question
        TextView questionText = findViewById(R.id.questionText);
        questionText.setText(game.getNextQuestionText());

        RadioButton answerA = findViewById(R.id.answerA);
        RadioButton answerB = findViewById(R.id.answerB);
        RadioButton answerC = findViewById(R.id.answerC);
        RadioButton answerD = findViewById(R.id.answerD);

        answerA.setText(game.getAnswer("A"));
        answerB.setText(game.getAnswer("B"));
        answerC.setText(game.getAnswer("C"));
        answerD.setText(game.getAnswer("D"));

    }

    public void answerSelected (View view) {
        this.answerChosen = view.getId();

        RadioButton answerA = findViewById(R.id.answerA);
        RadioButton answerB = findViewById(R.id.answerB);
        RadioButton answerC = findViewById(R.id.answerC);
        RadioButton answerD = findViewById(R.id.answerD);

        if (answerChosen != answerA.getId())
            answerA.setChecked(false);
        if (answerChosen != answerB.getId())
            answerB.setChecked(false);
        if (answerChosen != answerC.getId())
            answerC.setChecked(false);
        if (answerChosen != answerD.getId())
            answerD.setChecked(false);

    }

    public void answerButton(View view) {
        // First make sure they answered...
        if (answerChosen == 0) {
            // Display a message prompting them to answer
            showMessage(getResources().getString(R.string.no_answer));
            return;
        }

        view.setEnabled(false);

        RadioButton selected = findViewById(answerChosen);
        String answerChoice = selected.toString();

        final boolean isCorrect = game.checkAnswer(String.valueOf(answerChoice.charAt(answerChoice.length()-2)));

        showMessage(getResources().getString(isCorrect ? R.string.answer_right : R.string.answer_wrong));

        // Give the message time and go to the next page - MainActivity or LoseActivity.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, isCorrect ? MainActivity.class : LoseActivity.class);
                startActivity(intent);
                finish();
            }
        }, getResources().getInteger(R.integer.toast_duration_ms));


    }

    public void showMessage(String messageText) {
        Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_SHORT).show();
    }

}