package com.flappyago.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappyago.game.FlappyAgo;

public class MenuState extends State {
    // background
    private Texture backgroundTexture;

    // play button
    private Texture playTexture;
    private Drawable drawablePlay;
    private ImageButton playButton;

    // sound button
    private Texture soundTexture;
    private Drawable drawableSound;
    private ImageButton soundButton;

    // viewport
    private Viewport viewport;

    // stage
    private Stage stage;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);

        camera.setToOrtho(false, FlappyAgo.WIDTH / 2,
                FlappyAgo.HEIGHT / 2);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        viewport = new StretchViewport(FlappyAgo.WIDTH, FlappyAgo.HEIGHT, camera);
        stage = new Stage(viewport);


        // set background for menu
        backgroundTexture = new Texture("menu_background.png");
        Image bg = new Image(backgroundTexture);
        bg.setPosition(0, 0);  // bg location: left-hand bottom corner
        bg.setSize(FlappyAgo.WIDTH, FlappyAgo.HEIGHT);  // set background image size
        stage.addActor(bg);

        if (FlappyAgo.masterVolume != 0) {
            changeSoundButton("ON");
        } else {
            changeSoundButton("OFF");
        }

        // set play button
        playTexture = new Texture("play_button.png");
        drawablePlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(drawablePlay);
        playButton.setPosition(camera.position.x - playButton.getWidth() / 2, camera.position.y);
        stage.addActor(playButton);


        stage.getViewport().apply();
        Gdx.input.setInputProcessor(stage);

        playButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return Gdx.input.justTouched();
            }
        });

        soundButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return Gdx.input.justTouched();
            }
        });

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
        stage.getCamera().position.set(stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, 0);
        stage.getCamera().update();
    }

    @Override
    public void handleInput() {
        // when user has touched the screen (with mouse or button)
        if (playButton.isPressed()) {  // && Gdx.input.justTouched()
            gameStateManager.set(new PlayState(gameStateManager));

        } else if (soundButton.isPressed() &&  // && Gdx.input.justTouched()
                ((FlappyAgo.menuMusic.getVolume() != 0) &&
                        FlappyAgo.playMusic.getVolume() != 0)) {
            soundButton.remove();
            changeSoundButton("OFF");
            FlappyAgo.setMasterVolume(0);
            // sets the volume of the music to 0

        } else if (soundButton.isPressed() &&  // // && Gdx.input.justTouched()
                ((FlappyAgo.menuMusic.getVolume() == 0) &&
                        FlappyAgo.playMusic.getVolume() == 0)) {
            soundButton.remove();
            changeSoundButton("ON");
            FlappyAgo.setMasterVolume(0.5f);
            // sets the volume back high
        }
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

        // stage.getBatch().draw(backgroundTexture, 0, 0);
        // stage.getBatch().draw(titleTexture, 0, 0);
        stage.getBatch().end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        FlappyAgo.menuMusic.play();
    }

    @Override
    public void dispose() {
        FlappyAgo.menuMusic.stop();
        stage.dispose();
        System.out.println("Menu state disposed");
    }
}
