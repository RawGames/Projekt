package com.rawgames.skybouncer;

import com.rawgames.skybouncer.utils.Listener;
import com.rawgames.skybouncer.utils.ListenerManager;

/**
 * Created by sebastianjohansson on 2017-07-12.
 */
public class HideAdListener implements Listener {

    IOSLauncher base;

    @Override
    public void call() {
        this.base.hideAd();
    }

    public void setBase(IOSLauncher base){
        this.base = base;
    }

    @Override
    public ListenerManager.ListenerType type() {
        return ListenerManager.ListenerType.SHOWAD;
    }
}
