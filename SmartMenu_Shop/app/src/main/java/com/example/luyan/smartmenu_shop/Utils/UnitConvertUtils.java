package com.example.luyan.smartmenu_shop.Utils;

import android.content.Context;

/**
 * Created by luyan on 5/6/16.
 */
public class UnitConvertUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
