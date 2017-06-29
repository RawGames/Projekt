package com.mygdx.game.utils;

/**
 * Created by sebastianjohansson on 2017-06-29.
 */
public class Timer {

    int time;
    boolean timerStart;

    // sätter timer på -1 automatiskt
    public Timer(boolean timerRunning){
        time = -1;
        timerStart = timerRunning;
    }
    // sätter timer till vad man vill
    public Timer(int time, boolean timerRunning){
        this.time = time;
        timerStart = timerRunning;
    }

    public void timerStart(){
        timerStart = true;
    }
    public void timerStop(){
        timerStart = false;
    }

    // subtraherar timern och kollar om den är 0
    public boolean checkTimer(){
        if (time >= 0 && timerStart) time--;
        if (time == 0) return true;
        return false;
    }

}
