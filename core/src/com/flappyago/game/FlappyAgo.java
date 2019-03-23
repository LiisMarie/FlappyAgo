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

	public static int maxScore;
	public static float masterVolume;
	public static Music menuMusic;
	public static Music playMusic;


	@Override
	public void create () {
		maxScore = 0;
		batch = new SpriteBatch();
		gameStateManager = new GameStateManager();

		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("musicb.mp3"));
		menuMusic.setLooping(true);
		menuMusic.setVolume(0.5f);  // 1f is 100% volume
		menuMusic.play();

		playMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		playMusic.setLooping(true);
		masterVolume = 0.5f;
		playMusic.setVolume(masterVolume);  // 1f is 100% volume

		Gdx.gl.glClearColor(1, 0, 0, 1);

		gameStateManager.push(new MenuState(gameStateManager));
	}

	public void setMasterVolume(float newVolume) {
		masterVolume = newVolume;
		menuMusic.setVolume(masterVolume);
		playMusic.setVolume(masterVolume);
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
		playMusic.dispose();
		menuMusic.dispose();
	}
}
