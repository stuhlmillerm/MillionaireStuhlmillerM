package com.csit551.millionairestuhlmillerm;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.CharArrayReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MillionaireGame {
    // Class for keeping game state
    private SharedPreferences prefs;
    private int score;

    private Context context;
    private static String scoreKey = "saved_score";
    private static String questionsKey = "game_questions";
    private int newGameScore = 0;
    private int winCount = 15;
    private String[] scoreValues = {
            "0", "100", "200", "300", "500", "1,000",
            "2,000", "4,000", "8,000", "16,000", "32,000",
            "64,000", "125,000", "250,000", "500,000", "1 MILLION" };
    private String questions = "";
    private String correctAnswer = "";
    private Map<String, String> answers = new HashMap<>();

    public MillionaireGame(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(context.getResources().getString(R.string.prefs_key), Context.MODE_PRIVATE);
        this.score = getSavedScore();
    }

    public void resetGame() {
        // Reset our saved score
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(scoreKey, newGameScore);
        score = newGameScore;

        // Get our list of possible question keys and shuffle to get our game questions
        String possibleQuestions = context.getResources().getString(R.string.game_questions);
        List<String> letters = Arrays.asList(possibleQuestions.split(""));
        Collections.shuffle(letters);
        StringBuilder builder = new StringBuilder();
        for (String letter : letters) {
            builder.append(letter);
        }
        questions = builder.toString();

        // Store the list so we keep track throughout the game
        edit.putString(questionsKey, questions);
        edit.apply();

    }

    public int getSavedScore() {
        return prefs.getInt(scoreKey, newGameScore);
    }

    public String getCurrentValue() {
        return "$" + this.scoreValues[this.score];
    }

    public String getNextQuestionText() {
        if (questions.isEmpty()) {
            questions = prefs.getString(questionsKey, "_");
        }
        char nextQuestion = questions.charAt(score);
        String resourceKey = "q" + nextQuestion;
        int resourceId = context.getResources().getIdentifier(resourceKey, "array", context.getPackageName());
        String[] questionInfo = context.getResources().getStringArray(resourceId);

        // Set up our random answer array, recording the correct one
        List<String> letters = Arrays.asList("A", "B", "C", "D");
        Collections.shuffle(letters);

        this.correctAnswer = letters.get(0);
        answers.put(letters.get(0), questionInfo[1]);
        answers.put(letters.get(1), questionInfo[2]);
        answers.put(letters.get(2), questionInfo[3]);
        answers.put(letters.get(3), questionInfo[4]);

        // Return the first element in our string array (the question text)
        return questionInfo[0];
    }

    public String getAnswer(String item) {
        return item + ". " + answers.get(item);
    }

    public boolean checkAnswer(String choice) {
        // See if the choice matches the loaded question

        if (choice.equals(correctAnswer)) {
            // If correct, advance the score and save state.
            score++;
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt(scoreKey, score);
            edit.apply();

            return true;
        } else {
            // Otherwise, just return false.
            return false;
        }
    }

    public boolean isWinner() {
        return this.score >= this.winCount;
    }

}
