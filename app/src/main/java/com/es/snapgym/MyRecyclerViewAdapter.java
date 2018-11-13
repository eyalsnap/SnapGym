package com.es.snapgym;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by EYAL on 13/11/2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private SetDetailData[] sets;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, SetDetailData[] theSets) {
        this.mInflater = LayoutInflater.from(context);
        this.sets = theSets;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.excersice_watch_detail, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SetDetailData currentSet = sets[position];
        holder.roundTextView.setText(""+currentSet.getRound());
        holder.weightTextView.setText(""+currentSet.getWeight());
        holder.repeatTextView.setText(""+currentSet.getRepeat());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        int index = 0;
        while (index < this.sets.length) {
            if (this.sets[index] == null)
                return index;
            index++;
        }
        return this.sets.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView roundTextView, weightTextView, repeatTextView;

        ViewHolder(View itemView) {
            super(itemView);
            roundTextView = (TextView) itemView.findViewById(R.id.roundWatchTextView);
            weightTextView = (TextView) itemView.findViewById(R.id.watchRepeatTextView);
            repeatTextView = (TextView) itemView.findViewById(R.id.watchWeightTextView);
        }

    }

}
