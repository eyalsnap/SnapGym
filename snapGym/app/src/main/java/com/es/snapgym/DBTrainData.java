package com.es.snapgym;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

/**
 * Created by EYAL on 28/09/2018.
 */

public class DBTrainData extends DBConnector {

    public DBTrainData(Context context, String trainName) {
        super(context, trainName, "TABLE_NAME");
    }

    @Override
    protected String getCreationString() {
        return " (ID INTEGER PRIMARY KEY, TABLE_NAME STRING, TYPE STRING, DATE LONG, DATE_TO_SHOW STRING, LOCATION STRING)";
    }

    @Override
    protected void updateContentValues(ContentValues contentValues, DBDetailObject objectDetail) {
        TrainDetailData reportDetail = (TrainDetailData) objectDetail;
        contentValues.put("table_name", reportDetail.getTrainTableName());
        contentValues.put("type", reportDetail.getTrainType());
        contentValues.put("date", reportDetail.getDate_in_milis());
        contentValues.put("date_to_show", reportDetail.getDateToShow());
        contentValues.put("location", reportDetail.getLocation());
    }

    @Override
    protected Object convertDataToReportDetail(Cursor data) {
        String name = data.getString(1);
        String type = data.getString(2);
        long date = data.getLong(3);
        String date_to_show = data.getString(4);
        String location = data.getString(5);
        return new TrainDetailData(name, type, date, date_to_show, location);
    }

    public String getPreviousTrain(String type){
        String sql = "SELECT TABLE_NAME FROM " + this.tableName + " WHERE DATE = (SELECT max(DATE) FROM " + this.tableName + " WHERE TYPE = '" + type + "') AND TYPE = '" + type + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(sql, null);
        if (data.getCount() <= 0)
            return null;

        data.moveToLast();
        return data.getString(0);
    }

    public LinkedList<TrainDetailData> loadTrains() {
        LinkedList<TrainDetailData> newExcersicesList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + this.tableName;
        Cursor data = db.rawQuery(query, null);

        data.moveToFirst();
        while(!data.isAfterLast()) {
            newExcersicesList.add((TrainDetailData)convertDataToReportDetail(data));
            data.moveToNext();
        }
        data.close();

        return newExcersicesList;

    }
}
