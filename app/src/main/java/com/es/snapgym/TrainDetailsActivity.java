package com.es.snapgym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class TrainDetailsActivity extends AppCompatActivity {

    private NumberPicker addExcersiceNumberPicker;

    private ListView excersiceListView;
    private LinkedList<ExcersiceDetailData> excersiceDetails = new LinkedList<>();
    private DBExcersiceData currentDbExcersiceData;
    private DBExcersiceData previousDbExcersiceData;
    private boolean modeAdding = true;
    private int taggedExcersiceIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details);

        createAddExcersiceButton();

        initTimeAndLocation();
        initExcersiceList();

        updatingExcersiceList();

    }

    private void createAddExcersiceButton() {
        Button addingExcerciseButton = (Button) findViewById(R.id.addingExcersiceButton);
        addingExcerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExcersiceDetailData newExcersiceDetail = createExcersiceDetail();
                if (newExcersiceDetail != null) {
                    addingExcersiceToArray(newExcersiceDetail);
                    updatingExcersiceList();
                }
            }
        });
        Button deletingExcersice = (Button) findViewById(R.id.removingExcersiceButton);
        deletingExcersice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeAdding = true;
                excersiceDetails.remove(taggedExcersiceIndex);
                addingElementsChangingVisible(View.INVISIBLE);
                removingElementsChangingVisible(View.VISIBLE);
                updatingExcersiceList();
            }
        });
        Button editRhythm = (Button) findViewById(R.id.editRhythmButton);
        editRhythm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveCurrentExcersices();

                Intent editRhythmActivity = new Intent(getApplicationContext(), EditRhythmActivity.class);
                editRhythmActivity.putExtra("com.es.snapgym.EXCERSICE_OBJECT", excersiceDetails.get(taggedExcersiceIndex));

                editRhythmActivity.putExtra("com.es.snapgym.LOCATION", getIntent().getExtras().getString("com.es.snapgym.LOCATION"));
                editRhythmActivity.putExtra("com.es.snapgym.TYPE", getIntent().getExtras().getString("com.es.snapgym.TYPE"));
                editRhythmActivity.putExtra("com.es.snapgym.DATE", getIntent().getExtras().getString("com.es.snapgym.DATE"));
                editRhythmActivity.putExtra("com.es.snapgym.PREVIOUS_TRAIN_TABLE_NAME", getIntent().getExtras().getString("com.es.snapgym.PREVIOUS_TRAIN_TABLE_NAME"));
                editRhythmActivity.putExtra("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME", getIntent().getExtras().getString("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME"));

                startActivity(editRhythmActivity );

            }
        });
    }

    private ExcersiceDetailData createExcersiceDetail() {
        EditText newnameTextView = (EditText) findViewById(R.id.newExcersiceEditView);

        String newName = newnameTextView.getText().toString();
        int newRepeat = addExcersiceNumberPicker.getValue();

        if (isExcersiceExist(newName)) {
            Toast.makeText(getApplicationContext(), "Excersice Already Exists", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new ExcersiceDetailData(newName, newRepeat);
    }

    private void addingExcersiceToArray(ExcersiceDetailData newExcersiceDetail) {
        this.excersiceDetails.add(newExcersiceDetail);
    }

    private void initExcersiceList() {

        addExcersiceNumberPicker = (NumberPicker) findViewById(R.id.excersiceRepeatNumberPicker);
        addExcersiceNumberPicker.setMinValue(1);
        addExcersiceNumberPicker.setMaxValue(20);
        addExcersiceNumberPicker.setWrapSelectorWheel(false);

        String previous = getIntent().getExtras().getString("com.es.snapgym.PREVIOUS_TRAIN_TABLE_NAME");
        String currentTableName = getIntent().getExtras().getString("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME");
        this.previousDbExcersiceData = new DBExcersiceData(this, previous);
        this.currentDbExcersiceData = new DBExcersiceData(this, currentTableName);

        loadExcersices();

        Button saveButton = (Button) findViewById(R.id.saveExcersicesButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCurrentExcersices();
            }
        });
        Button loadButton = (Button) findViewById(R.id.loadExcersicesButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadExcersices();
            }
        });
    }

    private void updatingExcersiceList() {

        ExcersiceListAdapter excersiceListAdapter = new ExcersiceListAdapter(this, excersiceDetails);

        excersiceListView = (ListView) findViewById(R.id.excersicesListView);
        excersiceListView.setAdapter(excersiceListAdapter);

        excersiceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!modeAdding) {
                    swichExcersices(taggedExcersiceIndex, position);
                    modeAdding = true;
                    addingElementsChangingVisible(View.INVISIBLE);
                    removingElementsChangingVisible(View.VISIBLE);
                    updatingExcersiceList();
                    return;
                }

                saveCurrentExcersices();

                Intent reportExcersiceActivity = new Intent(getApplicationContext(), SetReportActivity.class);

                String excersiceName = excersiceDetails.get(position).getName();

                reportExcersiceActivity.putExtra("com.es.snapgym.EXCERSICE_NAME", excersiceName);
                reportExcersiceActivity.putExtra("com.es.snapgym.EXCERSICE_REPEAT", "" + excersiceDetails.get(position).getRepeat());

                excersiceName = excersiceName.replace(" ", "_" );

                reportExcersiceActivity.putExtra("com.es.snapgym.PREVIOUS_SETS_TABLE_NAME", previousDbExcersiceData.getTableName() + "_" + excersiceName);
                reportExcersiceActivity.putExtra("com.es.snapgym.CURRENT_SETS_TABLE_NAME", currentDbExcersiceData.getTableName() + "_" + excersiceName);

                startActivity(reportExcersiceActivity);
            }
        });

        excersiceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                if (!modeAdding)
                    return false;

                taggedExcersiceIndex = position;

                longClicking();

                return true;

            }
        });
    }

    private void initTimeAndLocation(){
        String location = getIntent().getExtras().getString("com.es.snapgym.LOCATION");
        String date = getIntent().getExtras().getString("com.es.snapgym.DATE");

        TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
        TextView dateTextView = (TextView) findViewById(R.id.excersiceTextView);

        locationTextView.setText(location);
        dateTextView.setText(date);
    }

    private void saveCurrentExcersices() {
        this.currentDbExcersiceData.cleanTable();
        for (int excersiceIndex = 0; excersiceIndex < this.excersiceDetails.size(); excersiceIndex++)
            this.currentDbExcersiceData.addRoundDeleteIfExist(this.excersiceDetails.get(excersiceIndex));
    }

    private void loadExcersices() {
        boolean isNew = getIntent().getExtras().getBoolean("com.es.snapgym.IS_NEW");
        if (isNew)
            this.excersiceDetails = this.previousDbExcersiceData.loadExcersices();
        else
            this.excersiceDetails = this.currentDbExcersiceData.loadExcersices();

        saveCurrentExcersices();
        updatingExcersiceList();
    }

    private boolean isExcersiceExist(String name){
        for(ExcersiceDetailData excersiceDetailData : this.excersiceDetails )
            if (excersiceDetailData.getName().equals(name))
                return true;
        return false;
    }

    private void longClicking(){
        modeAdding = false;
        addingElementsChangingVisible(View.VISIBLE);
        removingElementsChangingVisible(View.INVISIBLE);

    }

    private void addingElementsChangingVisible(int visibility){
        Button deletingExcersice = (Button) findViewById(R.id.removingExcersiceButton);
        deletingExcersice.setVisibility(visibility);
        Button editRhythm = (Button) findViewById(R.id.editRhythmButton);
        editRhythm.setVisibility(visibility);
    }

    private void removingElementsChangingVisible(int visibility){
        Button loadButton = (Button) findViewById(R.id.loadExcersicesButton);
        Button saveButton = (Button) findViewById(R.id.saveExcersicesButton);
        Button addExcersiceButton = (Button) findViewById(R.id.addingExcersiceButton);
        loadButton.setVisibility(visibility);
        saveButton.setVisibility(visibility);
        addExcersiceButton.setVisibility(visibility);

        TextView excersiceName = (TextView) findViewById(R.id.newExcersiceEditView);
        excersiceName.setVisibility(visibility);
        addExcersiceNumberPicker.setVisibility(visibility);

    }

    private void swichExcersices(int first, int second) {
        ExcersiceDetailData temp = excersiceDetails.get(first);
        excersiceDetails.set(first, excersiceDetails.get(second));
        excersiceDetails.set(second, temp);
    }

}
