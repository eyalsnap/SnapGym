package com.es.snapgym;

import android.widget.NumberPicker;

import java.util.LinkedList;

/**
 * Created by EYAL on 22/10/2018.
 */

public class RhythmClassReal extends RhythmAbstractClass {

    public RhythmClassReal(LinkedList<NumberPicker> times, int preparingTime){
        super(preparingTime, convertNumberPickerToInteger(times));
    }

    public RhythmClassReal(String rhythmString){
        super(convertStringToInteger(rhythmString));
    }

    private static LinkedList<Integer> convertNumberPickerToInteger(LinkedList<NumberPicker> times){
        LinkedList<Integer> theTimes = new LinkedList<>();
        for (int round_index = 0; round_index < times.size(); round_index++)
            theTimes.add(times.get(round_index).getValue());
        return theTimes;
    }

    private static LinkedList<Integer> convertStringToInteger(String rhythmString){
        String [] values = rhythmString.split(":");
        LinkedList<Integer> time = new LinkedList<>();
        for (int time_index=0; time_index<values.length; time_index++)
            time.add(Integer.parseInt(values[time_index]));
        return time;
    }


}
