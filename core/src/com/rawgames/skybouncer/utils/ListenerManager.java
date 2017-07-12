package com.rawgames.skybouncer.utils;

import java.util.ArrayList;

/**
 * Created by sebastianjohansson on 2017-07-12.
 */
public class ListenerManager {

    ArrayList<Listener> listener;

    public ListenerManager(){
        listener = new ArrayList<Listener>();
    }

    public void add(Listener l){
        if (listener == null){
            listener = new ArrayList<Listener>();
        }
        listener.add(l);
        System.out.println("created a listener");
    }

    public void call(ListenerType type){
        for (Listener l : listener){
            if (l.type() == type){
                l.call();
            }
        }
    }

    public enum ListenerType {
        SHOWAD, HIDEAD
    }

}
