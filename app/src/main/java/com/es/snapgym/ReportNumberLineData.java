package com.es.snapgym;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by EYAL on 26/09/2018.
 */

public class ReportNumberLineData {

    protected final TextView textView;
    protected final NumberPicker numberPicker;

    public ReportNumberLineData(View theTextView, View theNumberPicker, boolean initState) {

        this.textView = (TextView) theTextView;
        this.numberPicker = (NumberPicker) theNumberPicker;

        this.numberPicker.setMinValue(1);
        this.numberPicker.setMaxValue(30);
        this.numberPicker.setWrapSelectorWheel(false);
        this.numberPicker.setEnabled(initState);

    }

    public void setValue(int value){
        this.numberPicker.setValue(value);
    }

    public int getNumber() {

        return this.numberPicker.getValue();

    }

}
