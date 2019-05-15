package com.flappyago.game.scores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.flappyago.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Scores {
    private static int maxScore;
    // private static List<Player> highScores;
    private static Preferences pref;
    public Scores() {
        // preferences
        pref = Gdx.app.getPreferences("SharedPrefs");
        maxScore = pref.getInteger("HighScore");
        /*highScores = new ArrayList<Player>();
        if (!pref.contains("Player5")) {
            pref.putString("Player1", "");
            pref.putString("Player2", "");
            pref.putString("Player3", "");
            pref.putString("Player4", "");
            pref.putString("Player5", "");
            pref.putInteger("Score1", 0);
            pref.putInteger("Score2", 0);
            pref.putInteger("Score3", 0);
            pref.putInteger("Score4", 0);
            pref.putInteger("Score5", 0);
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
    public static void updateMax(int score) {
        if (score > maxScore) {
            maxScore = score;
            pref.putInteger("HighScore", score);
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
}
