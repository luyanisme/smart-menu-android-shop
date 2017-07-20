package com.example.luyan.smartmenu_shop.Widgt;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by luyan on 7/23/16.
 */
public class ToastWidgt {
    private static Toast mToast;

    public static void showWithInfo(Context context,String message,int duration){
        if (null != mToast) {
            mToast.setText(message);
        } else {
            mToast = Toast.makeText(context, message, duration);
        }
        mToast.show();
    }
}
