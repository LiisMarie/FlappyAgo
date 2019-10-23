package com.flappyago.game.scores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Scores {
    static HashMap<Integer, String> highScores;
    static List<Integer> scores = new ArrayList<Integer>();
    private static int minScore;
    private static int maxScore;
    private static String maxName;
    private static Preferences pref;
    public Scores() {
        pref = Gdx.app.getPreferences("SharedPrefs");
        if (!pref.contains("Name5")) {
            pref.putString("Name1", "");
            pref.putString("Name2", "");
            pref.putString("Name3", "");
            pref.putString("Name4", "");
            pref.putString("Name5", "");
            pref.putInteger("Score1", 0);
            pref.putInteger("Score2", 0);
            pref.putInteger("Score3", 0);
            pref.putInteger("Score4", 0);
            pref.putInteger("Score5", 0);
            pref.flush();
        }
        highScores = new HashMap<Integer, String>();
        highScores.put(pref.getInteger("Score1"), pref.getString("Name1"));
        highScores.put(pref.getInteger("Score2"), pref.getString("Name2"));
        highScores.put(pref.getInteger("Score3"), pref.getString("Name3"));
        highScores.put(pref.getInteger("Score4"), pref.getString("Name4"));
        highScores.put(pref.getInteger("Score5"), pref.getString("Name5"));
        scores.clear();
        scores.add(pref.getInteger("Score1"));
        scores.add(pref.getInteger("Score2"));
        scores.add(pref.getInteger("Score3"));
        scores.add(pref.getInteger("Score4"));
        scores.add(pref.getInteger("Score5"));
        System.out.println("Scores: " + scores);
        Collections.sort(scores);
        for (int i = 0; i < 1; i++) updateScores(0);
    }

    public static void updateScores(int score) {
        if (score > minScore) {
            highScores.remove(scores.get(0));
            scores.remove(0);
            System.out.println(scores);
            scores.add(score);
            highScores.put(score, "KunagiTulebSiiaNimi");
            Collections.sort(scores);

            pref.putInteger("Score1", scores.get(4));
            pref.putInteger("Score2", scores.get(3));
            pref.putInteger("Score3", scores.get(2));
            pref.putInteger("Score4", scores.get(1));
            pref.putInteger("Score5", scores.get(0));
            pref.putString("Name1", highScores.get(scores.get(4)));
            pref.putString("Name2", highScores.get(scores.get(3)));
            pref.putString("Name3", highScores.get(scores.get(2)));
            pref.putString("Name4", highScores.get(scores.get(1)));
            pref.putString("Name5", highScores.get(scores.get(0)));
            pref.flush();
        }
        minScore = scores.get(0);
        maxScore = scores.get(4);
        maxName = highScores.get(maxScore);
        System.out.println(scores);
    }
    public static int getMaxScore() {
        return maxScore;
    }
}
