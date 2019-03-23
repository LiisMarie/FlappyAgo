package com.flappyago.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappyago.game.FlappyAgo;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Pushes new state to the stack.
     * @param state to be pushed on top of the stack.
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Pops the state which is on top of the stack.
     */
    public void pop() {
        states.pop().dispose();  // dispose the state we don't need anymore
    }

    /**
     * Replaces the state on top of the stack.
     * @param state which is being added on the top of the stack.
     */
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Looks at the top state of the stack.
     * @param dt changing time between two renders.
     */
    public void update(float dt) {
        states.peek().update(dt);
    }

    /**
     * Render everything to the screen.
     * @param sb container containing everything which renders to the screen.
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
