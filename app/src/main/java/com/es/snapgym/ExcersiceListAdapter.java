package com.es.snapgym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by EYAL on 24/09/2018.
 */

public class ExcersiceListAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final LinkedList<ExcersiceDetailData> excersiceList;

    public ExcersiceListAdapter(Context c, LinkedList<ExcersiceDetailData> excersiceList) {
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.excersiceList = excersiceList;
    }

    @Override
    public int getCount() {
        return this.excersiceList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.excersiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExcersiceDetailData currentExcersice = this.excersiceList.get(position);

        View view = this.mInflater.inflate(R.layout.excersice_performence, null);
        TextView nameTextView = (TextView) view.findViewById(R.id.excersiceTextView);
        TextView repeatTextView = (TextView) view.findViewById(R.id.locationTextView);
        TextView rhythmTextView = (TextView) view.findViewById(R.id.rhythmTextView);

        nameTextView.setText(currentExcersice.getName());
        repeatTextView.setText("" + currentExcersice.getRepeat());
        rhythmTextView.setText("" + currentExcersice.getRhythm());

        return view;

    }
}
