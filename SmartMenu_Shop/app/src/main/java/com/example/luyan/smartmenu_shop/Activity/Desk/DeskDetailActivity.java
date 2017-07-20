package com.example.luyan.smartmenu_shop.Activity.Desk;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Adapter.CaseOrderedAdapter;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEPROPERTYITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASESTANDARDITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDEREDITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Model.OrderModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Utils.UnitConvertUtils;
import com.example.luyan.smartmenu_shop.Widgt.ToastWidgt;
import com.kaopiz.kprogresshud.KProgressHUD;

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
        casestandarditem.setRuleId(1);
        casestandarditem.setRuleName("数量");
        casestandarditem.setId(1);
        casestandarditem.setValue("一条");
        caseitem.setCasestandarditem(casestandarditem);
        CASEPROPERTYITEM casepropertyitem = new CASEPROPERTYITEM();
        casepropertyitem.setRuleId(1);
        casepropertyitem.setRuleName("口味");
        casepropertyitem.setId(1);
        casepropertyitem.setValue("微辣");
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
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_state_btn:
                final KProgressHUD hud = KProgressHUD.create(DeskDetailActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getResources().getString(R.string.place_an_order))
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f);
                hud.show();
                ORDEREDITEM ordereditem = new ORDEREDITEM();
                ordereditem.setShopId((long) 1);
                ordereditem.setDeskId((long) 1);
                ordereditem.setDeskCateId((long) 1);
                ordereditem.setOrderedItems(caseitems);

                OrderModel.getInstance().postOrderlist(this, ordereditem, new ZHHttpCallBack<RESPONSE>() {

                    @Override
                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                        if (response.getStatus() == 0){
                            hud.dismiss();
                            ((TextView) v).setText(getResources().getString(R.string.ordered));
                            v.setBackgroundColor(getResources().getColor(R.color.gray));
                            ToastWidgt.showWithInfo(DeskDetailActivity.this,response.getMsg(),Toast.LENGTH_SHORT);
                            ActivityCollectorUtils.pop();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

                    }
                });

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
            if (data.getParcelableArrayListExtra("orderItems") != null) {
                ArrayList<CASEITEM> caseItems = data.getParcelableArrayListExtra("orderItems");
                caseitems.addAll(caseItems);
                caseOrderedAdapter.setCaseItems(caseitems);
                caseOrderedAdapter.notifyDataSetChanged();
            }
        }
    }
}
