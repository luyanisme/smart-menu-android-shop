package com.example.luyan.smartmenu_shop.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.luyan.smartmenu_shop.Fragment.Desk.DeskFragment;
import com.example.luyan.smartmenu_shop.Fragment.Mine.MineFragment;
import com.example.luyan.smartmenu_shop.Fragment.Main.MainFragment;
import com.example.luyan.smartmenu_shop.Metadata.TABBARITEMCONTENT;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Widgt.UITabbar;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements UITabbar.UITabBarDelegate {

    private static FragmentManager fragmentManager;

    private Fragment[] mFragments;
    private int prePos = 0;//上一个点击tabbar的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cleanBgOfStateBar();
        setContentView(R.layout.activity_main);

        initTabbar(this);
        initFragment();
        ActivityCollectorUtils.addActivity(MainActivity.this);
    }

    private void initTabbar(Context context) {
        //设置Tabbar
        UITabbar tabbar = new UITabbar(context);
        tabbar.setDelegate(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        tabbar.setLayoutParams(params);

        //设置灰色线条
        ImageView topLine = new ImageView(context);
        topLine.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        topLine.setLayoutParams(lineParams);

        ArrayList<TABBARITEMCONTENT> tabbaritemcontents = new ArrayList<TABBARITEMCONTENT>();
        TABBARITEMCONTENT tabbar1 = new TABBARITEMCONTENT(R.drawable.main_icon_select,R.drawable.main_icon_disselect,getResources().getString(R.string.main));
        TABBARITEMCONTENT tabbar2 = new TABBARITEMCONTENT(R.drawable.desk_icon_select,R.drawable.desk_icon_disselect,getResources().getString(R.string.desks));
//        TABBARITEMCONTENT tabbar3 = new TABBARITEMCONTENT(R.drawable.error_diagnosis_selected,R.drawable.error_diagnosis_disselected,"精密故障诊断");
        TABBARITEMCONTENT tabbar4 = new TABBARITEMCONTENT(R.drawable.waiter_icon_select,R.drawable.waiter_icon_disselect,getResources().getString(R.string.mine));
        tabbaritemcontents.add(tabbar1);
        tabbaritemcontents.add(tabbar2);
//        tabbaritemcontents.add(tabbar3);
        tabbaritemcontents.add(tabbar4);
        tabbar.setTabbarArray(tabbaritemcontents);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.bottom_content);
        relativeLayout.addView(tabbar);
        relativeLayout.addView(topLine);
    }

    /*清除状态栏背景色*/
    private void cleanBgOfStateBar() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(this, R.color.statue_navi_bar_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.statue_navi_bar_color));
        }
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();

//        mFragments = new Fragment[]{new PollingFragment(),
//                                    new TempTaskFragment(),
//                                    new ErrorDiagnoseFragment(),
//                                    new PersonalCenterFragment()};
        mFragments = new Fragment[]{new MainFragment(),
                new DeskFragment(),
                new MineFragment()};
        fragmentManager.beginTransaction().add(R.id.main_content, mFragments[0]).commit();
    }

    private void changeFragment(int pos) {

        if (prePos == pos) {
            return;
        }

        Fragment currentFragment = mFragments[pos];
        Fragment preFragment = mFragments[prePos];

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!currentFragment.isAdded()){
            transaction.add(R.id.main_content, currentFragment).hide(preFragment).commit();
        } else {
            transaction.hide(preFragment).show(currentFragment).commit();
        }

        prePos = pos;
    }

    @Override
    public void tapIndexOfTabbar(int index) {
        switch (index){
            case 0:
                changeFragment(0);
                break;

            case 1:
                changeFragment(1);
                break;

            case 2:
                changeFragment(2);
                break;

            case 3:
                changeFragment(3);
                break;
        }
    }
}
