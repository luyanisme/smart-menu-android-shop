package com.example.luyan.smartmenu_shop.Widgt;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Metadata.TABBARITEMCONTENT;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 4/28/16.
 */
public class UITabbar extends LinearLayout implements View.OnClickListener {

    private static final String Tag = UITabbar.class.getSimpleName();

    private static final int FIRSTFRAGMENT = 9990;
    private static final int SENCONDFRAGMENT = 9991;
    private static final int THIRDFRAGMENT = 9992;
    private static final int FOURTHFRAGMENT = 9993;

    private UITabBarDelegate delegate;
    private Context mContext;
    private TabBarItem currentTabBarItem;//当前的TabbarItem

    public UITabbar(Context context) {
        super(context);
        this.mContext = context;
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public UITabbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UITabbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTabbarArray(ArrayList<TABBARITEMCONTENT> tabbarArray) {
        for (int i = 0; i < tabbarArray.size(); i++) {
            TABBARITEMCONTENT tabbarItemContent = tabbarArray.get(i);
            TabBarItem tarBarItem = new TabBarItem(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            tarBarItem.setLayoutParams(params);
            tarBarItem.setmTextContent(tabbarItemContent.getTextTitle());
            tarBarItem.setmCheckedIconId(tabbarItemContent.getmCheckedIconId());
            tarBarItem.setmNormalIconId(tabbarItemContent.getmNormalIconId());
            tarBarItem.setId(i + 9990);
            tarBarItem.setOnClickListener(this);
            addView(tarBarItem);
            if (i == 0) {
                currentTabBarItem = tarBarItem;
                currentTabBarItem.isChoosed = true;
                currentTabBarItem.changeItemState();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case FIRSTFRAGMENT:
                Log.v(Tag, "您点击的第一个按钮");
                changeStateOfTabBarItem((TabBarItem) v);
                if (delegate != null){
                    delegate.tapIndexOfTabbar(0);
                }
                break;

            case SENCONDFRAGMENT:
                Log.v(Tag, "您点击的第二个按钮");
                changeStateOfTabBarItem((TabBarItem) v);
                if (delegate != null){
                    delegate.tapIndexOfTabbar(1);
                }
                break;

            case THIRDFRAGMENT:
                Log.v(Tag, "您点击的第三个按钮");
                changeStateOfTabBarItem((TabBarItem) v);
                if (delegate != null){
                    delegate.tapIndexOfTabbar(2);
                }
                break;

            case FOURTHFRAGMENT:
                Log.v(Tag, "您点击的第四个按钮");
                changeStateOfTabBarItem((TabBarItem) v);
                if (delegate != null){
                    delegate.tapIndexOfTabbar(3);
                }
                break;
        }
    }

    /*改变tabbar的状态*/
    private void changeStateOfTabBarItem(TabBarItem tabBarItem) {
        if (currentTabBarItem.getId() == tabBarItem.getId()) {
            return;
        } else {
            //将之前的选中状态还原
            currentTabBarItem.isChoosed = false;
            currentTabBarItem.changeItemState();
            //将目前item的状态改为选中
            tabBarItem.isChoosed = true;
            tabBarItem.changeItemState();
            currentTabBarItem = tabBarItem;
        }

    }

    public void setDelegate(UITabBarDelegate delegate) {
        this.delegate = delegate;
    }

    public interface UITabBarDelegate {
        public void tapIndexOfTabbar(int index);
    }
}

class TabBarItem extends LinearLayout {

    private static final int ICON_HEIGHT = 40;
    private static final int ICON_WIDTH = 40;

    private String mTextContent;
    private int mCheckedIconId;
    private int mNormalIconId;

    public Boolean isChoosed;

    private ImageView mBtnImage;
    private TextView mBtnText;

    public TabBarItem(Context context) {
        super(context);
        addChilds(context);
    }

    public TabBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabBarItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 添加子控件
     *
     * @param context
     */
    private void addChilds(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        mBtnImage = new ImageView(context);
        mBtnImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mBtnImage.setLayoutParams(
                new LayoutParams(ICON_WIDTH, ICON_HEIGHT));

        mBtnText = new TextView(context);
        mBtnText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10.0f);
        mBtnText.setTextColor(Color.parseColor("#FFFFFF"));
        mBtnText.setLayoutParams(
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(mBtnImage);
        addView(mBtnText);
    }

    public void setmNormalIconId(int mNormalIconId) {
        this.mNormalIconId = mNormalIconId;
        mBtnImage.setImageResource(mNormalIconId);
    }

    public void setmCheckedIconId(int mCheckedIconId) {
        this.mCheckedIconId = mCheckedIconId;
    }

    public void setmTextContent(String mTextContent) {
        this.mTextContent = mTextContent;
        mBtnText.setText(mTextContent);
    }

    public void changeItemState() {
        if (isChoosed) {
            mBtnText.setTextColor(Color.parseColor("#FFCE43"));
            mBtnImage.setImageResource(mCheckedIconId);
        } else {
            mBtnText.setTextColor(Color.parseColor("#FFFFFF"));
            mBtnImage.setImageResource(mNormalIconId);
        }
    }
}
