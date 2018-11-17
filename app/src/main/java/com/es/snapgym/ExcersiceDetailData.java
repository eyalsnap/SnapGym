package com.es.snapgym;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.NumberPicker;

import java.util.LinkedList;

/**
 * Created by EYAL on 24/09/2018.
 */

public class ExcersiceDetailData extends DBDetailObject implements Parcelable{

    private final String name;
    private final int repeat;
    private RhythmAbstractClass rhythm;

    public ExcersiceDetailData(String name, int repeat) {
        this.name = name;
        this.repeat = repeat;
        this.rhythm = new RhythmClassEmpty();
    }

    public ExcersiceDetailData(String name, int repeat, String rhythmString) {
        this.name = name;
        this.repeat = repeat;
        this.rhythm = new RhythmClassReal(rhythmString);
    }

    public ExcersiceDetailData(String name, int repeat, LinkedList<NumberPicker> rhythmLst, int preparingTime) {
        this.name = name;
        this.repeat = repeat;
        this.rhythm = new RhythmClassReal(rhythmLst, preparingTime);
    }

    protected ExcersiceDetailData(Parcel in) {
        name = in.readString();
        repeat = in.readInt();
        this.rhythm = new RhythmClassEmpty();
    }

    public void setRhythm(RhythmAbstractClass newRhytm){
        this.rhythm = newRhytm;
    }

    public String getRhythm(){
        return this.rhythm.getRhythmString();
    }

    public String getName() {
        return this.name;
    }

    public int getRepeat() {
        return this.repeat;
    }

    public int getPrepareTime(){
        return this.rhythm.getPreparingTime();
    }

    public LinkedList<Integer> getRhythmTimes(){
        return this.rhythm.getTimesLst();
    }

    @Override
    public String getIdObject() {
        return this.getName();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(repeat);
    }

    public static final Creator<ExcersiceDetailData> CREATOR = new Creator<ExcersiceDetailData>() {
        @Override
        public ExcersiceDetailData createFromParcel(Parcel in) {
            return new ExcersiceDetailData(in);
        }

        @Override
        public ExcersiceDetailData[] newArray(int size) {
            return new ExcersiceDetailData[size];
        }
    };
}
