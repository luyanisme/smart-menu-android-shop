package com.example.luyan.smartmenu_shop.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by luyan on 7/22/16.
 */
public class ActivityCollectorUtils {

    public static ArrayList<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);

    }

    public static void finishAll() {
        Collections.reverse(activities);
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

        activities.clear();
    }

    /*pop至指定的页面*/
    public static void popToActivity(String tarActivity){
        for (Activity activity: activities) {
            activity.finish();
            if (activity.getLocalClassName().equals(tarActivity)){
                break;
            }
        }
    }

    /*倒数第index个*/
    public static String getActivityName(Integer index){
        return activities.get(activities.size() - index - 1).getLocalClassName();
    }

    /*向前pop一个页面*/
    public static void pop(){
        if (activities.size() == 0){
            return;
        }
        activities.get(activities.size() - 1).finish();
        activities.remove(activities.size() - 1);
    }

    /*向前pop页面的个数*/
    public static void popNums(int nums){
        for (int i = 0; i < nums; i++) {
            if (!activities.get(activities.size() - i - 1).isFinishing()) {
                activities.get(activities.size() - i - 1).finish();
            }
        }
        for (int i = 0; i < nums; i++) {
            activities.remove(activities.size() - 1);
        }

    }

    public static void removeActivity(String activityName){
        int index = -1;
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getLocalClassName().equals(activityName)){
                index = i;
            }
        }
        if (index != -1){
            activities.get(index).finish();
            activities.remove(index);
        }
    }
}
