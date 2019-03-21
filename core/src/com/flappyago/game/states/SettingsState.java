package com.flappyago.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappyago.game.FlappyAgo;

public class SettingsState extends State {
    private Texture backgroundTexture;

    private Viewport viewport;

    private Stage stage;

    public SettingsState(GameStateManager gameStateManager) {
        super(gameStateManager);
        camera.setToOrtho(false, FlappyAgo.WIDTH / 2,
                FlappyAgo.HEIGHT / 2);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        viewport = new StretchViewport(FlappyAgo.WIDTH, FlappyAgo.HEIGHT, camera);
        stage = new Stage(viewport);

        // set background for settings
        backgroundTexture = new Texture("background.png");
        Image bg = new Image(backgroundTexture);
        bg.setPosition(0, 0);
        bg.setSize(FlappyAgo.WIDTH, FlappyAgo.HEIGHT);
        stage.addActor(bg);

        stage.getViewport().apply();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
