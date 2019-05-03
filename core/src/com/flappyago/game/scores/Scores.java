package com.flappyago.game.scores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Scores {
    private static int maxScore;
    private static Preferences pref;
    public Scores() {
        // preferences
        pref = Gdx.app.getPreferences("SharedPrefs");
        maxScore = pref.getInteger("HighScore");
    }

    public static void updateMax(int score) {
        if (score > maxScore) {
            maxScore = score;
            pref.putInteger("HighScore", score);
            pref.flush();
        }
    }

    public static int getMaxScore() {
        return maxScore;
    }
}
