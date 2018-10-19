package com.es.snapgym;

/**
 * Created by EYAL on 27/09/2018.
 */

public class SetDetailData extends DBDetailObject {

    private final int round;
    private final float weight;
    private final int repeat;
    private final int target;

    public SetDetailData(int theRound, float theWeight, int theRepeat, int theTarget) {
        this.round = theRound;
        this.weight = theWeight;
        this.repeat = theRepeat;
        this.target = theTarget;
    }

    public int getRound(){
        return this.round;
    }

    public float getWeight(){
        return this.weight;
    }

    public int getRepeat(){
        return this.repeat;
    }

    public int getTarget(){
        return this.target;
    }

    @Override
    public Object getIdObject() {
        return this.round;
    }
}
