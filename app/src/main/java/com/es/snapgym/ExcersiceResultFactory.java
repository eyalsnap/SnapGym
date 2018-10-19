package com.es.snapgym;

import java.util.LinkedList;

/**
 * Created by EYAL on 13/10/2018.
 */

public abstract class ExcersiceResultFactory {

    public static void extractAllExcersiceResult(SetDetailData [] allSets, DBSetData previousTable) {

        LinkedList<SetDetailData> allSetsInList = previousTable.getAllSets();
        for (int setIndex = 0; setIndex<allSets.length; setIndex++)
            allSets[setIndex] = findSetByRound(allSetsInList, setIndex + 1);

    }

    private static SetDetailData findSetByRound(LinkedList<SetDetailData> allSetsInList, int setNumber) {

        for (int setIndex = 0; setIndex < allSetsInList.size(); setIndex++)
            if (allSetsInList.get(setIndex).getRound() == setNumber )
                return allSetsInList.get(setIndex);
        return null;

    }

}
