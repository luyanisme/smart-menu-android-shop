package com.example.luyan.smartmenu_shop.Activity.Desk;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Adapter.CaseOrderedAdapter;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEPROPERTYITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASESTANDARDITEM;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.example.luyan.smartmenu_shop.Utils.UnitConvertUtils;

import java.util.ArrayList;

public class DeskDetailActivity extends BaseActivity {

    SwipeMenuListView caseOrdredList;//已点菜列表
    ArrayList<CASEITEM> caseitems = new ArrayList<>();
    CaseOrderedAdapter caseOrderedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_desk_detail);

        findViewById(R.id.order_state_btn).setOnClickListener(this);
        initData();
        caseOrdredList = (SwipeMenuListView) findViewById(R.id.case_order_list);
        caseOrderedAdapter = new CaseOrderedAdapter(this, caseitems);
        caseOrdredList.setAdapter(caseOrderedAdapter);

        createMenuItem();

        ActivityCollectorUtils.addActivity(this);
    }

    private void createMenuItem() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(UnitConvertUtils.dip2px(DeskDetailActivity.this, 90));
                // set a icon
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        caseOrdredList.setMenuCreator(creator);
    }

    private void initData() {
        CASEITEM caseitem = new CASEITEM();
        caseitem.setCaseName("红烧河豚");
        caseitem.setCasePrice((float) 256.0);
        caseitem.setOrderNum(2);
        CASESTANDARDITEM casestandarditem = new CASESTANDARDITEM();
        casestandarditem.setCaseStandardId(1);
        casestandarditem.setCaseStandardName("数量");
        casestandarditem.setCaseStandardValId(1);
        casestandarditem.setCaseStandardValue("一条");
        caseitem.setCasestandarditem(casestandarditem);
        CASEPROPERTYITEM casepropertyitem = new CASEPROPERTYITEM();
        casepropertyitem.setCasePropertyId(1);
        casepropertyitem.setCasePropertyName("口味");
        casepropertyitem.setCasePropertyValId(1);
        casepropertyitem.setCasePropertyValue("微辣");
        caseitem.setCasepropertyitem(casepropertyitem);

        CASEITEM caseitem1 = new CASEITEM();
        caseitem1.setCaseName("红烧河豚");
        caseitem1.setCasePrice((float) 256.0);
        caseitem1.setOrderNum(2);
        caseitem1.setCasestandarditem(casestandarditem);
        caseitem1.setCasepropertyitem(casepropertyitem);

        caseitems.add(caseitem);
        caseitems.add(caseitem1);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_state_btn:
                ((TextView) v).setText(getResources().getString(R.string.ordered));
                v.setBackgroundColor(getResources().getColor(R.color.gray));
                break;
        }
    }

    @Override
    public void setCenterTitle() {
        setTitleStr("详情");
    }

    @Override
    public void setLeftContent() {
        setLeftContent(R.drawable.back_icon);
    }

    @Override
    public void setRightContent() {
        setRightContent(R.drawable.add_icon);
    }

    @Override
    public void tapLeftNaviBar(View view) {
        ActivityCollectorUtils.pop();
    }

    @Override
    public void tapRightNaviBar(View view) {
        Intent intent = new Intent();
        intent.setClass(DeskDetailActivity.this, MenuActivity.class);
        startActivityForResult(intent, MenuActivity.REQUESTCODE);//REQUESTCODE定义一个整型做为请求对象标识
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MenuActivity.REQUESTCODE) {
            if (data.getParcelableArrayListExtra("orderItems") != null){
                ArrayList<CASEITEM> caseItems = data.getParcelableArrayListExtra("orderItems");
                caseitems.addAll(caseItems);
                caseOrderedAdapter.setCaseItems(caseitems);
                caseOrderedAdapter.notifyDataSetChanged();
            }
        }
    }
}
