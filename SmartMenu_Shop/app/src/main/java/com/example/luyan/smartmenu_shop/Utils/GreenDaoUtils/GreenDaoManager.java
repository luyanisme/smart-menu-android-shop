package com.example.luyan.smartmenu_shop.Utils.GreenDaoUtils;


import com.example.luyan.smartmenu_shop.Common.MyApplication;
import com.example.luyan.smartmenu_shop.GreenDao.DaoMaster;
import com.example.luyan.smartmenu_shop.GreenDao.DaoSession;

/**
 * Created by luyan on 2016/7/12.
 */
public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;

    public GreenDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "sm-db", null);
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getNewSession() {
        return mDaoMaster.newSession();
    }

}