package com.rawgames.skybouncer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rawgames.skybouncer.gameobjects.ObjectHandler;
import com.rawgames.skybouncer.utils.AdHandler;


public class Game extends ApplicationAdapter {




	// Graphics
	SpriteBatch batch;
	public static com.rawgames.skybouncer.graphics.GameCamera cam;
	BitmapFont scoreFont;
	float fontWidth;
	GlyphLayout fontLayout;
	Texture tapScreen;

	Color c;

	float alpha;
	int alphaTarget;

	// Objekt
	static ObjectHandler oh;

	// mark bild
	Texture groundImg;

	// Ad stuff
	AdHandler handler;
	boolean toggleAd;

	// gameplay
	public static boolean sound = true;
	public static boolean GameStarted = false;
	public static int score;
	public static int highScore;

	public final static int WIDTHT = 180;
	public final static int HEIGHT = 320;

	static FileHandle saveFile;


	public Game(AdHandler handler){
		// android reklam
		this.handler = handler;
		toggleAd = true;
		handler.showAds(true);

		// ios reklam

	}


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
		cam = new com.rawgames.skybouncer.graphics.GameCamera(WIDTHT, HEIGHT);
		cam.setPosition(0,0);
		cam.update();

		c = batch.getColor();
		alpha = 1;
		alphaTarget = 0;

		// objekt
		oh = new ObjectHandler();
		groundImg = new Texture("ground.png");

		tapScreen = new Texture("tapScreen.png");

	//	handler.showScore();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.58f, 0.8f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.projection);
		// Uppdatera saker här
		oh.update();

		// uppdaterar ads
		if (GameStarted && toggleAd || !GameStarted && !toggleAd){
			toggleAd = !toggleAd;
			handler.showAds(toggleAd);


		}

		// uppdaterar kameran lmao
		cam.update();

		batch.begin();
		batch.setColor(c.r, c.b, c.g, 1);
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
			scoreFont.draw(batch, "HIGHSCORE: " + Integer.toString(highScore), WIDTHT/2 - fontWidth/2, HEIGHT-64+cam.y);
			scoreFont.setColor(255 ,255 ,255 ,1);
			scoreFont.draw(batch, "HIGHSCORE: " + Integer.toString(highScore), WIDTHT/2 - fontWidth/2 -1, HEIGHT-64+cam.y + 1);

		} else {

			scoreFont.getData().setScale(1, 1);
			fontLayout.setText(scoreFont, Integer.toString(score));
			fontWidth = fontLayout.width;
			scoreFont.setColor(0, 0, 0, 1);
			scoreFont.draw(batch, Integer.toString(score), WIDTHT / 2 - fontWidth / 2, HEIGHT - 32 + cam.y);
			scoreFont.setColor(255, 255, 255, 1);
			scoreFont.draw(batch, Integer.toString(score), WIDTHT / 2 - fontWidth / 2 - 1, HEIGHT - 32 + cam.y + 1);

		}

		if (alpha == alphaTarget){
			if (alpha == 0) alphaTarget = 1;
			else alphaTarget = 0;
		}

		alpha = approach(alpha, alphaTarget, .02f);
		batch.setColor(c.r, c.b, c.g, alpha);
		if (!GameStarted){
			batch.draw(tapScreen, WIDTHT/2 - 64, 8);
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

	// approach stuff
	float approach(float value, float target, float speed){

		if (value == target) return value;

		if (value < target){
			value += speed;
			if (value >= target) return target;
		} else {
			value -= speed;
			if (value <= target) return target;
		}

		return value;

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		oh.dispose();
		scoreFont.dispose();
		groundImg.dispose();
		tapScreen.dispose();
	}
}
