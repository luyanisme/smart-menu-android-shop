package com.example.luyan.smartmenu_shop.Activity.Desk;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Adapter.CaseOrderedAdapter;
import com.example.luyan.smartmenu_shop.Adapter.OrderAdapter;
import com.example.luyan.smartmenu_shop.Adapter.OrderedAdapter;
import com.example.luyan.smartmenu_shop.Common.Public;
import com.example.luyan.smartmenu_shop.Metadata.DESKCATEITEM;
import com.example.luyan.smartmenu_shop.Metadata.DESKITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Model.UserModel;
import com.example.luyan.smartmenu_shop.Service.WebSocketService;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
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
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

public class DeskDetailActivity extends BaseActivity implements OrderAdapter.TapDelegate {

    SwipeMenuListView caseOrdredList;//已点菜列表
    ArrayList<CASEITEM> caseitems = new ArrayList<>();
    OrderAdapter orderedAdapter;
    private KProgressHUD hud;
    private TextView orderBtn;//立即下单
    private int caseNums;//商品数量
    private RelativeLayout totalPriceWrapper;
    private TextView totalPrice;
    private DESKITEM deskItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_desk_detail);

        deskItem = getIntent().getBundleExtra(IntentUtils.INTENT_BUNDLE_PARAM).getParcelable("deskInfo");

        orderBtn = (TextView) findViewById(R.id.order_state_btn);
        orderBtn.setOnClickListener(this);
        orderBtn.setVisibility(View.GONE);

        totalPriceWrapper = (RelativeLayout) findViewById(R.id.total_price_wrapper);
        totalPrice = (TextView) findViewById(R.id.total_price);

        caseOrdredList = (SwipeMenuListView) findViewById(R.id.case_order_list);
        orderedAdapter = new OrderAdapter(this, caseitems);
        orderedAdapter.setTapDelegate(this);
        caseOrdredList.setAdapter(orderedAdapter);

        hud = KProgressHUD.create(DeskDetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        hud.show();
//        createMenuItem();

        ActivityCollectorUtils.addActivity(this);

        OrderModel.getInstance().getOrdered(new ZHHttpCallBack<RESPONSE<ORDERITEM>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatue() == 0) {
                    hud.dismiss();
                    if (response.getData().size() == 0) {
                        ToastWidgt.showWithInfo(DeskDetailActivity.this, getResources().getString(R.string.no_case), Toast.LENGTH_SHORT);
                        return;
                    } else {
                        ORDERITEM orderitem = (ORDERITEM) response.getData().get(0);//只有一个对象
                        CASEITEM[] tempCaseitems = new Gson().fromJson(orderitem.getOrderContent(), CASEITEM[].class);
                        for (int i = 0; i < tempCaseitems.length; i++) {
                            tempCaseitems[i].setOrdered(orderitem.isOrderIsOrdered());
                            caseitems.add(tempCaseitems[i]);
                        }
                        caseNums = caseitems.size();
                        orderedAdapter.notifyDataSetChanged();
                        totalPriceWrapper.setVisibility(View.VISIBLE);
                        totalPrice.setText("总价:"+orderitem.getOrderPrice()+"￥");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

            }
        }, deskItem.getDeskId());
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

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_state_btn:
                hud.setLabel(getResources().getString(R.string.place_an_order));
                hud.show();
                ArrayList<CASEITEM> newCaseItems = new ArrayList<>();
                float totalPrice = 0;
                for (int i = 0; i < caseitems.size(); i++) {
                    if (!caseitems.get(i).isOrdered()) {
                        newCaseItems.add(caseitems.get(i));
                        totalPrice += caseitems.get(i).getCasePrice();
                    }
                }
                ORDERITEM orderitem = new ORDERITEM();
                orderitem.setClientType(2);
                /*直接发送到pad端*/
                orderitem.setNoticeType((long) 2);
                orderitem.setShopId((long) UserModel.getInstance().getShopId());
                orderitem.setDeskId((long) deskItem.getDeskId());
                orderitem.setDeskNum(deskItem.getDeskName());
                orderitem.setOrderPrice(String.valueOf(totalPrice));
                orderitem.setOrderContent(new Gson().toJson(newCaseItems));
                orderitem.setOrderIsDealed(true);
                orderitem.setOrderIsPayed(false);
                orderitem.setOrderIsOrdered(true);
                orderitem.setOrderIsUsing(true);

                WebSocketService.sendMsg(new Gson().toJson(orderitem, ORDERITEM.class));
                OrderModel.getInstance().postOrderlist(this, orderitem, new ZHHttpCallBack<RESPONSE>() {

                    @Override
                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                        if (response.getStatue() == 0) {
                            hud.dismiss();
                            ((TextView) v).setText(getResources().getString(R.string.ordered));
                            v.setBackgroundColor(getResources().getColor(R.color.gray));
                            ToastWidgt.showWithInfo(DeskDetailActivity.this, response.getMsg(), Toast.LENGTH_SHORT);
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
                orderedAdapter.setCaseItems(caseitems);
                orderedAdapter.notifyDataSetChanged();
                orderBtn.setVisibility(View.VISIBLE);
                totalPriceWrapper.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void tapReduce(int caseNums) {
        if (this.caseNums == caseNums) {
            orderBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void tapAdd(int caseNums) {

    }
}
