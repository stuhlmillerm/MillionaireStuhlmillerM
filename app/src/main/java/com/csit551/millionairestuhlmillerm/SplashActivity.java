package com.csit551.millionairestuhlmillerm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MillionaireGame game = new MillionaireGame(getApplicationContext());
        game.resetGame();

        // Set up an intent to automatically transition from the splash screen to main menu
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, getResources().getInteger(R.integer.splash_duration_ms));


    }
}