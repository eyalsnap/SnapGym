package com.es.snapgym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

public class EditRhythmActivity extends AppCompatActivity {

    private ListView rhythmListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rhythm);
        ExcersiceDetailData excersiceDetailData = getIntent().getParcelableExtra("com.es.snapgym.EXCERSICE_OBJECT");

        TextView excersiceTextView = (TextView) findViewById(R.id.editRhythmExNameTextView);
        excersiceTextView.setText(excersiceDetailData.getName());

        RhythmListAdapter rhythmList = createRhythmListView();

        rhythmListView = (ListView) findViewById(R.id.rhythmLinesListView);
        rhythmListView.setAdapter(rhythmList);

    }

    private RhythmListAdapter createRhythmListView(){
        LinkedList<RhythmLine> rll = new LinkedList<>();

        RhythmLine rl1 = new RhythmLine(1,4);
        RhythmLine rl2 = new RhythmLine(2,2);

        rll.add(rl1);
        rll.add(rl2);

        return  new RhythmListAdapter(this, rll);
    }
}
