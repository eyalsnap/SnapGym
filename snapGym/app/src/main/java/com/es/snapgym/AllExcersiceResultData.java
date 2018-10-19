package com.es.snapgym;

/**
 * Created by EYAL on 12/10/2018.
 */

public class AllExcersiceResultData {

    private final SetDetailData [] allPreviousSets;
    private final SetDetailData [] allCurrentSets;
    private final DBSetData previousTable;
    private final DBSetData currentTable;
    private int round;

    public AllExcersiceResultData(DBSetData previousTable, DBSetData currentTable, int numberOfSets) {

        this.previousTable = previousTable;
        this.currentTable = currentTable;
        this.allPreviousSets = new SetDetailData[numberOfSets];
        this.allCurrentSets = new SetDetailData[numberOfSets];
        ExcersiceResultFactory.extractAllExcersiceResult(this.allPreviousSets, this.previousTable);
        ExcersiceResultFactory.extractAllExcersiceResult(this.allCurrentSets, this.currentTable);
        copyingPreviousTrainToCurrent();

    }

    private void copyingPreviousTrainToCurrent(){
        for (int setIndex =0; setIndex<allCurrentSets.length; setIndex++)
            if (allCurrentSets[setIndex] == null)
                allCurrentSets[setIndex] = allPreviousSets[setIndex];
    }

    public SetDetailData getCurrentSet(int round) {
        return this.allCurrentSets[round-1];
    }

    public SetDetailData getPreviousSetResult(int round) {
        return this.allPreviousSets[round-1];
    }

    public void updateRound(SetDetailData sdd, int round) {
        this.currentTable.addRound(sdd);
        this.allCurrentSets[round-1] = sdd;
    }

}
