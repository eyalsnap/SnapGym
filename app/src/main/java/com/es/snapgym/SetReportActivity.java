package com.es.snapgym;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetReportActivity extends AppCompatActivity {

    private ReportNumberLineData targetReport;
    private ReportNumberLineData resultReport;
    private ReportNumberLineData previousReport;

    private EditText weightTextView;

    private TextView reportExcersiceNameTextView;
    private TextView reportExcersiceRepeatTextView;

    private String reportExcersiceName;
    private int maxRound;

    private DBSetData previousExcersiceResult;
    private DBSetData currentExcersiceResult;
    private AllExcersiceResultData resultsData;
    private int round = 1;

    private String previousTableName;
    private String currentTableName;

    private RhythmAbstractClass rhythm;

    SoundRhythm soundRhythm;
    Button soundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersice_report);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        initObjectsAndValues();

        createButtons();

        initSoundButton();

        tmpSwipe();

    }

    private GestureDetectorCompat gestureDetectorCompat = null;
    private void tmpSwipe() {
        // Create a common gesture listener object.
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();

        // Set activity in the listener.
        gestureListener.setActivity(this);

        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass activity on touch event to the gesture detector.
        gestureDetectorCompat.onTouchEvent(event);
        // Return true to tell android OS that event has been consumed, do not pass it to other event listeners.
        return true;
    }

    private void initSoundButton() {

        soundButton = (Button) findViewById(R.id.soundButton);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            soundRhythm = new SoundRhythm(rhythm, targetReport.getNumber(), getApplicationContext(), getScreenWindow(), soundButton);
                updateScreenWindow();
                if (!soundRhythm.isPlay()) {
                    soundRhythm.run();
                }
                else {
                    soundRhythm.stopPlay();
                }
            }
        });
    }

    private TextView getScreenWindow(){

        TextView textView = (TextView) findViewById(R.id.screenTextView);
        textView.setVisibility(View.INVISIBLE);
        return textView;
    }

    private void updateScreenWindow(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int scrrenWidth = size.x;

        TextView textView = (TextView) findViewById(R.id.screenTextView);

        textView.setX(0);
        textView.setY(0);
        textView.setWidth(scrrenWidth);
        textView.setHeight((int)soundButton.getY());
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.GREEN);
        textView.setText("0");
        textView.setTextSize(100);

    }

    private void createButtons() {
        createSpecificButton(-1, R.id.backRoundButton);
        createSpecificButton(1, R.id.nextRoundButton);
        createSpecificButton(0, R.id.saveRoundButton);
    }

    private void createSpecificButton(final int roundChange, int buttonId) {
        Button backButton = (Button) findViewById(buttonId);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDB();
                round+=roundChange;
                updatePage();
            }
        });
    }

    private void initObjectsAndValues() {

        readingTablesName();
        initPage();
        updatePage();

    }

    private void updatePage() {
        updateRoundTextViews();
        updateResults();
    }

    private void initPage() {
        initRoundTextViews();
        readingTables();
        initReportNumbers();
    }

    private void initReportNumbers() {
        this.weightTextView = (EditText) findViewById(R.id.reportExcersiceWeightTextView);
        this.targetReport = new ReportNumberLineChangebleData(findViewById(R.id.targetTextView), findViewById(R.id.targetNumberPicker), false, false);
        this.resultReport = new ReportNumberLineChangebleData(findViewById(R.id.resultTextView), findViewById(R.id.resultNumberPicker), true, false);
        this.previousReport = new ReportNumberLinePreviousData(findViewById(R.id.previousTextView), findViewById(R.id.previousNumberPicker), false, false);
        this.reportExcersiceNameTextView.setText(this.reportExcersiceName);
    }

    private void initRoundTextViews() {
        this.reportExcersiceNameTextView = (TextView) findViewById(R.id.reportExcersiceNameTextView);
        this.reportExcersiceName = getIntent().getExtras().getString("com.es.snapgym.EXCERSICE_NAME");
        this.reportExcersiceRepeatTextView = (TextView) findViewById(R.id.reportExcersiceRepeatTextView);
        this.maxRound = Integer.parseInt(getIntent().getExtras().getString("com.es.snapgym.EXCERSICE_REPEAT"));
    }

    private void updateRoundTextViews() {
        this.round = Math.max(1,this.round);
        this.round = Math.min(this.round, this.maxRound);
        this.reportExcersiceRepeatTextView.setText(round + " of " + getIntent().getExtras().getString("com.es.snapgym.EXCERSICE_REPEAT"));
    }

    private void setReportDetail(SetDetailData reportDetail){
        this.weightTextView.setText(reportDetail.getWeight());
        this.targetReport.setValue(reportDetail.getTarget());
        this.resultReport.setValue(reportDetail.getRepeat());
    }

    private void updateDB() {

        SetDetailData sdd = createReportDetail();
        resultsData.updateRound(sdd, this.round);

    }

    private void updateResults(){
        SetDetailData rd = resultsData.getPreviousSetResult(this.round);
        if (rd != null)
            this.previousReport.setValue(rd.getRepeat());

        if (resultsData.getCurrentSet(this.round) != null)
            rd = resultsData.getCurrentSet(this.round);

        if (rd != null)
            setReportDetail(rd);

    }

    private SetDetailData createReportDetail() {
        String weight = weightTextView.getText().toString();
        return new SetDetailData(this.round, weight, this.resultReport.getNumber(), this.targetReport.getNumber());
    }

    private void readingTables() {
        this.previousExcersiceResult = new DBSetData(this, previousTableName);
        this.currentExcersiceResult = new DBSetData(this, currentTableName);
        this.resultsData = new AllExcersiceResultData(this.previousExcersiceResult, this.currentExcersiceResult, this.maxRound, this.reportExcersiceName);
    }

    private void readingTablesName() {
        this.previousTableName = getIntent().getExtras().getString("com.es.snapgym.PREVIOUS_SETS_TABLE_NAME");
        this.currentTableName = getIntent().getExtras().getString("com.es.snapgym.CURRENT_SETS_TABLE_NAME");
        String rhythmString = getIntent().getExtras().getString("com.es.snapgym.RHYTHM_STRING");
        if ("rhythm".equals(rhythmString))
            this.rhythm = new RhythmClassEmpty();
        else
            this.rhythm = new RhythmClassReal(rhythmString);
        TextView rhythmShowTextView = (TextView) findViewById(R.id.rhythmShowTextView);
        rhythmShowTextView.setText(rhythmString);
    }

    public void next(){
        updateDB();
        round+=1;
        updatePage();
    }

    public void back(){
        updateDB();
        round-=1;
        updatePage();
    }

}
