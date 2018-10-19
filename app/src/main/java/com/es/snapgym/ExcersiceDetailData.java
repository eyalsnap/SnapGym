package com.es.snapgym;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EYAL on 24/09/2018.
 */

public class ExcersiceDetailData extends DBDetailObject implements Parcelable{

    private final String name;
    private final int repeat;

    public ExcersiceDetailData(String name, int repeat) {
        this.name = name;
        this.repeat = repeat;
    }

    protected ExcersiceDetailData(Parcel in) {
        name = in.readString();
        repeat = in.readInt();
    }

    public String getName() {
        return this.name;
    }

    public int getRepeat() {
        return this.repeat;
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
