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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.flappyago.game.FlappyAgo;

public class MenuState extends State {
    private Texture backgroundTexture;
    private Texture playTexture;

    private Drawable drawableBackground;
    private Drawable drawablePlay;
    private ImageButton playButton;

    private Stage stage;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        camera.setToOrtho(false, FlappyAgo.WIDTH / 2,
                FlappyAgo.HEIGHT / 2);

        stage = new Stage(new ScreenViewport());

        backgroundTexture = new Texture("background.png");
        drawableBackground = new TextureRegionDrawable((new TextureRegion(backgroundTexture)));
        Image bg = new Image(backgroundTexture);
        bg.setPosition(0, 0);
        stage.addActor(bg);

        playTexture = new Texture("playbutton.png");
        drawablePlay = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(drawablePlay);


        stage.addActor(playButton);
        Gdx.input.setInputProcessor(stage);

        playButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return Gdx.input.justTouched();
            }
        });

    }

    @Override
    public void handleInput() {
        // when user has touched the screen (with mouse or button)
        if (Gdx.input.justTouched() && playButton.isPressed()) {
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();  // checks whether there is any input all the time
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        // sb.begin();
        // sb.draw(background, 0,0);
        // sb.draw(background, 0,0, FlappyAgo.WIDTH, FlappyAgo.HEIGHT);
        // 0, 0 bottom left hand corner

        //sb.draw(backgroundTexture, 0, 0, FlappyAgo.WIDTH, FlappyAgo.HEIGHT);
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui

        // sb.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        // playButton.dispose();
        System.out.println("Menu state disposed");
    }
}
