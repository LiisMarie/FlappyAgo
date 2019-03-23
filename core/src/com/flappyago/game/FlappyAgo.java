package com.flappyago.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappyago.game.states.GameStateManager;
import com.flappyago.game.states.MenuState;

public class FlappyAgo extends ApplicationAdapter {

	public static final int WIDTH = 480;  // 480
	public static final int HEIGHT = 750;  // 800

	public static final String TITLE = "Flappy Ago";

	private GameStateManager gameStateManager;
	private SpriteBatch batch;

	public static float masterVolume = 0.2f;
	public Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameStateManager = new GameStateManager();

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(masterVolume);  // 1f is 100% volume
		music.play();

		Gdx.gl.glClearColor(1, 0, 0, 1);

		gameStateManager.push(new MenuState(gameStateManager));
	}

	public void setMasterVolume(float newVolume) {
		masterVolume = newVolume;
		music.setVolume(masterVolume);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// wipes the screen clear and then sb redraws everything

		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(batch);

		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		music.dispose();
	}
}
