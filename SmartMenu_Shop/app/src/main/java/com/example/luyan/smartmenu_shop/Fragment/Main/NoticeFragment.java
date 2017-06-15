package com.example.luyan.smartmenu_shop.Fragment.Main;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.example.luyan.smartmenu_shop.Adapter.NoticeAdapter;
import com.example.luyan.smartmenu_shop.Metadata.NOTICEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

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

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    noticeitems.addAll(noticeitems);
                    noticeAdapter.notifyDataSetChanged();
                    refreshView.stopRefresh();
                    break;

            }
        };
    };

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
        listView = (ListView) view.findViewById(R.id.notice_list);
        refreshView = (XRefreshView) view.findViewById(R.id.refresh_view);
        refreshView.setPullLoadEnable(true);//设置是否上拉加载更多
        refreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
            }

            @Override
            public void onRefresh(boolean isPullDown) {

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        refreshView.stopLoadMore();
                    }
                }, 2000);
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

    private void initList(){
        noticeAdapter = new NoticeAdapter(getActivity(),noticeitems);
        listView.setAdapter(noticeAdapter);
    }

    public void addNoticeItem(NOTICEITEM noticeitem){
        noticeitems.add(0, noticeitem);
        noticeAdapter.notifyDataSetChanged();
    }
}
