package com.flappyago.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.flappyago.game.states.PlayState;

import java.util.Random;

public class Tube {
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int TUBE_WIDTH = 52;
    public boolean hasPointScored = false;

    private Texture topTube, bottomTube;
    private Vector2 positionTopTube, positionBottomTube;
    private Rectangle boundsTop, boundsBottom;

    private Random rand;

    public Tube(float x) {
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");

        rand = new Random();
        positionTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionBottomTube = new Vector2(x, positionTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(positionTopTube.x, positionTopTube.y,
                topTube.getWidth(), topTube.getHeight());
        boundsBottom = new Rectangle(positionBottomTube.x, positionBottomTube.y,
                bottomTube.getWidth(), bottomTube.getHeight());
    }

    public void reposition(float x) {
        positionTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionBottomTube.set(x, positionTopTube.y - TUBE_GAP - bottomTube.getHeight());
        this.hasPointScored = false;

        boundsTop.setPosition(positionTopTube.x, positionTopTube.y);
        boundsBottom.setPosition(positionBottomTube.x, positionBottomTube.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBottom);
    }

    public boolean addPoint(Rectangle player) {
        if ((player.x - positionTopTube.x) > 0 && !collides(player) && !hasPointScored) {
            hasPointScored = true;
            return true;
        }
        return false;
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPositionTopTube() {
        return positionTopTube;
    }

    public Vector2 getPositionBottomTube() {
        return positionBottomTube;
    }
}
