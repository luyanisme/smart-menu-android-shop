package com.example.luyan.smartmenu_shop.Activity.Desk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Adapter.CaseAdapter;
import com.example.luyan.smartmenu_shop.Adapter.CateAdapter;
import com.example.luyan.smartmenu_shop.Metadata.CASECATEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEPROPERTYITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASESTANDARDITEM;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Widgt.ChooseStandardPanel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MenuActivity extends BaseActivity implements CaseAdapter.TapDelegate, ChooseStandardPanel.TapDelegate {

    private ListView cateList;//分类列表
    private ListView caseList;//商品列表
    private CateAdapter cateAdapter;
    private CaseAdapter caseAdapter;
    private CASECATEITEM[] casecateitems;
    private int currentIndex = 0;//当前index
    ArrayList<CASEITEM> caseitems = new ArrayList<>();
    ArrayList<CASEITEM> orderedItems = new ArrayList<>();//已选菜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_menu);

        initData();
        initCateList();
        initCaseList();
        ActivityCollectorUtils.addActivity(this);
    }

    private void initCateList() {
        cateList = (ListView) findViewById(R.id.cate_list);
        cateAdapter = new CateAdapter(this, casecateitems);
        cateList.setAdapter(cateAdapter);
        cateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == currentIndex) {
                    return;
                } else {
                    casecateitems[i].setSelected(true);
                    casecateitems[currentIndex].setSelected(false);
                    currentIndex = i;
                    cateAdapter.notifyDataSetChanged();
                    caseitems = casecateitems[currentIndex].getCases();
                    caseAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initCaseList() {
        caseList = (ListView) findViewById(R.id.case_list);
        caseitems = casecateitems[currentIndex].getCases();
        caseAdapter = new CaseAdapter(this, caseitems);
        caseAdapter.setTapDelegate(this);
        caseList.setAdapter(caseAdapter);
    }

    private void initData() {
        String json = null;
        try {
            InputStream is = getAssets().open("json.txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            json = new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        casecateitems = gson.fromJson(json, CASECATEITEM[].class);
        casecateitems[0].setSelected(true);

    }

    @Override
    public void setCenterTitle() {
        setTitleStr(getResources().getString(R.string.menu));
    }

    @Override
    public void setLeftContent() {
        setLeftContent(R.drawable.back_icon);
    }

    @Override
    public void setRightContent() {

    }

    @Override
    public void tapLeftNaviBar(View view) {
        ActivityCollectorUtils.pop();
    }

    @Override
    public void tapRightNaviBar(View view) {

    }

    /*商品数量添加减少*/
    @Override
    public void tapAddButton(int index, TextView orderNumView, RelativeLayout reduceView) {
        int orderNum = Integer.valueOf(orderNumView.getText().toString());
        if (orderNum == 0) {
            orderNum++;
            orderNumView.setVisibility(View.VISIBLE);
            reduceView.setVisibility(View.VISIBLE);
            orderNumView.setText(String.valueOf(orderNum));
        } else {
            orderNum++;
            orderNumView.setText(String.valueOf(orderNum));
        }
    }

    @Override
    public void tapReduceButton(int index, TextView orderNumView, RelativeLayout reduceView) {
        int orderNum = Integer.valueOf(orderNumView.getText().toString());
        orderNum--;
        orderNumView.setText(String.valueOf(orderNum));
        if (orderNum == 0) {
            orderNumView.setVisibility(View.GONE);
            reduceView.setVisibility(View.GONE);
        }
    }

    @Override
    public void tapChooseStandard(int index, TextView orderTag) {
        ChooseStandardPanel csd = new ChooseStandardPanel(MenuActivity.this, casecateitems[currentIndex].getCases().get(index));
        csd.setCaseIndex(index);
        csd.setOrderTag(orderTag);
        csd.setOrderedItems(orderedItems);
        csd.setTapDelegate(this);
    }

    @Override
    /*ChooseStandardPanel点击事件的代理方法*/
    public void tap(int index, int orderNum) {

    }
}
