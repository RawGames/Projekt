package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Game;
import com.mygdx.game.utils.AdHandler;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Game(new AdHandler() {
			@Override
			public void showAds(boolean show) {

			}
		}), config);

		config.height = Game.HEIGHT;
		config.width = Game.WIDTHT;
	}
}
