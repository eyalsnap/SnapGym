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

public class TrainListAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final LinkedList<TrainDetailData> trainsList;

    public TrainListAdapter(Context c, LinkedList<TrainDetailData> excersiceList) {
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.trainsList = excersiceList;
    }

    @Override
    public int getCount() {
        return this.trainsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.trainsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrainDetailData currentTrain = this.trainsList.get(position);

        View view = this.mInflater.inflate(R.layout.train_performence, null);

        TextView dateTextView = (TextView) view.findViewById(R.id.trainDateTextView);
        TextView typeTextView = (TextView) view.findViewById(R.id.typeTextView);
        TextView locationTextView = (TextView) view.findViewById(R.id.locationTextView);

        dateTextView.setText(currentTrain.getDateToShow());
        typeTextView.setText(currentTrain.getTrainType());
        locationTextView.setText(currentTrain.getLocation());

        return view;
    }
}
