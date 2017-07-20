package com.example.luyan.smartmenu_shop.Utils.GreenDaoUtils;


import com.example.luyan.smartmenu_shop.GreenDao.DaoSession;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by luyan on 10/19/16.
 */

public class GreenDaoUtils {

    /*插入数据*/
    public static boolean insertObject(Object object) {
        boolean flag = false;
        try {
            GreenDaoManager.getInstance().getNewSession().insert(object);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*批量插入数据*/
    public static boolean insertObjects(final List<Object> objects) {
        boolean flag = false;
        try {
            GreenDaoManager.getInstance().getNewSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Object object : objects) {
                        GreenDaoManager.getInstance().getNewSession().insertOrReplace(object);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*查找数据*/
    public static Object queryObject(Class clazz,WhereCondition cond, WhereCondition... condMore) {
        Object object = GreenDaoManager.getInstance().getNewSession().queryBuilder(clazz).where(cond, condMore).build().unique();
        return object;
    }

    /*批量查询数据*/
    public static List<Object> queryObjects(Class clazz,WhereCondition cond, WhereCondition... condMore) {
        List<Object> objects;
        if (cond == null) {
            objects = GreenDaoManager.getInstance().getNewSession().queryBuilder(clazz).build().list();
        } else {
            objects = GreenDaoManager.getInstance().getNewSession().queryBuilder(clazz).where(cond, condMore).build().list();
        }
        return objects;
    }

    /*更新数据*/
    public static boolean updateObject(Object object) {
        boolean flag = false;
        try {
            GreenDaoManager.getInstance().getNewSession().update(object);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*删除数据*/
    public static boolean deleteObject(Object object) {
        boolean flag = false;
        try {
            GreenDaoManager.getInstance().getNewSession().delete(object);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*按条件删除数据*/
    public static boolean deleteWithQuery(Class clazz,WhereCondition cond, WhereCondition... condMore){
        boolean flag = false;
        try {
            GreenDaoManager.getInstance().getNewSession().queryBuilder(clazz).where(cond, condMore).buildDelete().executeDeleteWithoutDetachingEntities();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static DaoSession getNewSession() {
        return GreenDaoManager.getInstance().getNewSession();
    }
}
