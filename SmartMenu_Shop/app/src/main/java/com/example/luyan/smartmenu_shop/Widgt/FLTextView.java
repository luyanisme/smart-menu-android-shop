package com.example.luyan.smartmenu_shop.Widgt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.R;

/**
 * Created by luyan on 13/06/2017.
 */

public class FLTextView extends TextView {

    public static int STANDARD = 0;
    public static int PROPERTY = 1;

    private int Type;
    private boolean isSelected;//是否选中
    private Context mContext;

    public FLTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public FLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FLTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        if(isSelected){
            setBackgroundResource(R.drawable.choose_standard_bg);
            setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            setBackgroundResource(R.drawable.bg_tag);
            setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }
}
