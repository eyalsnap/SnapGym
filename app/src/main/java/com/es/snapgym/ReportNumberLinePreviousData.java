package com.es.snapgym;

import android.view.View;

/**
 * Created by EYAL on 14/10/2018.
 */

public class ReportNumberLinePreviousData extends ReportNumberLineData {

    public ReportNumberLinePreviousData(View theTextView, View theNumberPicker, boolean initState, boolean isWatching) {

        super(theTextView, theNumberPicker, initState);

        if (isWatching)
            disapearPrevious();

    }

    private void disapearPrevious(){
        this.numberPicker.setVisibility(View.INVISIBLE);
        this.textView.setVisibility(View.INVISIBLE);
    }

}
