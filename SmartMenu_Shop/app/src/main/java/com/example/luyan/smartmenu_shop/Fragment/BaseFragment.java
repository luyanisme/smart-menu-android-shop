package com.example.luyan.smartmenu_shop.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

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

    public BaseFragment() {
        // Required empty public constructor
    }

    public void setContentLayout(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(layoutResID, null);
        if (null != mContentfl) {
            mContentfl.addView(mContentView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContentfl = (FrameLayout) getView().findViewById(R.id.sub_frag_content);
        leftImage = (ImageView) getView().findViewById(R.id.left_image);
        leftText = (TextView) getView().findViewById(R.id.left_text);
        rightImage = (ImageView) getView().findViewById(R.id.right_image);
        rightText = (TextView) getView().findViewById(R.id.right_text);
        centerTitle = (TextView) getView().findViewById(R.id.center_title);

        getView().findViewById(R.id.left_content).setOnClickListener(this);
        getView().findViewById(R.id.right_content).setOnClickListener(this);

        setCenterTitle();
        setLeftContent();
        setRightContent();
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
                tapLeftNaviBar();
                break;

            case R.id.right_content:
                tapRightNaviBar();
                break;
        }
    }

    public abstract void setCenterTitle();

    public abstract void setLeftContent();

    public abstract void setRightContent();

    public abstract void tapLeftNaviBar();

    public abstract void tapRightNaviBar();
}
