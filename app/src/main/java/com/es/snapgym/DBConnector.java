package com.es.snapgym;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EYAL on 28/09/2018.
 */

public abstract class DBConnector extends SQLiteOpenHelper {

    protected final String tableName;
    protected final String keyParameter;

    public DBConnector(Context context, String theTableName, String key) {
        super(context, "table_of_" + theTableName, null, 1);
        this.tableName = "table_of_" + theTableName;
        this.keyParameter = key;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + this.tableName + getCreationString();
        db.execSQL(createTable);
    }

    protected abstract String getCreationString();

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + this.tableName);
        onCreate(db);
    }

    public void addRound(DBDetailObject ObjectDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        updateContentValues(contentValues, ObjectDetail);
        deletePreviuosRound(db, ObjectDetail);
        db.insert(this.tableName, null, contentValues);
    }

    public void addRoundNoDelete(DBDetailObject ObjectDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        updateContentValues(contentValues, ObjectDetail);
        db.insert(this.tableName, null, contentValues);
    }

    public void addRoundDeleteIfExist(DBDetailObject ObjectDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        updateContentValues(contentValues, ObjectDetail);
        try {
            deletePreviuosRound(db, ObjectDetail);
        }
        catch(Exception e) {}
        db.insert(this.tableName, null, contentValues);
    }

    protected void deletePreviuosRound(SQLiteDatabase db, DBDetailObject objectToCompare) {
        db.delete(this.tableName, keyParameter + " = '" + getIdFromObject(objectToCompare) + "'", null);
    }


    public Object getObjectData(Object paramterToCompare){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + this.tableName + " WHERE " + keyParameter + " = " + paramterToCompare;
        Cursor data = db.rawQuery(query, null);
        if (data.getCount() <= 0)
            return null;
        data.moveToFirst();
        return convertDataToReportDetail(data);
    }

    protected String getIdFromObject(DBDetailObject objectDetail){
        return objectDetail.getIdObject().toString();
    }

    public void cleanTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.tableName, "1 = 1", null);
    }

    public void copyingTable(String originTable){
        String sql = "INSERT INTO " + this.tableName +
                " SELECT * FROM table_of_" + originTable;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public String getTableName(){
        return this.tableName;
    }

    protected abstract void updateContentValues(ContentValues contentValues, DBDetailObject objectDetail);

    protected abstract Object convertDataToReportDetail(Cursor data);

}
