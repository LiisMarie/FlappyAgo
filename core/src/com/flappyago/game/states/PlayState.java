package com.flappyago.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.flappyago.game.FlappyAgo;
import com.flappyago.game.music.GameMusic;
import com.flappyago.game.scores.Scores;
import com.flappyago.game.sprites.Ago;
import com.flappyago.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {


    // Ago
    private Ago ago;
    private static final int AGO_BEHIND_CENTER = 80;
    private static final int AGO_STARTING_POSITION_X = FlappyAgo.WIDTH / 4 - AGO_BEHIND_CENTER;
    private static final int AGO_STARTING_POSITION_Y = FlappyAgo.HEIGHT / 4;

    // background
    private Texture background;

    // buttons
    private static final int BUTTON_WIDTH = 70, BUTTON_HEIGHT = 30;

    // ground
    private static final int GROUND_Y_OFFSET = -50;
    private Texture ground;
    private Vector2 groundPosition1, groundPosition2;

    // game booleans
    private boolean gameOn;
    public boolean gameOver;

    // game over background
    private Texture bgGameOver;
    private ImageButton playButton;
    private boolean newGame = false;
    private ImageButton menuButton;
    private boolean backToMenu = false;

    // score
    private int score;
    private Scores scores;

    // text
    GlyphLayout layout;
    BitmapFont font;
    private static final float SCORE_FONT = 0.65f;
    private static final float TITLE_FONT = 0.5f;
    private static final float BOX_FONT = 0.3f;

    // tubes
    private static final int SPACE_BETWEEN_TUBES = 125;  // space between tubes, not including tubes
    private static final int TUBE_COUNT = 4;
    private ArrayList<Tube> tubes;
    private int nextTube;
    public static boolean aiOn;



    public PlayState(GameStateManager gameStateManager, boolean ai) {
        super(gameStateManager);

        gameOn = false;
        gameOver = false;
        aiOn = ai;

        scores = new Scores();

        ago = new Ago(AGO_STARTING_POSITION_X, AGO_STARTING_POSITION_Y);

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
        nextTube = 0;

        // font
        font = new BitmapFont(Gdx.files.internal("flappybirdy2.fnt"));

        // creates new game and back to menu buttons for game over screen
        createGameOverButtons();

        layout = new GlyphLayout();
    }

    private void createGameOverButtons() {
        // playbutton on gameover screen
        Texture playTexture = new Texture("play_button.png");
        if (aiOn) {
            playTexture.dispose();
            playTexture = new Texture("ai_button.png");
        }
        TextureRegionDrawable drawablePlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(drawablePlay);
        playButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        playButton.setPosition(camera.position.x - playButton.getWidth() + 80, camera.position.y - 70);
        playButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)   {
                if (gameOver && !gameOn) {
                    System.out.println("Chose to play again");
                    newGame = true;
                    return true;
                }
                return false;
            }
        });

        // menubutton on gameover screen
        Texture menuTexture = new Texture("menu_button.png");
        TextureRegionDrawable drawableMenu = new TextureRegionDrawable(new TextureRegion(menuTexture));
        menuButton = new ImageButton(drawableMenu);
        menuButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        menuButton.setPosition(camera.position.x - playButton.getWidth() - 15, camera.position.y - 70);
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
            // if game hasn't started yet, it will start
            // if game has started already then ago jumps
            gameOn = true;
            ago.jump();
        } else if (newGame) {  // starts a new game
            gameStateManager.set(new PlayState(gameStateManager, aiOn));
        } else if (backToMenu) {  // goes back to the menu
            gameStateManager.set(new MenuState(gameStateManager));
        }
    }

    @Override
    public void update(float dt) {
        if (aiOn && gameOn && ago.getPosition().y < tubes.get(nextTube).getPositionTopTube().y - SPACE_BETWEEN_TUBES + 35) {
            ago.jump();
            System.out.println("JUMP!!!");
        }
        handleInput();
        if (gameOn) {
            updateGround();
            ago.update(dt);

            camera.position.x = ago.getPosition().x + AGO_BEHIND_CENTER;  // camera follows Ago

            for (Tube tube : tubes) {
                if (camera.position.x - (camera.viewportWidth / 2) > tube.getPositionTopTube().x
                        + tube.getTopTube().getWidth()) {
                    tube.reposition(tube.getPositionTopTube().x + ((Tube.TUBE_WIDTH
                            + SPACE_BETWEEN_TUBES) * TUBE_COUNT));
                }

                if (tube.addPoint(ago.getBounds())) {  // add score
                    score++;
                    if (score > FlappyAgo.maxScore) FlappyAgo.maxScore = score;
                    System.out.println("SCORE: " + score);
                }

                if ((ago.getPosition().x - tubes.get(nextTube).getPositionTopTube().x) > 60) {
                    nextTube += 1;
                    nextTube %= 4;
                }


                    if (tube.collides(ago.getBounds())) {  // check collision with tubes
                    GameMusic.playDying();
                    gameOver = true;
                    gameOn = false;
                    System.out.println("Collided with a tube!");
                }
            }

            if (ago.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {  // check collision with ground
                GameMusic.playDying();
                gameOver = true;
                gameOn = false;
                ago.getPosition().y = GROUND_Y_OFFSET + ground.getHeight();
                System.out.println("Collided with ground!");
            }

            if (ago.getPosition().y >= FlappyAgo.HEIGHT / 2 - ago.getTexture().getRegionHeight()) {
                ago.getPosition().y = FlappyAgo.HEIGHT / 2 - ago.getTexture().getRegionHeight();
                ago.getVelocity().y = 0;
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

        // draw tubes
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPositionTopTube().x, tube.getPositionTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPositionBottomTube().x,
                    tube.getPositionBottomTube().y);
        }

        // display text when game hasn't started yet
        if (!gameOn && !gameOver) {
            font.getData().setScale(TITLE_FONT);
            font.setColor(Color.BLACK);
            font.draw(sb, "Tap to begin!", camera.position.x - 115, camera.position.y + 130);
        }

        // display score during game
        if (!gameOver && gameOn) {
            String scoreString = Integer.toString(score);
            layout.setText(font, scoreString);
            float width = layout.width;
            font.setUseIntegerPositions(false);
            font.getData().setScale(SCORE_FONT);
            font.draw(sb, scoreString, camera.position.x - width / 2, camera.position.y + FlappyAgo.HEIGHT / 4);
        }

        // draw ground
        sb.draw(ground, groundPosition1.x, groundPosition1.y);
        sb.draw(ground, groundPosition2.x, groundPosition2.y);

        // game over screen
        if (gameOver) {
            displayGameOverScreen(sb);
        }

        if (!gameOver) {
            sb.end();
        }
    }

    private void displayGameOverScreen(SpriteBatch sb) {
        // gameOver text
        sb.draw(bgGameOver, camera.position.x - 105, camera.position.y - 30);
        font.getData().setScale(TITLE_FONT);
        font.setColor(Color.BLACK);
        font.draw(sb, "GameOver", camera.position.x - 90, camera.position.y + 130);

        // display current score and highscore
        font.getData().setScale(BOX_FONT);
        font.draw(sb, "Score " + score, camera.position.x - 90, camera.position.y + 50);
        font.draw(sb, "Best " + Scores.getMaxScore(), camera.position.x - 90, camera.position.y + 10);
        ago.newStart = true;

        // write over the score if new one is greater
        if (!aiOn) {
            Scores.updateMax(score);
        }

        sb.end();

        // add new game and back to menu buttons
        Stage stage = new Stage(new StretchViewport(camera.viewportWidth, camera.viewportHeight));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(playButton);
        stage.addActor(menuButton);
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        bgGameOver.dispose();
        ago.dispose();
        ground.dispose();
        GameMusic.getPlayMusic().dispose();
        System.out.println("Music stopped...");

        for (Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play state disposed");
    }
}
