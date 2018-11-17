package com.es.snapgym;

import java.util.LinkedList;

/**
 * Created by EYAL on 22/10/2018.
 */

public abstract class RhythmAbstractClass {

    private int preparingTime;
    private LinkedList<Integer> times;

    public RhythmAbstractClass(int preparingTime, LinkedList<Integer> theTimes){
        this.preparingTime = preparingTime;
        this.times = theTimes;
    }

    public RhythmAbstractClass(LinkedList<Integer> theTimes){
        this.times = theTimes;
        this.preparingTime = 0;
    }

    public int getPreparingTime(){
        return preparingTime;
    }

    public LinkedList<Integer> getTimesLst(){
        return this.times;
    }

    public String getRhythmString(){
        String rhythmString = "" + this.times.getFirst();

        for (int timeIndex = 1; timeIndex<this.times.size(); timeIndex++)
            rhythmString = rhythmString + ":" + this.times.get(timeIndex);

        return rhythmString;
    }

}
