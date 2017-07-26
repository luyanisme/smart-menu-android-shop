package com.example.luyan.smartmenu_shop.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by luyan on 2/17/16.
 */
public class IntentUtils {

    public static final String INTENT_STRING_PARAM = "string_param";
    public static final String INTENT_STRINGS_PARAM = "strings_param";
    public static final String INTENT_INT_PARAM = "int_param";
    public static final String INTENT_BUNDLE_PARAM = "bundle";

    /*启动activity*/
    public static void startToActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /*带字符串*/
    public static void startToActivityWithString(Context context, Class cls, String info) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(INTENT_STRING_PARAM, info);
        context.startActivity(intent);
    }

    /*带字符串数组*/
    public static void startToActivityWithStrings(Context context, Class cls, String[] infos) {
        Intent intent = new Intent(context, cls);
        for (int i = 0; i < infos.length; i++) {
            intent.putExtra(INTENT_STRINGS_PARAM+"i", infos[i]);
        }
        context.startActivity(intent);
    }

    /*带整型*/
    public static void startToActivityWithInt(Context context, Class cls, int info) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(INTENT_INT_PARAM, info);
        context.startActivity(intent);
    }

    /*启动activity*/
    public static void startToActivityWithBundle(Context context, Class cls, Bundle data) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(INTENT_BUNDLE_PARAM, data);
        context.startActivity(intent);
    }


}
