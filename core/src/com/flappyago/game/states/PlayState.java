package com.flappyago.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappyago.game.FlappyAgo;
import com.flappyago.game.sprites.Ago;
import com.flappyago.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    // tubes
    private static final int SPACE_BETWEEN_TUBES = 125;  // space between tubes, not including tubes
    private static final int TUBE_COUNT = 4;
    private ArrayList<Tube> tubes;

    // Ago
    private Ago ago;

    // background
    private Texture background;

    // ground
    private static final int GROUND_Y_OFFSET = -50;
    private Texture ground;
    private Vector2 groundPosition1, groundPosition2;

    // game over background
    private Texture bgGameOver;

    private Sound die;


    private boolean gameOn;
    private boolean gameOver;

    BitmapFont font;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);

        gameOn = false;
        gameOver = false;

        ago = new Ago(50, 100);

        camera.setToOrtho(false, FlappyAgo.WIDTH / 2,
                FlappyAgo.HEIGHT / 2);

        background = new Texture("background.png");

        bgGameOver = new Texture("game_over_background.png");

        // ground
        ground = new Texture("ground.png");
        groundPosition1 = new Vector2(camera.position.x - camera.viewportWidth / 2,
                GROUND_Y_OFFSET);
        groundPosition2 = new Vector2((camera.position.x - camera.viewportWidth / 2)
                + ground.getWidth(), GROUND_Y_OFFSET);

        // tubes
        tubes = new ArrayList<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (SPACE_BETWEEN_TUBES + Tube.TUBE_WIDTH)));
        }

        die = Gdx.audio.newSound(Gdx.files.internal("dying.ogg"));

        font = new BitmapFont(Gdx.files.internal("flappybirdy.fnt"));
    }

    @Override
    protected void handleInput() {
        if ((Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && !gameOver) {
            gameOn = true;
            ago.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (gameOn) {
            updateGround();
            ago.update(dt);

            camera.position.x = ago.getPosition().x + 80;  // camera follows Ago

            for (Tube tube : tubes) {
                if (camera.position.x - (camera.viewportWidth / 2) > tube.getPositionTopTube().x
                        + tube.getTopTube().getWidth()) {
                    tube.reposition(tube.getPositionTopTube().x + ((Tube.TUBE_WIDTH
                            + SPACE_BETWEEN_TUBES) * TUBE_COUNT));
                }

                if (tube.collides(ago.getBounds())) {  // check collision with tubes
                    die.play(FlappyAgo.masterVolume);
                    gameOver = true;
                    gameOn = false;
                    // gameStateManager.push(new GameOverState(gameStateManager));
                }
            }

            if (ago.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) { // check collision with ground
                die.play(FlappyAgo.masterVolume);
                gameOver = true;
                gameOn = false;
                // gameStateManager.push(new GameOverState(gameStateManager));
            }

            camera.update();
        }
    }

    private void updateGround() {
        if ((camera.position.x - (camera.viewportWidth / 2)) >
                (groundPosition1.x + ground.getWidth())) {
            groundPosition1.add(ground.getWidth() * 2, 0);
        }
        if ((camera.position.x - (camera.viewportWidth / 2)) >
                (groundPosition2.x + ground.getWidth())) {
            groundPosition2.add(ground.getWidth() * 2, 0);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        // tells where in the game word we are,
        // so that only things, which camera is able to see, will be drawn
        sb.begin();

        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);

        sb.draw(ago.getTexture(), ago.getPosition().x, ago.getPosition().y);

        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPositionTopTube().x, tube.getPositionTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPositionBottomTube().x,
                    tube.getPositionBottomTube().y);
        }

        sb.draw(ground, groundPosition1.x, groundPosition1.y);
        sb.draw(ground, groundPosition2.x, groundPosition2.y);

        // TODO draw sound button nd make it work
        // sb.draw(soundTexture, camera.position.x - (camera.viewportWidth / 2) - 100, camera.position.y);

        if (gameOver) {
            // gameOver text
            sb.draw(bgGameOver, camera.position.x - 100, camera.position.y - 40);
            font.getData().setScale(0.5f, 0.5f);
            font.setColor(Color.BLACK);
            font.draw(sb, "GameOver", camera.position.x - 90, camera.position.y + 130);
            font.getData().setScale(0.3f, 0.3f);
            font.draw(sb, "Score ", camera.position.x - 90, camera.position.y + 50);
            font.draw(sb, "Best ", camera.position.x - 90, camera.position.y + 10);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        ago.dispose();
        ground.dispose();

        for (Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play state disposed");
    }
}
