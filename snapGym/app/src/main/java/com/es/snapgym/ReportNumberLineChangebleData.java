package com.es.snapgym;

import android.view.View;

/**
 * Created by EYAL on 14/10/2018.
 */

public class ReportNumberLineChangebleData extends ReportNumberLineData {

    public ReportNumberLineChangebleData(View theTextView, View theNumberPicker, boolean initState, boolean isWatching) {

        super(theTextView, theNumberPicker, initState);

        if (isWatching)
            this.numberPicker.setEnabled(false);
        else {
            this.textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    numberPicker.setEnabled(!numberPicker.isEnabled());
                    return true;
                }
            });
        }
    }

}
