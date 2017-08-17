package com.example.luyan.smartmenu_shop.Activity.OrderActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Activity.Desk.DeskDetailActivity;
import com.example.luyan.smartmenu_shop.Adapter.CaseOrderedAdapter;
import com.example.luyan.smartmenu_shop.Common.Public;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.NOTICEITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDEREDITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Model.OrderModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Service.WebSocketService;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Widgt.ToastWidgt;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

public class OrderActivity extends BaseActivity {

    private ORDERITEM orderitem;
    SwipeMenuListView caseOrdredList;//已点菜列表
    ArrayList<CASEITEM> caseitems = new ArrayList<>();
    CaseOrderedAdapter caseOrderedAdapter;
    private KProgressHUD hud;
    private TextView orderBtn;

    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("order_callback")) {
                int statue = (int) intent.getLongExtra("statue", 0);
                if (statue == 0) {
                    hud.dismiss();
                    ToastWidgt.showWithInfo(OrderActivity.this, getResources().getString(R.string.ordered_success), Toast.LENGTH_SHORT);
                    ActivityCollectorUtils.pop();
                }
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_order);

        orderBtn = (TextView) findViewById(R.id.order_state_btn);
        orderBtn.setOnClickListener(this);

        orderitem = getIntent().getBundleExtra(IntentUtils.INTENT_BUNDLE_PARAM).getParcelable("order");

        if (orderitem.isOrderIsDealed()){
            orderBtn.setBackgroundResource(R.color.gray);
            orderBtn.setEnabled(false);
        }

        CASEITEM[] items = new Gson().fromJson(orderitem.getOrderContent(), CASEITEM[].class);
        for (int i = 0; i < items.length; i++) {
            caseitems.add(items[i]);
        }
        caseOrdredList = (SwipeMenuListView) findViewById(R.id.case_order_list);
        caseOrderedAdapter = new CaseOrderedAdapter(this, caseitems);
        caseOrdredList.setAdapter(caseOrderedAdapter);

        hud = KProgressHUD.create(OrderActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.place_an_order))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        ActivityCollectorUtils.addActivity(this);

        registerBoradcastReceiver();
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_state_btn:

                hud.show();
//                ORDEREDITEM ordereditem = new ORDEREDITEM();
//                ordereditem.setShopId((long) 1);
//                ordereditem.setDeskId((long) 1);
//                ordereditem.setDeskCateId((long) 1);
//                ordereditem.setOrderedItems(caseitems);
//
//                OrderModel.getInstance().postOrderlist(this, ordereditem, new ZHHttpCallBack<RESPONSE>() {
//
//                    @Override
//                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
//                        if (response.getStatus() == 0){
//                            hud.dismiss();
//                            ((TextView) v).setText(getResources().getString(R.string.ordered));
//                            v.setBackgroundColor(getResources().getColor(R.color.gray));
//                            ToastWidgt.showWithInfo(OrderActivity.this,response.getMsg(), Toast.LENGTH_SHORT);
//                            ActivityCollectorUtils.pop();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {
//
//                    }
//                });
                orderitem.setOrderIsDealed(true);
                WebSocketService.sendMsg(new Gson().toJson(orderitem, ORDERITEM.class));

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBoradcastReceiver();
    }

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("order_callback");
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    public void unRegisterBoradcastReceiver() {
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void setCenterTitle() {
        setTitleStr(Public.deskNum);
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
}
