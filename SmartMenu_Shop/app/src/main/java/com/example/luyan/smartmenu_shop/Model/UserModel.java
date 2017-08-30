package com.example.luyan.smartmenu_shop.Model;

import android.content.Context;

import com.example.luyan.smartmenu_shop.Common.ServerConfig;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Metadata.USERINFO;
import com.example.luyan.smartmenu_shop.Metadata.USERITEM;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpHelper;
import com.google.gson.Gson;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by luyan on 20/07/2017.
 */

public class UserModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static UserModel instance = null;
    private static USERINFO userinfo = null;

    /* 私有构造方法，防止被实例化 */
    private UserModel() {
    }

    /*加上synchronized，但是每次调用实例时都会加载**/
    public static UserModel getInstance() {
        synchronized (UserModel.class) {
            if (instance == null) {
                instance = new UserModel();
            }
        }
        return instance;
    }

    public void login(Context context, USERITEM useritem, ZHHttpCallBack httpCallBack){
        StringEntity stringEntity = new StringEntity(new Gson().toJson(useritem), "UTF-8");
        ZHHttpHelper.getInstance().post(context, ServerConfig.HTTP + "login", stringEntity, httpCallBack);
    }

    public static USERINFO getUserinfo() {
        return userinfo;
    }

    public static void setUserinfo(USERINFO userinfo) {
        UserModel.userinfo = userinfo;
    }
}
