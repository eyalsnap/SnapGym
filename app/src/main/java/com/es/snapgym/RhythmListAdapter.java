package com.es.snapgym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by EYAL on 24/09/2018.
 */

public class RhythmListAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final LinkedList<RhythmLine> rhythmLinesList;

    public RhythmListAdapter(Context c, LinkedList<RhythmLine> rhythms) {
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rhythmLinesList = rhythms;
    }

    @Override
    public int getCount() {
        return this.rhythmLinesList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.rhythmLinesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RhythmLine currentRhythm = this.rhythmLinesList.get(position);

        View view = this.mInflater.inflate(R.layout.rhythm_performence, null);
        TextView roundTextView = (TextView) view.findViewById(R.id.rhythmRoundTextView);
        NumberPicker timeNumberPicker = (NumberPicker) view.findViewById(R.id.rhythmRoundTimeNumberPicker);

        roundTextView.setText("" + currentRhythm.getRound());
        timeNumberPicker.setValue(currentRhythm.getSeconds());

        timeNumberPicker.setMinValue(1);
        timeNumberPicker.setMaxValue(30);
        timeNumberPicker.setWrapSelectorWheel(false);

        return view;

    }
}
