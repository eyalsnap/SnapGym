package com.es.snapgym;

import java.util.LinkedList;

/**
 * Created by EYAL on 22/10/2018.
 */

public abstract class RhythmAbstractClass {

    private int preparingTime;
    private LinkedList<Integer> times;

    public RhythmAbstractClass(){}

    public RhythmAbstractClass(int preparingTime, LinkedList<Integer> theTimes){
        this.preparingTime = preparingTime;
        this.times = theTimes;
    }

    public RhythmAbstractClass(LinkedList<Integer> theTimes){
        this.times = theTimes;
        this.preparingTime = 0;
    }

    public RhythmAbstractClass(String rhythmString){
        String [] parts = rhythmString.split(":");
        this.preparingTime = Integer.parseInt(parts[0]);
        String [] values = parts[1].split("-");
        this.times = new LinkedList<>();
        for (int time_index=0; time_index<values.length; time_index++)
            this.times.add(Integer.parseInt(values[time_index]));
    }

    private static LinkedList<Integer> convertStringToInteger(String rhythmString){
        String [] parts = rhythmString.split(":");
        int prepareTime = Integer.parseInt(parts[0]);
        String [] values = parts[1].split("-");
        LinkedList<Integer> time = new LinkedList<>();
        for (int time_index=0; time_index<values.length; time_index++)
            time.add(Integer.parseInt(values[time_index]));
        return time;
    }

    public int getPreparingTime(){
        return preparingTime;
    }

    public LinkedList<Integer> getTimesLst(){
        return this.times;
    }

    public String getRhythmString(){
        String rhythmString = preparingTime + ":" + this.times.getFirst();

        for (int timeIndex = 1; timeIndex<this.times.size(); timeIndex++)
            rhythmString = rhythmString + "-" + this.times.get(timeIndex);

        return rhythmString;
    }

}
