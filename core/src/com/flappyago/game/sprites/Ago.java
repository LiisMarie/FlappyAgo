package com.flappyago.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.flappyago.game.music.GameMusic;
import com.flappyago.game.states.PlayState;

public class Ago {
    public boolean newStart;
    private static final int GRAVITY = -15;
    public float movement;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture texture;
    private Animation agoAnimation;

    public Ago(int x, int y) {
        GameMusic.getPlayMusic().dispose();
        newStart = true;
        position = new Vector3(x, y, 0);  // Ago's starting point
        velocity = new Vector3(0, 0, 0);  // before starting speed is 0
        movement = 100;
        texture = new Texture("ago_animation.png");
        agoAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
    }

    public void update(float dt) {
        if (newStart) {
            newStart = false;
            GameMusic.start();
        }
        agoAnimation.update(dt);
        if (!PlayState.aiOn && movement < 200) {
            movement += 0.03;
        }

        if (0 < position.y) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(dt);
        position.add(movement * dt, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = 250;
        GameMusic.playFlying();
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public TextureRegion getTexture() {
        return agoAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
