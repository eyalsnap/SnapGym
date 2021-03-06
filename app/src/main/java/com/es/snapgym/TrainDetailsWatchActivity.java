package com.es.snapgym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;

public class TrainDetailsWatchActivity extends AppCompatActivity {

    private ListView trainsListView;
    private LinkedList<TrainDetailData> trainsDetails = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details_watch);

        initExcersiceList();
    }


    private void initExcersiceList() {

        String trainsTableName = getIntent().getExtras().getString("com.es.snapgym.TRAIN_TABLE_NAME");
        trainsTableName = trainsTableName.replace("table_of_","");
        DBTrainData dbt = new DBTrainData(this, trainsTableName);

        trainsDetails = dbt.loadTrains();
        updatingTrainList();

    }

    private void updatingTrainList() {

        final TrainListAdapter trainListAdapter = new TrainListAdapter(this, trainsDetails);

        trainsListView = (ListView) findViewById(R.id.TrainsListView);
        trainsListView.setAdapter(trainListAdapter);

        trainsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TrainDetailData trainDetailData = trainsDetails.get(position);

                Intent watchingTrainIntent = new Intent(getApplicationContext(), WatchingBoardActivity.class);

                watchingTrainIntent.putExtra("com.es.snapgym.LOCATION", trainDetailData.getLocation());
                watchingTrainIntent.putExtra("com.es.snapgym.TYPE", trainDetailData.getTrainType());
                watchingTrainIntent.putExtra("com.es.snapgym.DATE", trainDetailData.getDateToShow());
                watchingTrainIntent.putExtra("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME", trainDetailData.getTrainTableName());

                startActivity(watchingTrainIntent);
            }
        });

    }

}
