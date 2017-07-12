package com.rawgames.skybouncer.utils;

/**
 * Created by sebastianjohansson on 2017-07-12.
 */

import com.rawgames.skybouncer.utils.ListenerManager.ListenerType;

public interface Listener {

    public abstract void call();
    public abstract ListenerType type();

}
