package com.rawgames.skybouncer;

import com.rawgames.skybouncer.utils.AdHandler;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.annotation.CustomClass;

/**
 * Created by sebastianjohansson on 2017-07-18.
 */

@CustomClass("ViewController")
public class ViewController extends UIViewController implements AdHandler{

    @Override
    public void showAds(boolean show) {

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
