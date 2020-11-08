package com.csit551.millionairestuhlmillerm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoseActivity extends AppCompatActivity {
    private MillionaireGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        // Load the final winnings and display
        game = new MillionaireGame(getApplicationContext());
        TextView scoreView = findViewById(R.id.loseScore);
        scoreView.setText(game.getCurrentValue());

    }

    public void clickButton(View view) {
        // Reset the game state
        game.resetGame();

        // Go back to the new first question.
        Intent intent=new Intent(LoseActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}