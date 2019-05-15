package com.flappyago.game.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappyago.game.FlappyAgo;
import com.flappyago.game.music.GameMusic;

public class MenuState extends State {
    // background
    private Texture backgroundTexture;

    // play button
    private Texture playTexture;
    private Drawable drawablePlay;
    private ImageButton playButton;

    // ai button
    private Texture aiTexture;
    private Drawable drawableAi;
    private ImageButton aiButton;

    // sound button
    private Texture soundTexture;
    private Drawable drawableSound;
    private ImageButton soundButton;

    // info button
    private Texture infoTexture;
    private Drawable drawableInfo;
    private ImageButton infoButton;

    // credits button
    private Texture creditsTexture;
    private Drawable drawableCredits;
    private ImageButton creditsButton;

    // close button
    private Texture closeTexture;
    private Drawable drawableClose;
    private ImageButton closeCreditsButton;
    private ImageButton closeInfoButton;

    // booleans for info and credits
    private boolean displayInfo = false;
    private boolean displayCredits = false;

    // viewport
    private Viewport viewport;

    // stage
    private Stage stage;

    // text
    private BitmapFont font;
    private Label title;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);

        font = new BitmapFont(Gdx.files.internal("flappybirdy2.fnt"));

        camera.setToOrtho(false, FlappyAgo.WIDTH / 2,
                FlappyAgo.HEIGHT / 2);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        viewport = new StretchViewport(FlappyAgo.WIDTH, FlappyAgo.HEIGHT, camera);
        stage = new Stage(viewport);

        // set background for menu
        backgroundTexture = new Texture("background.png");
        Image bg = new Image(backgroundTexture);
        bg.setPosition(0, 0);  // bg location: left-hand bottom corner
        bg.setSize(FlappyAgo.WIDTH, FlappyAgo.HEIGHT);  // set background image size
        stage.addActor(bg);

        // set the volume button according to master volume
        if (GameMusic.getMasterVolume() != 0) {
            changeSoundButton("ON");
        } else {
            changeSoundButton("OFF");
        }


        createButtons();  // generates necessary buttons

        // game title
        title = new Label("Flappy Ago", new Label.LabelStyle(font, Color.BLACK));
        title.setFontScale(1);
        title.setX(camera.position.x - title.getWidth() / 2);
        title.setY(camera.position.y + 100);
        stage.addActor(title);

        stage.getViewport().apply();
        Gdx.input.setInputProcessor(stage);
    }

    private void createButtons() {
        // set play button
        playTexture = new Texture("play_button.png");
        drawablePlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(drawablePlay);
        playButton.setPosition(camera.position.x - playButton.getWidth() / 2, camera.position.y);
        stage.addActor(playButton);

        // set ai button
        aiTexture = new Texture("ai_button.png");
        drawableAi = new TextureRegionDrawable(new TextureRegion(aiTexture));
        aiButton = new ImageButton(drawableAi);
        aiButton.setPosition(camera.position.x - playButton.getWidth() / 2, camera.position.y - 80);
        stage.addActor(aiButton);

        // set info button
        infoTexture = new Texture("info_button.png");
        drawableInfo = new TextureRegionDrawable(new TextureRegion(infoTexture));
        infoButton = new ImageButton(drawableInfo);
        infoButton.setSize(40, 50);
        infoButton.setPosition(28, FlappyAgo.HEIGHT - infoButton.getHeight() - 32);
        stage.addActor(infoButton);

        // set credits button
        creditsTexture = new Texture("credits_button.png");
        drawableCredits = new TextureRegionDrawable(new TextureRegion(creditsTexture));
        creditsButton = new ImageButton(drawableCredits);
        creditsButton.setSize(70, 70);
        creditsButton.setPosition(FlappyAgo.WIDTH - soundButton.getWidth() - 50, camera.position.y - 350);
        stage.addActor(creditsButton);

        // set close button
        closeTexture = new Texture("close_button.png");
        drawableClose = new TextureRegionDrawable(new TextureRegion(closeTexture));
        // close info button
        closeInfoButton = new ImageButton(drawableClose);
        closeInfoButton.setSize(75, 75);
        closeInfoButton.setPosition(FlappyAgo.WIDTH - soundButton.getWidth() - 70, camera.position.y - 350);
        // close credits button
        closeCreditsButton = new ImageButton(drawableClose);
        closeCreditsButton.setSize(75, 75);
        closeCreditsButton.setPosition(FlappyAgo.WIDTH - soundButton.getWidth() - 70, camera.position.y - 350);
    }

    private void changeSoundButton(String str) {
        if (str.equals("ON")) {
            soundTexture = new Texture("sound_button.png");
        } else {
            soundTexture = new Texture("off_sound_button.png");
        }
        drawableSound = new TextureRegionDrawable(new TextureRegion(soundTexture));
        soundButton = new ImageButton(drawableSound);
        soundButton.setSize(40, 50);
        soundButton.setPosition(FlappyAgo.WIDTH - soundButton.getWidth() - 30,
                FlappyAgo.HEIGHT - soundButton.getHeight() - 30);
        stage.addActor(soundButton);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().viewportWidth = FlappyAgo.WIDTH;
        stage.getCamera().viewportHeight = FlappyAgo.WIDTH * height / width;
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2,
                stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();
    }

    @Override
    public void handleInput() {
        // when user has touched the screen (with mouse or finger)
        //PLAYBUTTON
        if (playButton.isPressed()) {
            gameStateManager.set(new PlayState(gameStateManager, false));
        }

        //AIBUTTON
        if (aiButton.isPressed()) {
            gameStateManager.set(new PlayState(gameStateManager, true));
        }

        // SOUNDBUTTON
        if (soundButton.isPressed() && GameMusic.getMasterVolume() != 0) {
            soundButton.remove();
            changeSoundButton("OFF");
            GameMusic.getMenuMusic().stop();
            GameMusic.setMasterVolume(0);  // sets the volume of the music to 0

        } else if (soundButton.isPressed() && GameMusic.getMasterVolume() == 0) {
            soundButton.remove();
            GameMusic.setMasterVolume(0.5f);  // sets the volume back high
            changeSoundButton("ON");
            GameMusic.getMenuMusic().dispose(); // It looks shit but it works...
            GameMusic.createNewMenuMusic();
            GameMusic.getMenuMusic().play();
        }

        // INFO BUTTON
        if (infoButton.isPressed()) {
            if (!displayInfo) {
                openInfo();
            }
        }
        if (closeInfoButton.isPressed()) {
            if (displayInfo) {
                closeInfo();
            }
        }

        // CREDITS BUTTONS
        if (creditsButton.isPressed()) {
            if (!displayCredits) {
                openCredits();
            }
        }
        if (closeCreditsButton.isPressed()) {
            if (displayCredits) {
                closeCredits();
            }
        }
    }

    private void removeMenu() {
        infoButton.remove();
        creditsButton.remove();
        playButton.remove();
        aiButton.remove();
        soundButton.remove();
    }

    private void addMenu() {
        stage.addActor(infoButton);
        stage.addActor(creditsButton);
        stage.addActor(playButton);
        stage.addActor(aiButton);
        stage.addActor(soundButton);
    }

    private void openInfo() {
        System.out.println("pressed INFO button");
        displayInfo = true;
        stage.addActor(closeInfoButton);

        removeMenu();  // remove menu stuff

        // instructions title
        Label instructionsTitle = new Label("Instructions", new Label.LabelStyle(font, Color.BLACK));
        instructionsTitle.setFontScale(0.6f);
        instructionsTitle.setX(camera.position.x - instructionsTitle.getWidth() / 2 + 90);
        instructionsTitle.setY(camera.position.y);
        stage.addActor(instructionsTitle);

        // instructions text
        Label instructions = new Label("Take good care of Ago!" +
                "\n \nDon't let him fall on the ground and fly against the Pythons." +
                "\n \nTapping on the screen makes Ago jump.", new Label.LabelStyle(font, Color.BLACK));
        instructions.setFontScale(0.4f);
        instructions.setX(camera.position.x - 130);
        instructions.setY(camera.position.y - 340);
        instructions.setWrap(true);
        instructions.setWidth(300);
        stage.addActor(instructions);
    }

    private void closeInfo() {
        System.out.println("pressed CLOSE INFO button");
        displayInfo = false;

        // remove stuff related to info screen
        stage.getActors().pop();  // removes instructions text
        stage.getActors().pop();  // removes instructions title
        closeInfoButton.remove();

        addMenu();  // add menu stuff back
    }

    private void openCredits() {
        System.out.println("pressed CREDITS button");
        displayCredits = true;
        stage.addActor(closeCreditsButton);

        removeMenu();  // remove menu stuff

        // credits title
        Label creditsTitle = new Label("Credits", new Label.LabelStyle(font, Color.BLACK));
        creditsTitle.setFontScale(0.6f);
        creditsTitle.setX(camera.position.x - creditsTitle.getWidth() / 2 + 50);
        creditsTitle.setY(camera.position.y);
        stage.addActor(creditsTitle);

        // credits text
        Label credits = new Label("Made by:" +
                "\n \n   Liisu"
                + "\n \n   Ratu", new Label.LabelStyle(font, Color.BLACK));
        credits.setFontScale(0.4f);
        credits.setX(camera.position.x - 65);
        credits.setY(camera.position.y - 300);
        credits.setWrap(true);
        credits.setWidth(300);
        stage.addActor(credits);
    }

    private void closeCredits() {
        System.out.println("pressed CLOSE CREDITS button");
        displayCredits = false;

        // remove stuff related to credits screen
        stage.getActors().pop();  // removes credits text
        stage.getActors().pop();  // removes credits title
        closeCreditsButton.remove();


        addMenu();  // add menu stuff back
    }

    @Override
    public void update(float dt) {
        handleInput();  // checks whether there is any input all the time
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);

        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.getBatch().begin();
        stage.getBatch().end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (!GameMusic.getMenuMusic().isPlaying()) {
            GameMusic.getMenuMusic().play();
            System.out.println("Menu music started to play!");
        }
    }

    @Override
    public void dispose() {
        GameMusic.getMenuMusic().stop();
        System.out.println("Menu music stopped...");
        stage.dispose();
        System.out.println("Menu state disposed");
    }
}
