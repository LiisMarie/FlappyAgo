package com.flappyago.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappyago.game.FlappyAgo;
import com.flappyago.game.sprites.Ago;
import com.flappyago.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    // tubes
    private int score;
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
    private ImageButton playButton;
    private boolean newGame = false;
    private ImageButton menuButton;
    private boolean backToMenu = false;

    private Sound die;

    private boolean gameOn;
    public boolean gameOver;

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

        // dying sound
        die = Gdx.audio.newSound(Gdx.files.internal("dying.ogg"));

        font = new BitmapFont(Gdx.files.internal("flappybirdy.fnt"));

        // playbutton on gameover screen
        Texture playTexture = new Texture("play_button.png");
        TextureRegionDrawable drawablePlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(drawablePlay);
        playButton.setPosition(camera.position.x - playButton.getWidth() / 2 + 225, camera.position.y + 40);
        playButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)   {
                System.out.println("Chose to play again");
                newGame = true;
                return true;
            }
        });

        // menubutton on gameover screen
        Texture menuTexture = new Texture("menu_button.png");
        TextureRegionDrawable drawableMenu = new TextureRegionDrawable(new TextureRegion(menuTexture));
        menuButton = new ImageButton(drawableMenu);
        menuButton.setPosition(camera.position.x - playButton.getWidth() / 2, camera.position.y + 40);
        menuButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)   {
                System.out.println("Goin back to menu");
                backToMenu = true;
                return true;
            }
        });
    }

    @Override
    protected void handleInput() {
        if ((Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && !gameOver) {
            gameOn = true;
            ago.jump();
        } else if (newGame) {
            gameStateManager.push(new PlayState(gameStateManager));
        } else if (backToMenu) {
            gameStateManager.push(new MenuState(gameStateManager));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (gameOn) {
            updateGround();
            ago.update(dt);

            camera.position.x = ago.getPosition().x + 80;  // camera follows Ago

            float soundVolume = FlappyAgo.masterVolume;
            if (FlappyAgo.masterVolume != 0) {
                soundVolume = 1f;
            }

            for (Tube tube : tubes) {
                if (camera.position.x - (camera.viewportWidth / 2) > tube.getPositionTopTube().x
                        + tube.getTopTube().getWidth()) {
                    tube.reposition(tube.getPositionTopTube().x + ((Tube.TUBE_WIDTH
                            + SPACE_BETWEEN_TUBES) * TUBE_COUNT));
                }

                if (tube.addPoint(ago.getBounds())) {
                    score++;
                    if (score > FlappyAgo.maxScore) FlappyAgo.maxScore = score;
                    System.out.println("SCORE: " + Integer.toString(score));
                }

                if (tube.collides(ago.getBounds())) {  // check collision with tubes
                    die.play(soundVolume);
                    gameOver = true;
                    gameOn = false;
                    // gameStateManager.push(new GameOverState(gameStateManager));
                }
            }

            if (ago.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) { // check collision with ground
                die.play(soundVolume);
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
        if (!gameOver) {
            font.draw(sb, Integer.toString(score), camera.position.x, camera.position.y + 185);
        }
        sb.draw(ground, groundPosition1.x, groundPosition1.y);
        sb.draw(ground, groundPosition2.x, groundPosition2.y);

        if (gameOver) {
            // gameOver text
            sb.draw(bgGameOver, camera.position.x - 105, camera.position.y - 30);
            font.getData().setScale(0.5f, 0.5f);
            font.setColor(Color.BLACK);
            font.draw(sb, "GameOver", camera.position.x - 90, camera.position.y + 130);

            font.getData().setScale(0.3f, 0.3f);
            font.draw(sb, "Score " + Integer.toString(score), camera.position.x - 90, camera.position.y + 50);
            font.draw(sb, "Best " + Integer.toString(FlappyAgo.maxScore), camera.position.x - 90, camera.position.y + 10);
            FlappyAgo.playMusic.stop();
            ago.newStart = true;


        sb.end();


            Stage stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);
            stage.addActor(playButton);
            stage.addActor(menuButton);
            stage.draw();
        }

        if (!gameOver) {
            sb.end();
        }
    }

    boolean clicked = false;

    @Override
    public void dispose() {
        background.dispose();
        ago.dispose();
        ground.dispose();
        FlappyAgo.playMusic.stop();

        for (Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play state disposed");
    }

    public int getScore() {
        return this.score;
    }
}
