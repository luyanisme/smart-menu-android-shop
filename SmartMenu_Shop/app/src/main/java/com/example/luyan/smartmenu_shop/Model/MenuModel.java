package com.example.luyan.smartmenu_shop.Model;

import com.example.luyan.smartmenu_shop.Common.ServerConfig;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpHelper;

/**
 * Created by luyan on 23/06/2017.
 */

public class MenuModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static MenuModel instance = null;

    /* 私有构造方法，防止被实例化 */
    private MenuModel() {
    }

    /*加上synchronized，但是每次调用实例时都会加载**/
    public static MenuModel getInstance() {
        synchronized (OrderModel.class) {
            if (instance == null) {
                instance = new MenuModel();
            }
        }
        return instance;
    }

    public void getMenuList(ZHHttpCallBack httpCallBack){
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getMenu?shopId=1", null, httpCallBack);
    }

}
