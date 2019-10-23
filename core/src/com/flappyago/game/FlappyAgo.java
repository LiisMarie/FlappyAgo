package com.flappyago.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappyago.game.music.GameMusic;
import com.flappyago.game.scores.Scores;
import com.flappyago.game.states.GameStateManager;
import com.flappyago.game.states.MenuState;

import java.awt.DisplayMode;

public class FlappyAgo extends ApplicationAdapter {

	public static int maxScore;
	private Preferences pref;
	private Scores scores;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 750;

	public static final String TITLE = "Flappy Ago";

	private GameStateManager gameStateManager;
	private SpriteBatch batch;

	@Override
	public void create () {
		GameMusic gameMusic = new GameMusic();
		Gdx.input.setCatchBackKey(true);
		maxScore = 0;
		batch = new SpriteBatch();
		gameStateManager = new GameStateManager();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		pref = Gdx.app.getPreferences("SharedPrefs");
		if (!pref.contains("HighScore")) {
			pref.putInteger("HighScore", 0);
		}
		if (!pref.contains("AiScore")) {
			pref.putInteger("AiScore", 0);
		}
		scores = new Scores();
		gameStateManager.push(new MenuState(gameStateManager));
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
		GameMusic.dispose();
		super.dispose();
	}
}
