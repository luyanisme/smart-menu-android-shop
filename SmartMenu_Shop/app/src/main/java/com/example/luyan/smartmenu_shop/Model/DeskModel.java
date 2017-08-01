package com.example.luyan.smartmenu_shop.Model;

import com.example.luyan.smartmenu_shop.Common.ServerConfig;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpHelper;

/**
 * Created by luyan on 31/07/2017.
 */

public class DeskModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static DeskModel instance = null;

    /* 私有构造方法，防止被实例化 */
    private DeskModel() {
    }

    /*加上synchronized，但是每次调用实例时都会加载**/
    public static DeskModel getInstance() {
        synchronized (DeskModel.class) {
            if (instance == null) {
                instance = new DeskModel();
            }
        }
        return instance;
    }

    public void changeDeskStatue(ZHHttpCallBack httpCallBack, int deskId, int deskStatue){
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "changeDeskStatue?deskId="+ deskId + "&deskStatue=" + deskStatue, null, httpCallBack);
    }

    public void getDesk(ZHHttpCallBack httpCallBack, int shopId){
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getDesk?shopId="+ shopId, null, httpCallBack);
    }
}
