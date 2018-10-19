package com.es.snapgym;

/**
 * Created by EYAL on 24/09/2018.
 */

public class ExcersiceDetailData extends DBDetailObject{

    private final String name;
//    private final int repeat;
    public int repeat;

    public ExcersiceDetailData(String name, int repeat) {
        this.name = name;
        this.repeat = repeat;
    }

    public String getName() {
        return this.name;
    }

    public int getRepeat() {
        return this.repeat;
    }

    @Override
    public String getIdObject() {
        return this.getName();
    }
}
