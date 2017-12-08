package com.example.luyan.smartmenu_shop.Fragment.Desk;


import android.graphics.Color;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luyan.smartmenu_shop.Activity.Desk.DeskDetailActivity;
import com.example.luyan.smartmenu_shop.Activity.OrderActivity.OrderActivity;
import com.example.luyan.smartmenu_shop.Adapter.ConstellationAdapter;
import com.example.luyan.smartmenu_shop.Adapter.DeskAdapter;
import com.example.luyan.smartmenu_shop.Adapter.GirdDropDownAdapter;
import com.example.luyan.smartmenu_shop.Adapter.ListDropDownAdapter;
import com.example.luyan.smartmenu_shop.Common.Public;
import com.example.luyan.smartmenu_shop.Fragment.BaseFragment;
import com.example.luyan.smartmenu_shop.Metadata.CONDITIONITEM;
import com.example.luyan.smartmenu_shop.Metadata.DESKCATEITEM;
import com.example.luyan.smartmenu_shop.Metadata.DESKITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Model.DeskModel;
import com.example.luyan.smartmenu_shop.Model.UserModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.GreenDaoUtils.GreenDaoUtils;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Widgt.SMDialog;
import com.example.luyan.smartmenu_shop.Widgt.ToastWidgt;
import com.kaopiz.kprogresshud.KProgressHUD;
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

    //    private String areas[] = {"不限", "大厅", "包厢"};
    private ArrayList<CONDITIONITEM> areas = new ArrayList<>();
    //{"不限", "空座", "预订", "座满"}
    private ArrayList<CONDITIONITEM> statues = new ArrayList<>();
//    private String sexs[] = {"不限", "男", "女"};
//    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};

    private int constellationPosition = 0;
    private int statueIndex = 0;

    ArrayList<DESKITEM> deskitems = new ArrayList<>();
    ArrayList<DESKITEM> selectItems = new ArrayList<>();

    private int cateId = 0;//桌位分类id
    private int stateCode = 3;//状态code为3

    private KProgressHUD hud;

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
        initConditonData();
        initDeskItems();

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getActivity().getResources().getString(R.string.waiting))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f).show();
    }

    private void initConditonData() {
        //不限
        CONDITIONITEM noCondition = new CONDITIONITEM();
        noCondition.setStatueCode(0);
        noCondition.setStatue(getActivity().getResources().getString(R.string.no_limit));
        areas.add(noCondition);

        //不限
        CONDITIONITEM condition = new CONDITIONITEM();
        condition.setStatueCode(3);
        condition.setStatue(getActivity().getResources().getString(R.string.no_limit));
        statues.add(condition);

        CONDITIONITEM condition0 = new CONDITIONITEM();
        condition0.setStatueCode(DESKITEM.DESK_FREE);
        condition0.setStatue(getActivity().getResources().getString(R.string.free_desk));
        statues.add(condition0);

        CONDITIONITEM condition1 = new CONDITIONITEM();
        condition1.setStatueCode(DESKITEM.DESK_BOOK);
        condition1.setStatue(getActivity().getResources().getString(R.string.book_desk));
        statues.add(condition1);

        CONDITIONITEM condition2 = new CONDITIONITEM();
        condition2.setStatueCode(DESKITEM.DESK_FULL);
        condition2.setStatue(getActivity().getResources().getString(R.string.full_desk));
        statues.add(condition2);
    }

    private void initView() {
        //init area menu
        final ListView areaView = new ListView(getActivity());
        areaAdapter = new GirdDropDownAdapter(getActivity(), areas);
        areaView.setDividerHeight(0);
        areaView.setAdapter(areaAdapter);

        //init statue menu
        final ListView statueView = new ListView(getActivity());
        statueView.setDividerHeight(0);
        statueAdapter = new ListDropDownAdapter(getActivity(), statues);
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
                selectItems.clear();
                areaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : areas.get(position).getStatue());
                mDropDownMenu.closeMenu();

                cateId = areas.get(position).getStatueCode();
                for (int i = 0; i < deskitems.size(); i++) {

                    if (cateId != 0 && stateCode != 3) {
                        if (deskitems.get(i).getDeskCateId() == cateId) {
                            if (deskitems.get(i).getDeskStatue() == stateCode) {
                                selectItems.add(deskitems.get(i));
                            }
                        }
                    } else if (cateId == 0 && stateCode != 3) {
                        if (deskitems.get(i).getDeskStatue() == stateCode) {
                            selectItems.add(deskitems.get(i));
                        }
                    } else if (cateId != 0 && stateCode == 3) {
                        if (deskitems.get(i).getDeskCateId() == cateId) {
                            selectItems.add(deskitems.get(i));
                        }
                    } else {
                        selectItems.add(deskitems.get(i));
                    }
                }
                deskAdapter.notifyDataSetChanged();
            }
        });

        statueView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItems.clear();
                statueAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : statues.get(position).getStatue());
                mDropDownMenu.closeMenu();

                stateCode = statues.get(position).getStatueCode();
                for (int i = 0; i < deskitems.size(); i++) {

                    if (cateId != 0 && stateCode != 3) {
                        if (deskitems.get(i).getDeskCateId() == cateId) {
                            if (deskitems.get(i).getDeskStatue() == stateCode) {
                                selectItems.add(deskitems.get(i));
                            }
                        }
                    } else if (cateId == 0 && stateCode != 3) {
                        if (deskitems.get(i).getDeskStatue() == stateCode) {
                            selectItems.add(deskitems.get(i));
                        }
                    } else if (cateId != 0 && stateCode == 3) {
                        if (deskitems.get(i).getDeskCateId() == cateId) {
                            selectItems.add(deskitems.get(i));
                        }
                    } else {
                        selectItems.add(deskitems.get(i));
                    }
                }
                deskAdapter.notifyDataSetChanged();
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
        deskAdapter = new DeskAdapter(getActivity(), selectItems);
        deskGridView.setAdapter(deskAdapter);
        deskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("deskInfo", selectItems.get(i));
                IntentUtils.startToActivityWithBundle(getActivity(), DeskDetailActivity.class, bundle);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });

        deskGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                statueIndex = selectItems.get(i).getDeskStatue();
                final SMDialog dh = new SMDialog(getActivity());
                dh.setTitle("请选择状态");
                dh.setRadioGroup(Public.statues, selectItems.get(i).getDeskStatue(), new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        statueIndex = checkedId - dh.TAG;
                    }
                });

                dh.setPositiveButton("确定", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        DeskModel.getInstance().changeDeskStatue(new ZHHttpCallBack<RESPONSE>() {
                            @Override
                            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                                if (response.getStatus() == 0) {
                                    ToastWidgt.showWithInfo(getActivity(), response.getMsg(), Toast.LENGTH_SHORT);
                                    selectItems.get(i).setDeskStatue(statueIndex);
                                    deskAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

                            }
                        }, selectItems.get(i).getDeskId(), statueIndex);

                        dh.dismiss();
                    }
                });

                dh.setNegativeButton("取消", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dh.dismiss();
                    }
                });
                return true;
            }
        });
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, deskView);

    }

    private void initDeskItems() {
        selectItems.clear();
        DeskModel.getInstance().getDesk(new ZHHttpCallBack<RESPONSE<List<DESKCATEITEM>>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatus() == 0) {
                    ArrayList<DESKCATEITEM> deskcateitems = (ArrayList<DESKCATEITEM>) response.getData();
                    deskitems.clear();
                    for (int i = 0; i < deskcateitems.size(); i++) {
                        deskitems.addAll(deskcateitems.get(i).getDesks());
                        CONDITIONITEM condition = new CONDITIONITEM();
                        condition.setStatueCode(deskcateitems.get(i).getDeskCateId());
                        condition.setStatue(deskcateitems.get(i).getDeskCateName());
                        areas.add(condition);
                    }
                    selectItems.addAll(deskitems);
                    initView();
                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatus() == 1) {
                    ToastWidgt.showWithInfo(getActivity(), response.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        }, Integer.valueOf(UserModel.getInstance().getUserinfo().getShopId()));
    }

    @Override
    public void onResume() {
        super.onResume();
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
        setRightContent(R.drawable.refresh_icon);
    }

    @Override
    public void tapLeftNaviBar() {

    }

    @Override
    public void tapRightNaviBar() {
        hud.show();
        selectItems.clear();
        DeskModel.getInstance().getDesk(new ZHHttpCallBack<RESPONSE<List<DESKCATEITEM>>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatus() == 0) {
                    ArrayList<DESKCATEITEM> deskcateitems = (ArrayList<DESKCATEITEM>) response.getData();
                    deskitems.clear();
                    for (int i = 0; i < deskcateitems.size(); i++) {
                        deskitems.addAll(deskcateitems.get(i).getDesks());
                        CONDITIONITEM condition = new CONDITIONITEM();
                        condition.setStatueCode(deskcateitems.get(i).getDeskCateId());
                        condition.setStatue(deskcateitems.get(i).getDeskCateName());
                        areas.add(condition);
                    }
                    selectItems.addAll(deskitems);
                    deskAdapter.notifyDataSetChanged();
                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatus() == 1) {
                    ToastWidgt.showWithInfo(getActivity(), response.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        }, Integer.valueOf(UserModel.getInstance().getUserinfo().getShopId()));
    }

}
