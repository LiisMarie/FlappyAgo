package com.flappyago.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera camera;
    // each state needs a camera to locate a position in the world
    protected Vector3 mouse;
    protected GameStateManager gameStateManager;
    // helps to put game states on top of each other
    // for example pausing game and starting it again

    protected State (GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();

    public abstract void update(float dt);
    // update takes in delta time, which is the difference between
    // one frame rendered and the next one rendered

    public abstract void render(SpriteBatch sb);
    // renders everything to the screen

    public abstract void dispose();
}
