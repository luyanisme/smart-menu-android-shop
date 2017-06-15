package com.example.luyan.smartmenu_shop.Utils;

import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;

import java.util.ArrayList;

/**
 * Created by luyan on 15/06/2017.
 */

public class ArrayUtils {

    public static CASEITEM isContainCaseId(CASEITEM caseitem, ArrayList<CASEITEM> caseitems) {
        for (int i = 0; i < caseitems.size(); i++) {
            if (caseitem.getCaseId() == caseitems.get(i).getCaseId()) {
                return caseitems.get(i);
            }
        }
        return null;
    }

    public static CASEITEM findStandardCaseItem(String standardStr, ArrayList<CASEITEM> caseitems) {

        for (int i = 0; i < caseitems.size(); i++) {
            if (standardStr.equals(caseitems.get(i).getStandardDesc())){
             return caseitems.get(i);
            }
        }
        return null;
    }

}
