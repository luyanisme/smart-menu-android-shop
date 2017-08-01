package com.example.luyan.smartmenu_shop.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.example.luyan.smartmenu_shop.Fragment.BaseFragment;
import com.example.luyan.smartmenu_shop.Metadata.NOTICEITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESULT;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Service.WebSocketService;
import com.example.luyan.smartmenu_shop.Widgt.ToastWidgt;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.widget.MsgView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by luyan on 26/05/2017.
 */

public class MainFragment extends BaseFragment implements NoticeFragment.NoticeDelegate, OrderFragment.OrderDelegate {

    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();
    private Intent websocketServiceIntent;

    private NoticeFragment noticeFragment;
    private OrderFragment orderFragment;
    private OrderFragment orderedFragment;

    private int noticeUnreadNums;//消息未读个数;
    private int orderUnreadNums;//订单未读个数;
    private int orderedUnreadNums;//已接单消息未读个数;

    private boolean isLoaded = false;//是否加载过

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
        WebSocketService.webSocketConnect();
        WebSocketService.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    RESULT result = new Gson().fromJson(msg.getData().get("notice").toString(), RESULT.class);
                    switch (result.getNoticeType()) {
                        /*消息*/
                        case 0:
                            NOTICEITEM noticeitem = new Gson().fromJson(msg.getData().get("notice").toString(), NOTICEITEM.class);
                            //收到notice
                            noticeFragment.addNoticeItem(noticeitem);
                            noticeUnreadNums = noticeFragment.getUnreadNums();
                            noticeUnreadNums++;
                            noticeFragment.setUnreadNums(noticeUnreadNums);
                            break;

                        /*订单*/
                        case 1:
                            ORDERITEM orderitem = new Gson().fromJson(msg.getData().get("notice").toString(), ORDERITEM.class);
                            //收到notice
                            orderFragment.addOrderItem(orderitem);
                            orderUnreadNums = orderFragment.getUnreadNums();
                            orderUnreadNums++;
                            orderFragment.setUnreadNums(orderUnreadNums);
                            break;

                        /*消息返回*/
                        default:
                            noticeFragment.getHud().dismiss();
                            ToastWidgt.showWithInfo(getActivity(), result.getMsg(), Toast.LENGTH_SHORT);
                            if (result.getStatue() == 0) {
                                //消息类型
                                switch (result.getCallbackNoticeType()) {
                                    //通知
                                    case 0:
                                        noticeUnreadNums = noticeFragment.getUnreadNums();
                                        noticeUnreadNums--;
                                        noticeFragment.setUnreadNums(noticeUnreadNums);
                                        break;

                                    //订单
                                    case 1:
                                        orderedUnreadNums = orderFragment.getUnreadNums();
                                        orderedUnreadNums--;
                                        orderFragment.setUnreadNums(orderedUnreadNums);
                                        orderFragment.changeItemStatue();
                                        Intent mIntent = new Intent("order_callback");
                                        mIntent.putExtra("statue", result.getStatue());
                                        //发送广播
                                        getActivity().sendBroadcast(mIntent);
                                        break;
                                }
                            }
                            break;
                    }

                }
            }
        };
    }

    private void initTabs() {

        tabs.add(getActivity().getResources().getString(R.string.notice));
        tabs.add(getActivity().getResources().getString(R.string.order));
//        tabs.add(getActivity().getResources().getString(R.string.order_receive));
        noticeFragment = new NoticeFragment(getActivity(), initNoticeData());
        noticeFragment.setDelegate(this);
        orderFragment = new OrderFragment(getActivity(), initOrderData());
        orderFragment.setDelegate(this);
//        orderedFragment = new OrderFragment(getActivity(), initOrderedData());
        fragments.add(noticeFragment);
        fragments.add(orderFragment);
//        fragments.add(orderedFragment);

        tabLayout = (SlidingTabLayout) getView().findViewById(R.id.tablayout);

        viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        //设置TabLayout的模式
        viewPager.setAdapter(new TabAdapter(getActivity().getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    if (!isLoaded) {
                        orderFragment.startLoadData();
                        isLoaded = true;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //关联ViewPager和TabLayout
        tabLayout.setViewPager(viewPager);

    }

    private ArrayList<NOTICEITEM> initNoticeData() {
        ArrayList<NOTICEITEM> noticeitems = new ArrayList<>();
        return noticeitems;
    }

    private ArrayList<ORDERITEM> initOrderData() {
        ArrayList<ORDERITEM> orderItems = new ArrayList<>();
        return orderItems;
    }

//    private ArrayList<ORDERITEM> initOrderedData() {
//        ArrayList<ORDERITEM> orderItems = new ArrayList<>();
//
//        return orderItems;
//    }

    @Override
    public void noticeMsgNumChange(int unreadNum) {
//        if (unreadNum == 0){
//            tabLayout.hideMsg(0);
//        } else {
//            tabLayout.showMsg(0, unreadNum);
//            tabLayout.setMsgMargin(0, 20, 10);
//            MsgView rtv_2_3 = tabLayout.getMsgView(0);
//            if (rtv_2_3 != null) {
//                rtv_2_3.setBackgroundColor(getResources().getColor(R.color.colorRed));
//            }
//        }
        if (unreadNum > 0) {
            tabLayout.showDot(0);
            tabLayout.setMsgMargin(0, 55, 0);
        } else {
            tabLayout.hideMsg(0);
        }
    }

    @Override
    public void orderMsgNumChange(int unreadNum) {
        if (unreadNum > 0) {
            tabLayout.showDot(1);
            tabLayout.setMsgMargin(1, 55, 0);
        } else {
            tabLayout.hideMsg(0);
        }
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
