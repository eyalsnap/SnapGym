package com.es.snapgym;

/**
 * Created by EYAL on 28/09/2018.
 */

public class TrainDetailData extends DBDetailObject {

    private final String trainTableName;
    private final String trainType;
    private final long date_in_milis;
    private final String date_to_introduce;
    private final String location;

    public TrainDetailData(String theTrainTableName, String type, long theDate, String data_for_show, String theLocation) {
        this.trainTableName = theTrainTableName;
        this.trainType = type;
        this.date_in_milis = theDate;
        this.date_to_introduce = data_for_show;
        this.location = theLocation;
    }

    public String getTrainTableName(){
        return this.trainTableName;
    }

    public String getTrainType(){
        return this.trainType;
    }

    public long getDate_in_milis(){
        return this.date_in_milis;
    }

    public String getDateToShow() {
        return this.date_to_introduce;
    }

    public String getLocation(){
        return this.location;
    }

    @Override
    public Object getIdObject() {
        return this.trainTableName;
    }
}
