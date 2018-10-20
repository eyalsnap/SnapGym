package com.es.snapgym;

/**
 * Created by EYAL on 20/10/2018.
 */

public class RhythmLine {

    private final int round;
    private int seconds;

    public RhythmLine(int theRound, int theSeconds){
        this.round = theRound;
        this.seconds = theSeconds;

    }

    public int getRound(){
        return this.round;
    }

    public int getSeconds(){
        return this.seconds;
    }

}
