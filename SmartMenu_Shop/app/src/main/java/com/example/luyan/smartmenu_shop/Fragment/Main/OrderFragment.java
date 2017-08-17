package com.example.luyan.smartmenu_shop.Fragment.Main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.example.luyan.smartmenu_shop.Activity.OrderActivity.OrderActivity;
import com.example.luyan.smartmenu_shop.Adapter.OrderAdapter;
import com.example.luyan.smartmenu_shop.Common.Public;
import com.example.luyan.smartmenu_shop.Metadata.NOTICEITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Model.NoticeModel;
import com.example.luyan.smartmenu_shop.Model.OrderModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import static com.example.luyan.smartmenu_shop.Utils.IntentUtils.INTENT_BUNDLE_PARAM;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private Context context;
    private ArrayList<ORDERITEM> orderitems;
    private XRefreshView refreshView;
    private ListView listView;
    private OrderAdapter orderAdapter;
    private KProgressHUD hud;
    private OrderDelegate delegate;
    private int unreadNums = 0;//未读消息个数
    private int currentIndex = 0;

    public OrderFragment() {
        // Required empty public constructor
    }

    public OrderFragment(Context contexts, ArrayList<ORDERITEM> orderitems) {
        // Required empty public constructor
        this.context = contexts;
        this.orderitems = orderitems;

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.waiting))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        listView = (ListView) view.findViewById(R.id.order_list);
        refreshView = (XRefreshView) view.findViewById(R.id.refresh_view);
        refreshView.setPullLoadEnable(true);//设置是否上拉加载更多
        refreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                OrderModel.getInstance().firstPage(new ZHHttpCallBack<RESPONSE<ORDERITEM>>() {
                    @Override
                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                        if (response.getStatus() == 0) {
                            orderitems.clear();
                            orderitems.addAll(response.getData());
                            orderAdapter.notifyDataSetChanged();
                            refreshView.stopRefresh();
                            statistics();
                            hud.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

                    }
                });
            }

            @Override
            public void onRefresh(boolean isPullDown) {

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                OrderModel.getInstance().goNextPage(new ZHHttpCallBack<RESPONSE<ORDERITEM>>() {
                    @Override
                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                        if (response.getStatus() == 0) {
                            orderitems.addAll(response.getData());
                            orderAdapter.notifyDataSetChanged();
                            refreshView.stopLoadMore();
                            hud.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

                    }
                });
            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });

        initList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        statistics();
    }

    private void initList() {
        orderAdapter = new OrderAdapter(getActivity(), orderitems);
        listView.setAdapter(orderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentIndex = i;
                Public.deskNum = orderitems.get(i).getDeskNum();
                Bundle bundle = new Bundle();
                bundle.putParcelable("order", orderitems.get(i));
                IntentUtils.startToActivityWithBundle(getActivity(), OrderActivity.class, bundle);
            }
        });
    }

    public void addOrderItem(ORDERITEM orderitem) {
        orderitems.add(0, orderitem);
        orderAdapter.notifyDataSetChanged();
    }

    /*统计未读个数*/
    private void statistics() {
        unreadNums = 0;
        for (int i = 0; i < orderitems.size(); i++) {
            if (!orderitems.get(i).isOrderIsDealed()) {
                unreadNums++;
            }
        }
        if (delegate != null) {
            delegate.orderMsgNumChange(unreadNums);
        }
    }

    public void startLoadData() {
        hud.show();
        OrderModel.getInstance().firstPage(new ZHHttpCallBack<RESPONSE<ORDERITEM>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatus() == 0) {
                    orderitems.clear();
                    orderitems.addAll(response.getData());
                    orderAdapter.notifyDataSetChanged();
                    hud.dismiss();
                    statistics();
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

            }
        });
    }

    public void changeItemStatue() {
        orderitems.get(currentIndex).setOrderIsDealed(true);
        orderAdapter.notifyDataSetChanged();
    }

    public KProgressHUD getHud() {
        return hud;
    }

    public void setHud(KProgressHUD hud) {
        this.hud = hud;
    }

    public OrderDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(OrderDelegate delegate) {
        this.delegate = delegate;
    }

    public int getUnreadNums() {
        return unreadNums;
    }

    public void setUnreadNums(int unreadNums) {
        this.unreadNums = unreadNums;
        statistics();
    }

    public interface OrderDelegate {
        public void orderMsgNumChange(int unreadNum);
    }

}
