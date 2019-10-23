package com.flappyago.game.player;
/*
 * That class was meant to be used for high score table but that is not yet implemented.
 * That's kinda broken as well...
 */
public class Player {
    private static String name;
    private static int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public static int compareTo(Player o) {
        return score - o.score;
    }
    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
}
