package com.es.snapgym;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

/**
 * Created by EYAL on 27/09/2018.
 */

public class DBSetData extends DBConnector {

    public DBSetData(Context context, String excersiceName) {
        super(context, "table_of_" + excersiceName, "ROUND");
    }

    @Override
    protected String getCreationString(){
        return  " (ID INTEGER PRIMARY KEY, ROUND INTEGER, WEIGHT FLOAT, REPEAT INTEGER, TARGET INTEGER)";
    }

    @Override
    protected void updateContentValues(ContentValues contentValues, DBDetailObject objectDetail) {

        SetDetailData reportDetail = (SetDetailData) objectDetail;
        contentValues.put("round", reportDetail.getRound());
        contentValues.put("weight", reportDetail.getWeight());
        contentValues.put("repeat", reportDetail.getRepeat());
        contentValues.put("target", reportDetail.getTarget());
    }

    @Override
    protected Object convertDataToReportDetail(Cursor data) {
        int round = data.getInt(1);
        float weight = data.getFloat(2);
        int repeat = data.getInt(3);
        int target = data.getInt(4);
        return new SetDetailData(round, weight, repeat, target);
    }

    public LinkedList<SetDetailData> getAllSets() {
        LinkedList<SetDetailData> allSets = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + this.tableName;
        Cursor data = db.rawQuery(query, null);

        data.moveToFirst();
        while(!data.isAfterLast()) {
            allSets.add((SetDetailData)convertDataToReportDetail(data));
            data.moveToNext();
        }

        return allSets;
    }

}
