package com.es.snapgym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

public class WatchingBoardActivity extends AppCompatActivity {

    private LinkedList<AllExcersiceResultData> allExcersiceResultDatas;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watching_board);

        initExcersice();

        printingTrainTimeAndLocation();

        WatchListAdapter t = new WatchListAdapter(this, allExcersiceResultDatas);

        ListView lv = (ListView) findViewById(R.id.excersiceWatchListView);
        lv.setAdapter(t);
    }

    private void initExcersice() {
        String tableName = getIntent().getExtras().getString("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME");
        DBExcersiceData dbExcersiceData = new DBExcersiceData(this, tableName);
        LinkedList<ExcersiceDetailData> excersiceDetails = dbExcersiceData.loadExcersices();

        allExcersiceResultDatas = new LinkedList<>();
        for (int exIndex=0; exIndex<excersiceDetails.size(); exIndex++) {
            ExcersiceDetailData currentEx = excersiceDetails.get(exIndex);
            String exTableName = "table_of_" + tableName + "_" + currentEx.getName();
            DBSetData currentExcersiceResult = new DBSetData(this, exTableName);
            AllExcersiceResultData currentResults = new AllExcersiceResultData(currentExcersiceResult, currentExcersiceResult, currentEx.getRepeat(), currentEx.getName());
            allExcersiceResultDatas.add(currentResults);
        }
    }


    private void printingTrainTimeAndLocation() {
        String location = getIntent().getExtras().getString("com.es.snapgym.LOCATION");
        String date = getIntent().getExtras().getString("com.es.snapgym.DATE");

        TextView locationTextView = (TextView) findViewById(R.id.watchingLocationTextView);
        locationTextView.setText(location);

        TextView dateTextView = (TextView) findViewById(R.id.watchingDateTextView);
        dateTextView.setText(date);
    }
}
