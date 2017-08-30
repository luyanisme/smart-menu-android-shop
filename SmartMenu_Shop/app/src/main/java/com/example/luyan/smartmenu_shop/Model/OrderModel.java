package com.example.luyan.smartmenu_shop.Model;

import android.content.Context;

import com.example.luyan.smartmenu_shop.Common.ServerConfig;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpHelper;
import com.example.luyan.smartmenu_shop.Metadata.ORDEREDITEM;
import com.google.gson.Gson;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by luyan on 20/06/2017.
 */

public class OrderModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static OrderModel instance = null;
    private int index = 1;//页码
    private int pageSize = 10;

    /* 私有构造方法，防止被实例化 */
    private OrderModel() {
    }

    /*加上synchronized，但是每次调用实例时都会加载**/
    public static OrderModel getInstance() {
        synchronized (OrderModel.class) {
            if (instance == null) {
                instance = new OrderModel();
            }
        }
        return instance;
    }

    public void postOrderlist(Context context, ORDERITEM orderitem, ZHHttpCallBack httpCallBack){
        StringEntity stringEntity = new StringEntity(new Gson().toJson(orderitem), "UTF-8");
        ZHHttpHelper.getInstance().post(context, ServerConfig.HTTP + "postOrderedList", stringEntity, httpCallBack);
    }

    public void goNextPage(ZHHttpCallBack httpCallBack) {
        index ++;
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getOrders?shopId="+UserModel.getInstance().getUserinfo().getShopId()+"&page=" + index + "&pageSize=" + pageSize, null, httpCallBack);
    }

    public void firstPage(ZHHttpCallBack httpCallBack) {
        index = 1;
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getOrders?shopId="+UserModel.getInstance().getUserinfo().getShopId()+"&page=" + index + "&pageSize=" + pageSize, null, httpCallBack);
    }

    public void getOrdered(ZHHttpCallBack httpCallBack, int deskId) {
        index = 1;
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getOrdered?shopId="+UserModel.getInstance().getUserinfo().getShopId()+"&deskId="+ deskId, null, httpCallBack);
    }

    public void goNextAllOrderedPage(ZHHttpCallBack httpCallBack, String startDate, String endDate) {
        index ++;
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getAllOrdered?shopId="+UserModel.getInstance().getUserinfo().getShopId()+
                "&page=" + index +
                "&pageSize=" + pageSize +
                "&startDate=" + startDate +
                "&endDate=" + endDate, null, httpCallBack);
    }

    public void firstAllOrderedPage(ZHHttpCallBack httpCallBack, String startDate, String endDate) {
        index = 1;
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getAllOrdered?shopId="+UserModel.getInstance().getUserinfo().getShopId()+
                "&page=" + index +
                "&pageSize=" + pageSize +
                "&startDate=" + startDate +
                "&endDate=" + endDate, null, httpCallBack);    }
}
