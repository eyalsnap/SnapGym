package com.es.snapgym;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by EYAL on 24/09/2018.
 */

public class WatchListAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final LinkedList<AllExcersiceResultData> watchList;
    private final Context context;

    public WatchListAdapter(Context c, LinkedList<AllExcersiceResultData> excersiceList) {
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.watchList = excersiceList;
        this.context = c;
    }

    @Override
    public int getCount() {
        return this.watchList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.watchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = this.mInflater.inflate(R.layout.recycle_view_layout, null);

        AllExcersiceResultData currentResult = watchList.get(position);

        TextView nameTextView = (TextView) view.findViewById(R.id.watchExNameTextView);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);

        nameTextView.setText(currentResult.getName());

        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this.context, currentResult.allCurrentSets);
        recyclerView.setAdapter(adapter);

        return view;

    }
}
