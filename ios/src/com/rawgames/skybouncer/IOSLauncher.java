package com.rawgames.skybouncer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;
import com.rawgames.skybouncer.utils.AdHandler;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.*;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.pods.google.mobileads.*;

import java.util.ArrayList;
import java.util.List;

public class IOSLauncher extends IOSApplication.Delegate implements AdHandler{

    private static CGSize AD_SIZE;

    private static final boolean USE_TEST_DEVICES = true;

    private static final Logger log = new Logger(IOSLauncher.class.getName(), Application.LOG_DEBUG);
    private GADBannerView adview;
    private boolean adsInitialized = false;
    private IOSApplication iosApplication;

    @Override
    protected IOSApplication createApplication() {

        ShowAdListener show = new ShowAdListener();
        HideAdListener hide = new HideAdListener();
        show.setBase(this);
        hide.setBase(this);

        // spel
        Game app = new Game(this);
        app.addListener(show);
        app.addListener(hide);

        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = false;
        config.orientationPortrait = true;


        return new IOSApplication(app, config);

    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    // VARNING FÖR KAOS

    public void hideAd(){
        System.out.println("hiding ad");

        if (adsInitialized) {

            //System.out.println("Hiding Ad");
            Foundation.log("Hiding Ad");

            final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
            double screenWidth = screenSize.getWidth();


            AD_SIZE = adview.getBounds().getSize();
            double adWidth = AD_SIZE.getWidth();
            double adHeight = AD_SIZE.getHeight();

            float bannerWidth = (float) screenWidth;
            float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

            adview.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
        }

    }

    public void showAd() {

        //System.out.println("Showing Ad");
        Foundation.log("Showing Ad");

        initializeAds();

        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();

        AD_SIZE = adview.getBounds().getSize();
        double adWidth = AD_SIZE.getWidth();
        double adHeight = AD_SIZE.getHeight();

        log.debug(String.format("Hidding ad. size[%s, %s]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, 0, bannerWidth, bannerHeight));

    }

    public void initializeAds(){
        if (!adsInitialized){

            // loggar
            Foundation.log("Initializing ads...");

            adsInitialized = false;

            // lägger in reklam viewen eller något
            adview = new GADBannerView(GADAdSize.SmartBannerPortrait());
            adview.setAdUnitID("ca-app-pub-3940256099942544/6300978111");
            adview.setRootViewController(iosApplication.getUIViewController());
            iosApplication.getUIViewController().getView().addSubview(adview);

            final GADRequest request = new GADRequest();

            if (USE_TEST_DEVICES){
                final NSArray<NSObject> testDevices = new NSArray<NSObject>(
                    new NSString(GADRequest.getSimulatorID()));

                List<String> list = new ArrayList<>();
                list.add(GADRequest.getSimulatorID());

                request.setTestDevices(list);

                //log.debug("Test devices: " + request.getTestDevices());
                Foundation.log("Test devices: " + request.getTestDevices());
            }

            adview.setDelegate(new GADBannerViewDelegateAdapter(){

                @Override
                public void didReceiveAd(GADBannerView bannerView) {
                    super.didReceiveAd(bannerView);
                    log.debug("didReceiveAd");
                }

                @Override
                public void didFailToReceiveAd(GADBannerView bannerView, GADRequestError error) {
                    super.didFailToReceiveAd(bannerView, error);
                    Foundation.log("ERROR at didFailToReceiveAd: " + error);
                }
            });


            adview.loadRequest(request);

            Foundation.log("initializing ads complete");
        }
    }

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