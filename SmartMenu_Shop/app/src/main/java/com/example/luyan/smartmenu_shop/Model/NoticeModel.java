package com.example.luyan.smartmenu_shop.Model;

import android.content.Context;

import com.example.luyan.smartmenu_shop.Common.ServerConfig;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpHelper;
import com.google.gson.Gson;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by luyan on 20/07/2017.
 */

public class NoticeModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static NoticeModel instance = null;
    private int index = 1;//页码
    private int pageSize = 10;

    /* 私有构造方法，防止被实例化 */
    private NoticeModel() {
    }

    /*加上synchronized，但是每次调用实例时都会加载**/
    public static NoticeModel getInstance() {
        synchronized (NoticeModel.class) {
            if (instance == null) {
                instance = new NoticeModel();
            }
        }
        return instance;
    }

    public void goNextPage(ZHHttpCallBack httpCallBack) {
        index ++;
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getNotices?shopId="+UserModel.getInstance().getShopId()+"&page=" + index + "&pageSize=" + pageSize, null, httpCallBack);
    }

    public void firstPage(ZHHttpCallBack httpCallBack) {
        index = 1;
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getNotices?shopId="+UserModel.getInstance().getShopId()+"&page=" + index + "&pageSize=" + pageSize, null, httpCallBack);
    }
}
