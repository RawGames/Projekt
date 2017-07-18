package com.rawgames.skybouncer;

import com.rawgames.skybouncer.utils.AdHandler;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;
import org.robovm.pods.google.mobileads.GADAdSize;
import org.robovm.pods.google.mobileads.GADBannerView;
import org.robovm.pods.google.mobileads.GADRequest;

import java.util.Arrays;

/**
 * Created by sebastianjohansson on 2017-07-18.
 */

@CustomClass("ViewController")
public class ViewController extends UIViewController implements AdHandler{

    private GADBannerView bannerView;
    private boolean adsInitialized = false;

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        bannerView.setAdUnitID("ca-app-pub-3940256099942544/6300978111");
        bannerView.setRootViewController(this);
        bannerView.loadRequest(createRequest());

    }

    private GADRequest createRequest(){
        GADRequest request = new GADRequest();

        request.setTestDevices(Arrays.asList(GADRequest.getSimulatorID()));
        return request;
    }

    @IBOutlet
    private void setBannerView(GADBannerView bannerView){
        this.bannerView = bannerView;
    }

    public void initializeAds(){
        if (!adsInitialized){

            adsInitialized = true;

            bannerView = new GADBannerView(GADAdSize.SmartBannerPortrait());
            bannerView.setAdUnitID("ca-app-pub-3940256099942544/6300978111");
            bannerView.setRootViewController(this);


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
