package com.rawgames.skybouncer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rawgames.skybouncer.Game;
import com.rawgames.skybouncer.utils.AdHandler;

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
