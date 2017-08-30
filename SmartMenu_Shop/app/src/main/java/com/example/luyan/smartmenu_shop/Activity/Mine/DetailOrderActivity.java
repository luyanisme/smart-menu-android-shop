package com.example.luyan.smartmenu_shop.Activity.Mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Adapter.OrderDetailAdapter;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailOrderActivity extends BaseActivity {

    private ORDERITEM orderitem;
    private ArrayList<CASEITEM> caseitems = new ArrayList<>();
    private ListView caseList;
    private OrderDetailAdapter orderDetailAdapter;
    private TextView totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_detail_order);

        orderitem = getIntent().getBundleExtra(IntentUtils.INTENT_BUNDLE_PARAM).getParcelable("orderInfo");

        caseList = (ListView) findViewById(R.id.case_list);
        totalPrice = (TextView) findViewById(R.id.total_price);

        ActivityCollectorUtils.addActivity(this);
        initList();
        totalPrice.setText("总消费:"+orderitem.getOrderPrice()+"￥");
    }

    private void initList(){
        CASEITEM[] tempCaseitems = new Gson().fromJson(orderitem.getOrderContent(), CASEITEM[].class);
        for (int i = 0; i < tempCaseitems.length; i++) {
            caseitems.add(tempCaseitems[i]);
        }
        orderDetailAdapter = new OrderDetailAdapter(this, caseitems);
        caseList.setAdapter(orderDetailAdapter);
    }

    @Override
    public void setCenterTitle() {
        setTitleStr(getResources().getString(R.string.order_detail));
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
