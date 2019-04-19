package com.flappyago.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappyago.game.music.GameMusic;
import com.flappyago.game.states.GameStateManager;
import com.flappyago.game.states.MenuState;

public class FlappyAgo extends ApplicationAdapter {
	public static int maxScore;
	private Preferences pref;

	public static final int WIDTH = 480;  // 480
	public static final int HEIGHT = 750;  // 750

	public static final String TITLE = "Flappy Ago";

	private GameStateManager gameStateManager;
	private SpriteBatch batch;

	@Override
	public void create () {
		GameMusic gameMusic = new GameMusic();
		maxScore = 0;
		batch = new SpriteBatch();
		gameStateManager = new GameStateManager();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		pref = Gdx.app.getPreferences("SharedPrefs");
		if (!pref.contains("HighScore")) {
			pref.putInteger("HighScore", 0);
		}
		maxScore = pref.getInteger("HighScore");

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
		if (maxScore > pref.getInteger("HighScore")) {
			pref.putInteger("HighScore", maxScore);
			pref.flush();
		}
		GameMusic.dispose();
		super.dispose();
	}
}
