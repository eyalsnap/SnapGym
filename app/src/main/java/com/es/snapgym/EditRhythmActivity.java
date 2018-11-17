package com.es.snapgym;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.LinkedList;

public class EditRhythmActivity extends AppCompatActivity {

    private ExcersiceDetailData excersiceDetailData;

    private NumberPicker beforeNumberPicker;

    private DBExcersiceData dbExcersiceData;

    private NumberPicker basicNumberPicker;
    private float numberPickersWidth;
    private LinkedList<NumberPicker> numberPickersLst;
    private int numberOfTimes = 1;

    private int scrrenWidth;

    private float numberPickerWidth = 192.0f;

    ConstraintLayout constraintLayout;
    LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rhythm);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        excersiceDetailData = getIntent().getParcelableExtra("com.es.snapgym.EXCERSICE_OBJECT");
        String curr_rhytm = getIntent().getExtras().getString("com.es.snapgym.EXCERSICE_RHYTHM_OBJECT");
        if (!"rhythm".equals(curr_rhytm))
            excersiceDetailData.setRhythm(new RhythmClassReal(curr_rhytm));

        TextView excersiceTextView = (TextView) findViewById(R.id.editRhythmExNameTextView);
        excersiceTextView.setText(excersiceDetailData.getName());

        dbExcersiceData = new DBExcersiceData(this, getIntent().getExtras().getString("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME"));

        initBeforeSetNumberPicker();
        initNumberPickerVariables();

        Button addRhythm = (Button) findViewById(R.id.addRhythmButton);
        addRhythm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingTime();
            }
        });

        Button removeRhythm = (Button) findViewById(R.id.removeRhythmButton);
        removeRhythm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removingTime();
            }
        });

        Button finishRhythm = (Button) findViewById(R.id.finishRhytmButton);
        finishRhythm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent trainIntent = new Intent(getApplicationContext(), TrainDetailsActivity.class);

                trainIntent.putExtra("com.es.snapgym.LOCATION", getIntent().getExtras().getString("com.es.snapgym.LOCATION"));
                trainIntent.putExtra("com.es.snapgym.TYPE", getIntent().getExtras().getString("com.es.snapgym.TYPE"));
                trainIntent.putExtra("com.es.snapgym.DATE", getIntent().getExtras().getString("com.es.snapgym.DATE"));
                trainIntent.putExtra("com.es.snapgym.PREVIOUS_TRAIN_TABLE_NAME", getIntent().getExtras().getString("com.es.snapgym.PREVIOUS_TRAIN_TABLE_NAME"));
                trainIntent.putExtra("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME", getIntent().getExtras().getString("com.es.snapgym.CURRENT_TRAIN_TABLE_NAME"));
                trainIntent.putExtra("com.es.snapgym.IS_NEW", false);

                dbExcersiceData.addRoundDeleteIfExist(new ExcersiceDetailData(excersiceDetailData.getName(), excersiceDetailData.getRepeat(), numberPickersLst, beforeNumberPicker.getValue()));

                startActivity(trainIntent);
            }
        });

    }

    private void addingTime(){
        if (numberOfTimes > 3)
            return;

        NumberPicker newTime = createOneNumberPicker();

        numberPickersLst.add(newTime);
        numberOfTimes++;

        constraintLayout.addView(newTime);

        updateLocations();
    }

    private void initNumberPickerVariables(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        scrrenWidth = size.x;

        constraintLayout = (ConstraintLayout) findViewById(R.id.rl);

        // Create a LayoutParams for TextView
        layoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, // Width of TextView
                LayoutParams.WRAP_CONTENT); // Height of TextView

        basicNumberPicker = (NumberPicker) findViewById(R.id.basicRhytmNumberPicker);

        initNumberPickerLst();

    }

    private void initNumberPickerLst() {

        numberPickersWidth = basicNumberPicker.getWidth();

        numberPickersLst = new LinkedList<>();

        basicNumberPicker.setMinValue(1);
        basicNumberPicker.setMaxValue(12);
        basicNumberPicker.setValue(0);
        basicNumberPicker.setWrapSelectorWheel(false);
        numberPickersLst.add(basicNumberPicker);

        LinkedList<Integer> timesLst = excersiceDetailData.getRhythmTimes();
        if (timesLst != null){
            numberOfTimes = timesLst.size();

            basicNumberPicker.setValue(timesLst.get(0));

            for (int timeIndex = 1; timeIndex < numberOfTimes; timeIndex++) {
                NumberPicker newTime = createOneNumberPicker();
                newTime.setValue(timesLst.get(timeIndex));

                numberPickersLst.add(newTime);
                constraintLayout.addView(newTime);

            }
        }

        updateLocations();

    }

    private NumberPicker createOneNumberPicker() {
        NumberPicker newTime = new NumberPicker(getApplicationContext());
        newTime.setLayoutParams(layoutParams);
        newTime.setMinValue(0);
        newTime.setMaxValue(12);
        newTime.setWrapSelectorWheel(false);
        newTime.setScaleX(0.75f);
        newTime.setScaleY(1f);
        newTime.setY(631.0f);
        return newTime;
    }

    private void updateLocations(){

        float space = ( scrrenWidth - numberOfTimes * numberPickerWidth ) / ( numberOfTimes + 1 );
        for (int numberPickerIndex = 0; numberPickerIndex < numberOfTimes; numberPickerIndex++) {
            NumberPicker numberPicker = numberPickersLst.get(numberPickerIndex);
            float currentX = (numberPickerIndex * numberPickerWidth) + (numberPickerIndex + 1) * space;
            numberPicker.setX(currentX);
        }
    }

    private void removingTime(){
        if (numberOfTimes < 2)
            return;

        constraintLayout.removeView(numberPickersLst.removeLast());
        numberOfTimes--;

        updateLocations();
    }

    private void initBeforeSetNumberPicker(){
        beforeNumberPicker = (NumberPicker) findViewById(R.id.timeBeforeNumberPicker);
        beforeNumberPicker.setMinValue(0);
        beforeNumberPicker.setMaxValue(12);
        beforeNumberPicker.setValue(this.excersiceDetailData.getPrepareTime());
        beforeNumberPicker.setWrapSelectorWheel(false);

    }

}
