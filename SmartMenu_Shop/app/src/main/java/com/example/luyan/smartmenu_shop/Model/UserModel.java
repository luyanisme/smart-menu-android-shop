package com.example.luyan.smartmenu_shop.Model;

/**
 * Created by luyan on 20/07/2017.
 */

public class UserModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static UserModel instance = null;

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

    public int getShopId(){
        return 1;
    }
}
