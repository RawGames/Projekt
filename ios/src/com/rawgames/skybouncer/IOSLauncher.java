package com.rawgames.skybouncer;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.rawgames.skybouncer.utils.AdHandler;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

public class IOSLauncher extends IOSApplication.Delegate implements AdHandler{

    private boolean adsInitialized = false;

    private IOSApplication iosApp;

    @Override
    protected IOSApplication createApplication() {

        // spel
        Game app = new Game(this);


        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = false;
        config.orientationPortrait = true;

        iosApp = new IOSApplication(app, config);

        return iosApp;

    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }


    public void showDick(){
        System.out.println("AOAOAOAOAO");
    }


    @Override
    public void showAds(boolean show) {
        showDick();
    }

    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void submitScore(int highScore) {

    }

    @Override
    public void showScore() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }
}