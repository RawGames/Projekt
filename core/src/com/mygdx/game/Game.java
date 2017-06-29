package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameobjects.ObjectHandler;
import com.mygdx.game.graphics.GameCamera;


public class Game extends ApplicationAdapter {

	// Graphics
	SpriteBatch batch;
	public static GameCamera cam;
	BitmapFont scoreFont;
	float fontWidth;
	GlyphLayout fontLayout;

	// test
	Texture img;

	// Objekt
	static ObjectHandler oh;

	// gameplay
	public static boolean GameStarted = false;
	public static int score;

	public final static int WIDTHT = 180;
	public final static int HEIGHT = 320;

	@Override
	public void create () {
		// sprite batch
		batch = new SpriteBatch();
		scoreFont = new BitmapFont();
		fontLayout = new GlyphLayout();
		scoreFont.setUseIntegerPositions(false);

		// kamera skit
		cam = new GameCamera(WIDTHT, HEIGHT);
		cam.setPosition(0,0);
		cam.update();

		// objekt
		oh = new ObjectHandler();

		img = new Texture("test.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.55f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.projection);
		// Uppdatera saker här
		oh.update();

		// uppdaterar kameran
		cam.update();

		batch.begin();
		// Måla saker här

		// målar object
		oh.draw(batch);

		// målar poäng
		fontLayout.setText(scoreFont, Integer.toString(score));
		fontWidth = fontLayout.width;
		scoreFont.draw(batch, Integer.toString(score), WIDTHT/2 - fontWidth/2, HEIGHT-50+cam.y);

		batch.end();
	}

	public static void restart(){
		cam.setPosition(0, 0);
		GameStarted = false;
		oh.restart();
		score = 0;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		oh.dispose();
		img.dispose();
		scoreFont.dispose();
	}
}
