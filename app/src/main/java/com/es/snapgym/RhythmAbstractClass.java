package com.es.snapgym;

import java.util.LinkedList;

/**
 * Created by EYAL on 22/10/2018.
 */

public abstract class RhythmAbstractClass {

    private LinkedList<Integer> times;

    public RhythmAbstractClass(LinkedList<Integer> theTimes){
        this.times = theTimes;
    }

    public String getRhythmString(){
        String rhythmString = "" + this.times.getFirst();

        for (int timeIndex = 1; timeIndex<this.times.size(); timeIndex++)
            rhythmString = rhythmString + ":" + this.times.get(timeIndex);

        return rhythmString;
    }

}
