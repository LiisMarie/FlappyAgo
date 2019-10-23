package com.flappyago.game.scores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
<<<<<<< HEAD

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
=======
import com.flappyago.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Scores {
    private static int maxScore;
    private static int prevMax;
    private static int aiScore;
    // private static List<Player> highScores;
    private static Preferences pref;
    public Scores() {
        // preferences
        pref = Gdx.app.getPreferences("SharedPrefs");
        maxScore = pref.getInteger("HighScore");
        prevMax = maxScore;
        aiScore = pref.getInteger("AiScore");
        /*highScores = new ArrayList<Player>();
        if (!pref.contains("Player5")) {
            pref.putString("Player1", "");
            pref.putString("Player2", "");
            pref.putString("Player3", "");
            pref.putString("Player4", "");
            pref.putString("Player5", "");
>>>>>>> 3e8074e254639e55c8115f59739f6f360b3b8dd4
            pref.putInteger("Score1", 0);
            pref.putInteger("Score2", 0);
            pref.putInteger("Score3", 0);
            pref.putInteger("Score4", 0);
            pref.putInteger("Score5", 0);
<<<<<<< HEAD
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
=======
        }
        Player player1 = new Player(pref.getString("Player1"), pref.getInteger("Score1"));
        Player player2 = new Player(pref.getString("Player2"), pref.getInteger("Score2"));
        Player player3 = new Player(pref.getString("Player3"), pref.getInteger("Score3"));
        Player player4 = new Player(pref.getString("Player4"), pref.getInteger("Score4"));
        Player player5 = new Player(pref.getString("Player5"), pref.getInteger("Score5"));
        highScores.add(player1);
        highScores.add(player2);
        highScores.add(player3);
        highScores.add(player4);
        highScores.add(player5);
        */
    }

    // Update maximum score.
    public static void updateMax(int score, boolean aiOn) {
        if (!aiOn && score > maxScore) {
            prevMax = maxScore;
            maxScore = score;
            pref.putInteger("HighScore", maxScore);
            pref.flush();
        }
        if (aiOn && score > aiScore) {
            aiScore = score;
            pref.putInteger("AiScore", aiScore);
            pref.flush();
        }
    }
    /*
     *public static void updateScores(int score) {
     *  if (score > highScores.get(4).getScore()) {
     *      highScores.remove(4);
     *      String newName = "SIIN KÜSIB UUT NIME!";
     *      Player newPlayer = new Player(newName, score);
     *      highScores.add(newPlayer);
     *      // SIIN ON SORTEERIMINE!!!
     *  }
     *}
     */
    // Return max score.
    public static int getMaxScore() {
        return maxScore;
    }
    public static int getPrevMax() {
        return prevMax;
    }
    public static int getAiScore() {
        return aiScore;
    }
>>>>>>> 3e8074e254639e55c8115f59739f6f360b3b8dd4
}
