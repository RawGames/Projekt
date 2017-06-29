package com.mygdx.game.utils;

/**
 * Created by sebastianjohansson on 2017-06-29.
 */
public class Timer {

    int time;

    // sätter timer på -1 automatiskt
    public Timer(){
        time = -1;
    }
    // sätter timer till vad man vill
    public Timer(int time){
        this.time = time;
    }

    // subtraherar timern och kollar om den är 0
    public boolean checkTimer(){
        if (time >= 0) time--;
        if (time == 0) return true;
        return false;
    }

}
