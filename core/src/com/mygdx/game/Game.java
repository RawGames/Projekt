package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.graphics.GameCamera;


public class Game extends ApplicationAdapter {

	// Graphics
	SpriteBatch batch;
	GameCamera cam;

	public final static int WIDTHT = 180;
	public final static int HEIGHT = 320;

	@Override
	public void create () {
		batch = new SpriteBatch();

		// kamera skit
		cam = new GameCamera(WIDTHT, HEIGHT);
		cam.setPosition(0,0);
		cam.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.projection);
		// Uppdatera saker här


		// uppdaterar kameran
		cam.update();

		batch.begin();
		// Måla saker här

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
