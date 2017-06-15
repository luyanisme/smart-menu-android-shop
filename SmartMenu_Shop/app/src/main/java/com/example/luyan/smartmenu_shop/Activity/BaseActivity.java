package com.example.luyan.smartmenu_shop.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;


public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView leftImage;
    private TextView leftText;
    public Object leftContent;

    private ImageView rightImage;
    private TextView rightText;
    public Object rightContent;

    private TextView centerTitle;
    public String TitleStr;

    private FrameLayout mContentfl;/*给子类fragment填写内容*/
    private View mContentView;/*子类的view*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cleanBgOfStateBar();
        setContentView(R.layout.activity_base);
        mContentfl = (FrameLayout) findViewById(R.id.sub_frag_content);
        leftImage = (ImageView) findViewById(R.id.left_image);
        leftText = (TextView) findViewById(R.id.left_text);
        rightImage = (ImageView) findViewById(R.id.right_image);
        rightText = (TextView) findViewById(R.id.right_text);
        centerTitle = (TextView) findViewById(R.id.center_title);

        findViewById(R.id.left_content).setOnClickListener(this);
        findViewById(R.id.right_content).setOnClickListener(this);

        setCenterTitle();
        setLeftContent();
        setRightContent();
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

    public void setContentLayout(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(layoutResID, null);
        if (null != mContentfl) {
            mContentfl.addView(mContentView);
        }
    }

    public void setLeftContent(Object leftContent) {
        this.leftContent = leftContent;
        if (leftContent instanceof Integer){
            leftImage.setImageResource((Integer) leftContent);
        } else if (leftContent instanceof String){
            leftText.setText((String)leftContent);
            leftImage.setVisibility(View.GONE);
            leftText.setVisibility(View.VISIBLE);
        } else {
            return;
        }
    }

    public void setRightContent(Object rightContent) {
        this.rightContent = rightContent;
        if (rightContent instanceof Integer){
            rightImage.setImageResource((Integer) rightContent);
        } else if (rightContent instanceof String){
            rightText.setText((String)rightContent);
            rightImage.setVisibility(View.GONE);
            rightText.setVisibility(View.VISIBLE);
        } else {
            return;
        }
    }

    public void setTitleStr(String titleStr) {
        this.TitleStr = titleStr;
        centerTitle.setText(titleStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_content:
                tapLeftNaviBar(v);
                break;

            case R.id.right_content:
                tapRightNaviBar(v);
                break;

        }
    }

    public abstract void setCenterTitle();

    public abstract void setLeftContent();

    public abstract void setRightContent();

    public abstract void tapLeftNaviBar(View view);

    public abstract void tapRightNaviBar(View view);

    @Override
    public void finish() {
        super.finish();
        /*添加动画效果*/
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        /*动画效果*/
        overridePendingTransition(R.anim.move_right_in_activity,R.anim.move_left_out_activity);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        /*动画效果*/
        overridePendingTransition(R.anim.move_right_in_activity,R.anim.move_left_out_activity);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                ActivityCollectorUtils.pop();
        }
        return false;
    }

    public ImageView getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(ImageView leftImage) {
        this.leftImage = leftImage;
    }
}
