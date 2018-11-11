package com.es.snapgym;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

/**
 * Created by EYAL on 28/09/2018.
 */

public class DBExcersiceData extends DBConnector {

    public DBExcersiceData(Context context, String trainTableName) {
        super(context, trainTableName, "EXCERSICE_NAME");
    }

    @Override
    protected String getCreationString() {
        return  " (ID INTEGER PRIMARY KEY, EXCERSICE_NAME STRING, REPEAT INTEGER, RHYTHM STRING)";
    }

    @Override
    protected void updateContentValues(ContentValues contentValues, DBDetailObject objectDetail) {
        ExcersiceDetailData ExcersiceDetail = (ExcersiceDetailData) objectDetail;
        contentValues.put("excersice_name", ExcersiceDetail.getName());
        contentValues.put("repeat", ExcersiceDetail.getRepeat());
        contentValues.put("rhythm", ExcersiceDetail.getRhythm());
    }

    @Override
    protected Object convertDataToReportDetail(Cursor data) {
        return convertDataToReportDetailAfterMoving(data);
    }

    private ExcersiceDetailData convertDataToReportDetailAfterMoving(Cursor data) {
        String name = data.getString(1);
        int repeat = data.getInt(2);
        String rhytmString = data.getString(3);
        if ("rhythm".equals(rhytmString))
            return new ExcersiceDetailData(name, repeat);
        else
            return new ExcersiceDetailData(name, repeat, rhytmString);
    }

    public LinkedList<ExcersiceDetailData> loadExcersices() {
        LinkedList<ExcersiceDetailData> newExcersicesList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + this.tableName;
        Cursor data = db.rawQuery(query, null);

        data.moveToFirst();
        while(!data.isAfterLast()) {
            newExcersicesList.add(convertDataToReportDetailAfterMoving(data));
            data.moveToNext();
        }
        data.close();

        return newExcersicesList;

    }
}
