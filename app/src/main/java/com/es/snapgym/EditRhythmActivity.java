package com.es.snapgym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EditRhythmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rhythm);
        ExcersiceDetailData excersiceDetailData = getIntent().getParcelableExtra("com.es.snapgym.EXCERSICE_OBJECT");

        TextView excersiceTextView = (TextView) findViewById(R.id.editRhythmExNameTextView);
        excersiceTextView.setText(excersiceDetailData.getName());

    }
}
