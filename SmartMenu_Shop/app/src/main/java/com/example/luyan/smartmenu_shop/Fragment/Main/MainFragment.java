package com.example.luyan.smartmenu_shop.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.luyan.smartmenu_shop.Fragment.BaseFragment;
import com.example.luyan.smartmenu_shop.Metadata.NOTICEITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Service.WebSocketService;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.widget.MsgView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by luyan on 26/05/2017.
 */

public class MainFragment extends BaseFragment {

    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();
    private Intent websocketServiceIntent;

    private NoticeFragment noticeFragment;
    private OrderFragment orderFragment;
    private OrderFragment orderedFragment;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentLayout(R.layout.fragment_main);
        initTabs();
        initWebSocket();
    }

    private void initWebSocket() {
        websocketServiceIntent = new Intent(getActivity(), WebSocketService.class);
        getActivity().startService(websocketServiceIntent);
        WebSocketService.webSocketConnect(new WebSocketHandler() {

            //websocket启动时候的回调
            @Override
            public void onOpen() {
                Log.d("111", "open");
                WebSocketService.isClosed = false;
            }

            //websocket接收到消息后的回调
            @Override
            public void onTextMessage(String payload) {
                Log.d("111", "payload = " + payload);
                Gson gson = new Gson();
                NOTICEITEM noticeitem = gson.fromJson(payload, NOTICEITEM.class);
                noticeFragment.addNoticeItem(noticeitem);
            }

            //websocket关闭时候的回调
            @Override
            public void onClose(int code, String reason) {
                WebSocketService.isClosed = true;
                Log.d("111", "code = " + code + " reason = " + reason);
                switch (code) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3://手动断开连接
//                            if (!isExitApp) {
//                                webSocketConnect();
//                            }
                        break;
                    case 4:
                        break;
                    /**
                     * 由于我在这里已经对网络进行了判断,所以相关操作就不在这里做了
                     */
                    case 5://网络断开连接
//                            closeWebsocket(false);
//                            webSocketConnect();
                        break;
                }
            }
        });
    }

    private void initTabs() {

        tabs.add(getActivity().getResources().getString(R.string.notice));
        tabs.add(getActivity().getResources().getString(R.string.order));
        tabs.add(getActivity().getResources().getString(R.string.order_receive));
        noticeFragment = new NoticeFragment(getActivity(), initNoticeData());
        orderFragment = new OrderFragment(getActivity(), initOrderData());
        orderedFragment = new OrderFragment(getActivity(), initOrderedData());
        fragments.add(noticeFragment);
        fragments.add(orderFragment);
        fragments.add(orderedFragment);

        tabLayout = (SlidingTabLayout) getView().findViewById(R.id.tablayout);

        viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        //设置TabLayout的模式
        viewPager.setAdapter(new TabAdapter(getActivity().getSupportFragmentManager()));
        //关联ViewPager和TabLayout
        tabLayout.setViewPager(viewPager);

        tabLayout.showMsg(0, 5);
        tabLayout.setMsgMargin(0, 20, 10);
        MsgView rtv_2_3 = tabLayout.getMsgView(0);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
    }

    private ArrayList<NOTICEITEM> initNoticeData() {
        ArrayList<NOTICEITEM> noticeitems = new ArrayList<>();
        NOTICEITEM noticeitem = new NOTICEITEM();
        noticeitem.setDeskNum("5号桌");
        noticeitem.setNoticeContent("正在呼叫服务员...");
        noticeitem.setDealed(false);

        NOTICEITEM noticeitem1 = new NOTICEITEM();
        noticeitem1.setDeskNum("6号桌");
        noticeitem1.setNoticeContent("正在呼叫服务员...");
        noticeitem1.setDealed(true);
        noticeitems.add(noticeitem);
        noticeitems.add(noticeitem1);

        return noticeitems;
    }

    private ArrayList<ORDERITEM> initOrderData() {
        ArrayList<ORDERITEM> orderItems = new ArrayList<>();

        ORDERITEM orderItem = new ORDERITEM();
        orderItem.setDealed(false);
        orderItem.setDeskNum("6号桌");
        orderItem.setStatue("待接单...");

        ORDERITEM orderItem1 = new ORDERITEM();
        orderItem1.setDealed(true);
        orderItem1.setDeskNum("5号桌");
        orderItem1.setStatue("订单取消");

        orderItems.add(orderItem);
        orderItems.add(orderItem1);
        return orderItems;
    }

    private ArrayList<ORDERITEM> initOrderedData() {
        ArrayList<ORDERITEM> orderItems = new ArrayList<>();

        ORDERITEM orderItem = new ORDERITEM();
        orderItem.setDealed(true);
        orderItem.setDeskNum("6号桌");
        orderItem.setStatue("创建于2017-6-1 12:30");

        ORDERITEM orderItem1 = new ORDERITEM();
        orderItem1.setDealed(true);
        orderItem1.setDeskNum("5号桌");
        orderItem1.setStatue("创建于2017-6-1 12:30");

        orderItems.add(orderItem);
        orderItems.add(orderItem1);
        return orderItems;
    }

    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //显示标签上的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }

    public MainFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WebSocketService.closeWebsocket(true);
        getActivity().stopService(websocketServiceIntent);
    }

    @Override

    public void setCenterTitle() {
        setTitleStr(getActivity().getResources().getString(R.string.main));
    }

    @Override
    public void setLeftContent() {

    }

    @Override
    public void setRightContent() {

    }

    @Override
    public void tapLeftNaviBar() {

    }

    @Override
    public void tapRightNaviBar() {

    }
}
