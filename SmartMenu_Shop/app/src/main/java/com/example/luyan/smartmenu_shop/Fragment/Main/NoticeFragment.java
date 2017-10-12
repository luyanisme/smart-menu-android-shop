package com.example.luyan.smartmenu_shop.Fragment.Main;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.example.luyan.smartmenu_shop.Adapter.NoticeAdapter;
import com.example.luyan.smartmenu_shop.Metadata.NOTICEITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Model.NoticeModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Service.WebSocketService;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Widgt.SMDialog;
import com.example.luyan.smartmenu_shop.Widgt.ToastWidgt;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {

    private static final int REFRESH_COMPLETE = 0X110;
    private Context context;
    private ArrayList<NOTICEITEM> noticeitems;
    private XRefreshView refreshView;
    private ListView listView;
    private NoticeAdapter noticeAdapter;
    private KProgressHUD hud;
    private NoticeDelegate delegate;
    private int unreadNums = 0;//未读消息个数

    public NoticeFragment() {
        // Required empty public constructor
    }

    public NoticeFragment(Context contexts, ArrayList<NOTICEITEM> noticeitems) {
        // Required empty public constructor
        this.context = contexts;
        this.noticeitems = noticeitems;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.waiting))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        listView = (ListView) view.findViewById(R.id.notice_list);
        refreshView = (XRefreshView) view.findViewById(R.id.refresh_view);
        refreshView.setPullLoadEnable(true);//设置是否上拉加载更多
        refreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                NoticeModel.getInstance().firstPage(new ZHHttpCallBack<RESPONSE<List<NOTICEITEM>>>() {
                    @Override
                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                        if (response.getStatus() == 0) {
                            noticeitems.clear();
                            noticeitems.addAll((ArrayList<NOTICEITEM>) response.getData());
                            noticeAdapter.notifyDataSetChanged();
                            refreshView.stopRefresh();
                            statistics();
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
                NoticeModel.getInstance().goNextPage(new ZHHttpCallBack<RESPONSE<List<NOTICEITEM>>>() {
                    @Override
                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                        if (response.getStatus() == 0) {
                            noticeitems.addAll((ArrayList<NOTICEITEM>) response.getData());
                            noticeAdapter.notifyDataSetChanged();
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
        hud.show();
        NoticeModel.getInstance().firstPage(new ZHHttpCallBack<RESPONSE<List<NOTICEITEM>>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatus() == 0) {
                    noticeitems.addAll((ArrayList<NOTICEITEM>)  response.getData());
                    noticeAdapter.notifyDataSetChanged();
                    hud.dismiss();
                    statistics();
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

            }
        });
        initList();

        return view;
    }

    private void initList() {
        noticeAdapter = new NoticeAdapter(getActivity(), noticeitems);
        listView.setAdapter(noticeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                if (noticeitems.get(i).isNoticeIsDealed()){
                    ToastWidgt.showWithInfo(getActivity(),getActivity().getResources().getString(R.string.has_dealed),Toast.LENGTH_SHORT);
                    return;
                }

                final SMDialog sm = new SMDialog(getActivity());
                sm.setTitle(getResources().getString(R.string.tips));
                sm.setMessage(noticeitems.get(i).getDeskNum() + "请求服务");

                sm.setPositiveButton(getResources().getString(R.string.recieved), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        noticeitems.get(i).setClientType(1);//设置客户端为安卓
                        noticeitems.get(i).setNoticeIsDealed(true);
                        noticeAdapter.notifyDataSetChanged();
                        sm.dismiss();
                        WebSocketService.sendMsg(new Gson().toJson(noticeitems.get(i), NOTICEITEM.class));
                        hud.show();
                    }
                });

                sm.setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        sm.dismiss();
                    }
                });
            }
        });
    }

    public void addNoticeItem(NOTICEITEM noticeitem) {
        noticeitems.add(0, noticeitem);
        noticeAdapter.notifyDataSetChanged();
    }

    /*统计未读个数*/
    private void statistics(){
        unreadNums = 0;
        for (int i = 0; i < noticeitems.size(); i++) {
            if (!noticeitems.get(i).isNoticeIsDealed()){
                unreadNums ++;
            }
        }
        if (delegate != null) {
            delegate.noticeMsgNumChange(unreadNums);
        }
    }

    public KProgressHUD getHud() {
        return hud;
    }

    public void setHud(KProgressHUD hud) {
        this.hud = hud;
    }

    public NoticeDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(NoticeDelegate delegate) {
        this.delegate = delegate;
    }

    public int getUnreadNums() {
        return unreadNums;
    }

    public void setUnreadNums(int unreadNums) {
        this.unreadNums = unreadNums;
        statistics();
    }

    public interface NoticeDelegate {
        public void noticeMsgNumChange(int unreadNum);
    }

    public void clearNotices(){
        unreadNums = 0;
        this.noticeitems.clear();
        noticeAdapter.notifyDataSetChanged();

    }
}
