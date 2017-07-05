package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

	// Objekt
	static ObjectHandler oh;

	// mark bild
	Texture groundImg;

	// gameplay
	public static boolean sound = true;
	public static boolean GameStarted = false;
	public static int score;
	public static int highScore;

	public final static int WIDTHT = 180;
	public final static int HEIGHT = 320;

	static FileHandle saveFile;

	@Override
	public void create () {
		// sprite batch
		batch = new SpriteBatch();
		scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
		fontLayout = new GlyphLayout();
		scoreFont.setUseIntegerPositions(false);

		saveFile = Gdx.files.local("save.txt");
		if (!saveFile.exists()) highScore = 0;
		else highScore = Integer.parseInt(saveFile.readString());

		// kamera skit
		cam = new GameCamera(WIDTHT, HEIGHT);
		cam.setPosition(0,0);
		cam.update();

		// objekt
		oh = new ObjectHandler();
		groundImg = new Texture("ground.png");


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.55f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.projection);
		// Uppdatera saker här
		oh.update();

		// uppdaterar kameran lmao
		cam.update();

		batch.begin();
		// Måla saker här

		// målar object
		oh.draw(batch);

		batch.draw(groundImg, -3, -43);

		oh.drawBtn(batch);

		// målar poäng

		if (!GameStarted){

			scoreFont.getData().setScale(0.75f, 0.75f);
			fontLayout.setText(scoreFont, "HIGHSCORE: " + Integer.toString(highScore));
			fontWidth = fontLayout.width;
			scoreFont.setColor(0, 0, 0,  1);
			scoreFont.draw(batch, "HIGHSCORE: " + Integer.toString(highScore), WIDTHT/2 - fontWidth/2, HEIGHT-32+cam.y);
			scoreFont.setColor(255 ,255 ,255 ,1);
			scoreFont.draw(batch, "HIGHSCORE: " + Integer.toString(highScore), WIDTHT/2 - fontWidth/2 -1, HEIGHT-32+cam.y + 1);

		} else {

			scoreFont.getData().setScale(1, 1);
			fontLayout.setText(scoreFont, Integer.toString(score));
			fontWidth = fontLayout.width;
			scoreFont.setColor(0, 0, 0, 1);
			scoreFont.draw(batch, Integer.toString(score), WIDTHT / 2 - fontWidth / 2, HEIGHT - 32 + cam.y);
			scoreFont.setColor(255, 255, 255, 1);
			scoreFont.draw(batch, Integer.toString(score), WIDTHT / 2 - fontWidth / 2 - 1, HEIGHT - 32 + cam.y + 1);

		}

		batch.end();
	}

	static void save(){
		if (score > highScore) {
			if (saveFile.exists()) {
				saveFile.delete();
			}
			highScore = score;
			saveFile.writeString(Integer.toString(highScore), false);
		}
	}

	public static void restart(){
		save();
		cam.setPosition(0, 0);
		GameStarted = false;
		oh.restart();
		score = 0;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		oh.dispose();
		scoreFont.dispose();
		groundImg.dispose();
	}
}
