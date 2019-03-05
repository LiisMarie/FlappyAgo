package com.flappyago.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ago {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture texture;
    private Animation agoAnimation;

    private Sound fly;
    // private Sound dying;  /// BRLIIIING here

    public Ago(int x, int y) {
        position = new Vector3(x, y, 0);  // Ago's starting point
        velocity = new Vector3(0, 0, 0);  // before starting speed is 0

        texture = new Texture("agoanimation.png");
        agoAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

        fly = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt) {
        agoAnimation.update(dt);

        if (0 < position.y) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = 250;
        fly.play(0.5f);
    }

    public void dispose() {
        texture.dispose();
        fly.dispose();
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return agoAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
