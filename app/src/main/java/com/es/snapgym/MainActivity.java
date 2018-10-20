package com.es.snapgym;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final static String TRAINS_DB_NAME = "all_trains_table";

    private String currentTrainTableName;
    String dateString;
    Date date;
    private final boolean shouldCleanTable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.locationTextView);
        tv.setTag(tv.getKeyListener());
        tv.setKeyListener(null);

        if (shouldCleanTable) {
            DBTrainData dbTrainData = new DBTrainData(this, TRAINS_DB_NAME);
            SQLiteDatabase db = dbTrainData.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TRAINS_DB_NAME);
            db.execSQL("CREATE TABLE " + TRAINS_DB_NAME + "(ID INTEGER PRIMARY KEY, TABLE_NAME STRING, TYPE STRING, DATE LONG, DATE_TO_SHOW STRING, LOCATION STRING)");
        }

        setDate();

        Button toAppButton = (Button) findViewById(R.id.toAppButton);
        toAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String location = getLocation();
                String type = getType();
                if (location.isEmpty())
                    missingArgument("location");
                else
                    if (type.isEmpty())
                        missingArgument("type");
                else
                    moveToTrain(location, type);

            }

        });

        Button toWatchButton = (Button) findViewById(R.id.watchButton);
        toWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchingTrains();
            }
        });

        Button tempButton = (Button) findViewById(R.id.tempButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempFunction();
            }
        });
    }

    private void watchingTrains() {
        Intent watchActivity = new Intent(getApplicationContext(), TrainDetailsWatchActivity.class);
        watchActivity.putExtra("com.es.snapgym.TRAIN_TABLE_NAME", TRAINS_DB_NAME);
        startActivity(watchActivity);
    }

    private void setDate() {
        date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = dateFormat.format(new Date());
        dateString = dateString.replace("/", "_");

        TextView dateTextView = (TextView) findViewById(R.id.excersiceTextView);
        dateTextView.setText(dateString);
    }

    private void moveToTrain(String location, String type) {
        Intent trainListActivity = new Intent(getApplicationContext(), TrainDetailsActivity.class);

        String previousTrain = getPreviousTrainAndAddingCurrent(location, type);

        savingIntentVariables(location, type, trainListActivity, previousTrain);

        startActivity(trainListActivity);
    }

    private void savingIntentVariables(String location, String type, Intent trainListActivity, String previousTrain) {
        trainListActivity.putExtra("com.es.snapgym.LOCATION", location);
        trainListActivity.putExtra("com.es.snapgym.TYPE", type);
        trainListActivity.putExtra("com.es.snapgym.DATE", dateString);
        trainListActivity.putExtra("com.es.snapgym.PREVIOUS_TRAIN_TABLE_NAME", previousTrain);
        trainListActivity.putExtra("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME", this.currentTrainTableName);

        trainListActivity.putExtra("com.es.snapgym.IS_WATCHING", false);
    }

    private String getPreviousTrainAndAddingCurrent(String location, String type) {

        DBTrainData dbTrainData = new DBTrainData(this, TRAINS_DB_NAME);
        String previousTrain = dbTrainData.getPreviousTrain(type);

        TrainDetailData trainDetailData1 = createTrainDetailData(location, type);
        dbTrainData.addRoundNoDelete(trainDetailData1);

        return previousTrain;

    }

    private void missingArgument(String arg) {
        Toast.makeText(getApplicationContext(), "Enter " + arg + " First", Toast.LENGTH_SHORT).show();
    }

    private String getLocation() {
        TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
        return locationTextView.getText().toString();
    }

    private String getType() {
        TextView locationTextView = (TextView) findViewById(R.id.trainTypeTextView);
        return locationTextView.getText().toString();
    }

    private TrainDetailData createTrainDetailData(String location, String type){
        this.currentTrainTableName = dateString + "_" + date.getTime() + "_in_" + location + "_type_" + type;
        return new TrainDetailData(this.currentTrainTableName,type,date.getTime(),dateString, location);
    }

    private void tempFunction(){
        Intent editRhythmActivity = new Intent(getApplicationContext(), EditRhythmActivity.class);
        editRhythmActivity.putExtra("com.es.snapgym.EXCERSICE_OBJECT", new ExcersiceDetailData("ex_name", 5));
        startActivity(editRhythmActivity );
    }
}
