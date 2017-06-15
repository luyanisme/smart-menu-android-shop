package com.example.luyan.smartmenu_shop.Fragment.Desk;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Activity.Desk.DeskDetailActivity;
import com.example.luyan.smartmenu_shop.Adapter.ConstellationAdapter;
import com.example.luyan.smartmenu_shop.Adapter.DeskAdapter;
import com.example.luyan.smartmenu_shop.Adapter.GirdDropDownAdapter;
import com.example.luyan.smartmenu_shop.Adapter.ListDropDownAdapter;
import com.example.luyan.smartmenu_shop.Fragment.BaseFragment;
import com.example.luyan.smartmenu_shop.Metadata.DESKITEM;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeskFragment extends BaseFragment {

    private DropDownMenu mDropDownMenu;
    //    private String headers[] = {"区域", "状态", "性别", "星座"};
    private String headers[] = {"区域", "状态"};
    private List<View> popupViews = new ArrayList<>();
    private ArrayList<DESKITEM> showAreaList;//过滤后需要显示的item

    private GirdDropDownAdapter areaAdapter;
    private ListDropDownAdapter statueAdapter;
//    private ListDropDownAdapter sexAdapter;
//    private ConstellationAdapter constellationAdapter;
    private DeskAdapter deskAdapter;

    private String areas[] = {"不限", "大厅", "包厢"};
    private String statues[] = {"不限", "空座", "预订", "满座"};
//    private String sexs[] = {"不限", "男", "女"};
//    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};

    private int constellationPosition = 0;

    ArrayList<DESKITEM> deskitems = new ArrayList<>();

    public DeskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentLayout(R.layout.fragment_desk);
        mDropDownMenu = (DropDownMenu) getView().findViewById(R.id.dropDownMenu);
        initDeskItems();
        initView();
    }

    private void initView() {
        //init area menu
        final ListView areaView = new ListView(getActivity());
        areaAdapter = new GirdDropDownAdapter(getActivity(), Arrays.asList(areas));
        areaView.setDividerHeight(0);
        areaView.setAdapter(areaAdapter);

        //init statue menu
        final ListView statueView = new ListView(getActivity());
        statueView.setDividerHeight(0);
        statueAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(statues));
        statueView.setAdapter(statueAdapter);

//        //init sex menu
//        final ListView sexView = new ListView(getActivity());
//        sexView.setDividerHeight(0);
//        sexAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(sexs));
//        sexView.setAdapter(sexAdapter);
//
//        //init constellation
//        final View constellationView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout, null);
//        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
//        constellationAdapter = new ConstellationAdapter(getActivity(), Arrays.asList(constellations));
//        constellation.setAdapter(constellationAdapter);
//        TextView ok = (TextView) constellationView.findViewById(R.id.ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
//                mDropDownMenu.closeMenu();
//            }
//        });

        //init popupViews
        popupViews.add(areaView);
        popupViews.add(statueView);
//        popupViews.add(sexView);
//        popupViews.add(constellationView);

        //add item click event
        areaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : areas[position]);
                mDropDownMenu.closeMenu();
            }
        });

        statueView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                statueAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : statues[position]);
                mDropDownMenu.closeMenu();
            }
        });

//        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                sexAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                constellationAdapter.setCheckItem(position);
//                constellationPosition = position;
//            }
//        });

        //内容显示
        final View deskView = getActivity().getLayoutInflater().inflate(R.layout.desk_grid_layout, null);
        GridView deskGridView = (GridView) deskView.findViewById(R.id.desk_grid);
        deskAdapter = new DeskAdapter(getActivity(), deskitems);
        deskGridView.setAdapter(deskAdapter);
        deskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentUtils.startToActivity(getActivity(), DeskDetailActivity.class);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, deskView);
    }

    private void initDeskItems() {

        DESKITEM deskitem1 = new DESKITEM();
        deskitem1.setDeskName("1号桌");
        deskitem1.setCapacity(3);
        deskitem1.setStatue(DESKITEM.DESK_FREE);
        deskitem1.setHall(true);

        DESKITEM deskitem2 = new DESKITEM();
        deskitem2.setDeskName("2号桌");
        deskitem2.setCapacity(3);
        deskitem2.setStatue(DESKITEM.DESK_FREE);
        deskitem2.setHall(true);

        DESKITEM deskitem3 = new DESKITEM();
        deskitem3.setDeskName("3号桌");
        deskitem3.setCapacity(3);
        deskitem3.setStatue(DESKITEM.DESK_FULL);
        deskitem3.setHall(true);

        DESKITEM deskitem4 = new DESKITEM();
        deskitem4.setDeskName("4号桌");
        deskitem4.setCapacity(3);
        deskitem4.setStatue(DESKITEM.DESK_FULL);
        deskitem4.setHall(true);

        DESKITEM deskitem5 = new DESKITEM();
        deskitem5.setDeskName("兰花厅");
        deskitem5.setCapacity(12);
        deskitem5.setStatue(DESKITEM.DESK_FREE);
        deskitem5.setHall(false);

        DESKITEM deskitem6 = new DESKITEM();
        deskitem6.setDeskName("牡丹厅");
        deskitem6.setCapacity(12);
        deskitem6.setStatue(DESKITEM.DESK_BOOK);
        deskitem6.setHall(false);

        deskitems.add(deskitem1);
        deskitems.add(deskitem2);
        deskitems.add(deskitem3);
        deskitems.add(deskitem4);
        deskitems.add(deskitem5);
        deskitems.add(deskitem6);
    }

    @Override
    public void setCenterTitle() {
        setTitleStr(getActivity().getResources().getString(R.string.desks));
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
