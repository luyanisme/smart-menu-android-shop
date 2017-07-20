package com.example.luyan.smartmenu_shop.Utils;

import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEPROPERTYITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASESTANDARDITEM;

import java.util.ArrayList;

/**
 * Created by luyan on 15/06/2017.
 */

public class ArrayUtils {

    public static CASEITEM findCaseId(CASEITEM caseitem, ArrayList<CASEITEM> caseitems) {
        for (int i = 0; i < caseitems.size(); i++) {
            if (caseitem.getCaseId() == caseitems.get(i).getCaseId()) {
                return caseitems.get(i);
            }
        }
        return null;
    }

    public static CASESTANDARDITEM findStandardId(long id, ArrayList<CASESTANDARDITEM> casestandarditems) {
        for (int i = 0; i < casestandarditems.size(); i++) {
            if (id == casestandarditems.get(i).getId()) {
                return casestandarditems.get(i);
            }
        }
        return null;
    }

    public static CASEPROPERTYITEM findPropertyId(long id, ArrayList<CASEPROPERTYITEM> casepropertyitems) {
        for (int i = 0; i < casepropertyitems.size(); i++) {
            if (id == casepropertyitems.get(i).getId()) {
                return casepropertyitems.get(i);
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
