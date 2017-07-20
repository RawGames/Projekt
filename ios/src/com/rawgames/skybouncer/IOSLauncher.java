package com.rawgames.skybouncer;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.rawgames.skybouncer.utils.AdHandler;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.pods.google.mobileads.GADAdSize;
import org.robovm.pods.google.mobileads.GADBannerView;
import org.robovm.pods.google.mobileads.GADInterstitial;
import org.robovm.pods.google.mobileads.GADRequest;

import java.util.Arrays;

public class IOSLauncher extends IOSApplication.Delegate implements AdHandler{

    private boolean adsInitialized = false;
    GADBannerView banner;

    GADInterstitial interstitial;

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

    public void showBanner(){
        initBanner();

        banner.setRootViewController(iosApp.getUIViewController());

        iosApp.getUIViewController().getView().addSubview(banner);

        GADRequest request = new GADRequest();
        banner.loadRequest(request);

        CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        CGSize adSize = banner.getBounds().getSize();
        double adHeight = adSize.getHeight();
        double adWidth = adSize.getWidth();

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        banner.setFrame(new CGRect(screenWidth / 2 - adWidth / 2, screenHeight - bannerHeight, bannerWidth, bannerHeight));
    }

    public void showInterstitial(){

        interstitial = new GADInterstitial("ca-app-pub-7220882176968020/1095944394");

        GADRequest request = new GADRequest();
        request.setTestDevices(Arrays.asList(GADRequest.getSimulatorID()));

        interstitial.loadRequest(request);
        interstitial.present(iosApp.getUIViewController());

        if (interstitial.isReady()){
            interstitial.present(iosApp.getUIViewController());
        } else {
            interstitial.loadRequest(request);
        }

    }

    public void initBanner(){
        if (!adsInitialized){
            System.out.println("initializing...");
            adsInitialized = true;
            banner = new GADBannerView();
            banner.setAdUnitID("ca-app-pub-3940256099942544/6300978111");
            banner.setAdSize(GADAdSize.Banner());

        }
    }

    @Override
    public void showAds(boolean show) {
        //showInterstitial();
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