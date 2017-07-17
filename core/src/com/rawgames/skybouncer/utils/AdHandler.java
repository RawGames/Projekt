package com.rawgames.skybouncer.utils;

/**
 * Created by sebbe on 2017-07-06.
 */
public interface AdHandler {

    public void showAds(boolean show);

    public void signIn();
    public void signOut();
    public void submitScore(int highScore);
    public void showScore();
    public boolean isSignedIn();

}
