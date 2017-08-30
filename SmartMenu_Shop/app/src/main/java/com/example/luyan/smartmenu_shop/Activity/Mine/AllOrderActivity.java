package com.example.luyan.smartmenu_shop.Activity.Mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Adapter.AllOrderedAdapter;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Model.OrderModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Utils.DateUtils;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class AllOrderActivity extends BaseActivity {

    private ListView allOrderListView;
    private AllOrderedAdapter allOrderedAdapter;
    private ArrayList<ORDERITEM> orderitems = new ArrayList<>();
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_all_order);

        allOrderListView = (ListView) findViewById(R.id.all_order_list);

        ActivityCollectorUtils.addActivity(this);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.waiting))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        initList();
        initData();
    }

    private void initData() {
        OrderModel.getInstance().firstAllOrderedPage(new ZHHttpCallBack<RESPONSE<List<ORDERITEM>>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                hud.dismiss();
                if (response.getStatus() == 0) {
                    orderitems.addAll((ArrayList<ORDERITEM>) response.getData());
                    allOrderedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

            }
        }, DateUtils.getCurrentDate(),DateUtils.getTomorrowDate(DateUtils.getCurrentDate()));
    }

    private void initList() {
        allOrderedAdapter = new AllOrderedAdapter(this, orderitems);
        allOrderListView.setAdapter(allOrderedAdapter);
        allOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("orderInfo", orderitems.get(i));
                IntentUtils.startToActivityWithBundle(AllOrderActivity.this, DetailOrderActivity.class, bundle);
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
    }

    @Override
    public void setCenterTitle() {
        setTitleStr(DateUtils.getCurrentDateByCN());
    }

    @Override
    public void setLeftContent() {
        setLeftContent(R.drawable.back_icon);
    }

    @Override
    public void setRightContent() {
        setRightContent(R.drawable.czr_icon);
    }

    @Override
    public void tapLeftNaviBar(View view) {
        ActivityCollectorUtils.pop();
    }

    @Override
    public void tapRightNaviBar(View view) {

    }
}
