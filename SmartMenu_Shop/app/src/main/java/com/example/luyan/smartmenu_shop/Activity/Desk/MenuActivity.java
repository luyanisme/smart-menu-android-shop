package com.example.luyan.smartmenu_shop.Activity.Desk;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Adapter.CaseAdapter;
import com.example.luyan.smartmenu_shop.Adapter.CateAdapter;
import com.example.luyan.smartmenu_shop.Adapter.OrderedAdapter;
import com.example.luyan.smartmenu_shop.Common.Public;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Metadata.CASECATEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Model.MenuModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ActivityCollectorUtils;
import com.example.luyan.smartmenu_shop.Utils.ArrayUtils;
import com.example.luyan.smartmenu_shop.Widgt.ChooseStandardPanel;
import com.example.luyan.smartmenu_shop.Widgt.POPOrderView;
import com.example.luyan.smartmenu_shop.Widgt.SMDialog;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

public class MenuActivity extends BaseActivity implements CaseAdapter.TapDelegate, ChooseStandardPanel.TapDelegate, POPOrderView.TapDelegate, OrderedAdapter.TapDelegate {

    public static int REQUESTCODE = 0;

    private ListView cateList;//分类列表
    private ListView caseList;//商品列表
    private CateAdapter cateAdapter;
    private CaseAdapter caseAdapter;
    private ArrayList<CASECATEITEM> casecateitems;
    private ListView orderList;
    private TextView orderTag;
    private RelativeLayout listContent;
    private int currentIndex = 0;//当前index
    private boolean isShowShadow = false;
    private POPOrderView popOrderView;
    ArrayList<CASEITEM> caseitems = new ArrayList<>();
    ArrayList<CASEITEM> orderedItems = new ArrayList<>();//已选菜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_menu);

        listContent = (RelativeLayout) findViewById(R.id.list_content);
        orderTag = (TextView) findViewById(R.id.order_tag);
        findViewById(R.id.ordered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Public.totalOrderNum == 0) {
                    return;
                }
                if (popOrderView == null) {
                    popOrderView = new POPOrderView(MenuActivity.this, orderedItems, listContent);
                    popOrderView.getOrderedAdapter().setTapDelegate(MenuActivity.this);
                }
                popOrderView.setTapDelegate(MenuActivity.this);
                if (isShowShadow) {
                    popOrderView.dismiss();
                    isShowShadow = false;
                } else {
                    popOrderView.show();
                    isShowShadow = true;
                }
            }
        });

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("orderItems", orderedItems);
                setResult(REQUESTCODE, intent);
                ActivityCollectorUtils.pop();
            }
        });

        initData();
        ActivityCollectorUtils.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resotre();
    }

    private void initCateList() {
        cateList = (ListView) findViewById(R.id.cate_list);
        cateAdapter = new CateAdapter(this, casecateitems);
        cateList.setAdapter(cateAdapter);
        cateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == currentIndex) {
                    return;
                } else {
                    casecateitems.get(i).setSelected(true);
                    casecateitems.get(currentIndex).setSelected(false);
                    currentIndex = i;
                    cateAdapter.notifyDataSetChanged();
                    for (CASEITEM caseItem: casecateitems.get(currentIndex).getCases()) {
                        caseItem.setCateIndex(currentIndex);
                    }
                    caseAdapter.setCaseitems(casecateitems.get(currentIndex).getCases());
                    caseAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initCaseList() {
        caseList = (ListView) findViewById(R.id.case_list);
        caseitems = casecateitems.get(currentIndex).getCases();
        caseAdapter = new CaseAdapter(this, caseitems);
        caseAdapter.setTapDelegate(this);
        caseList.setAdapter(caseAdapter);
    }

    private void initData() {
        final KProgressHUD hud = KProgressHUD.create(MenuActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.waiting))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        hud.show();

        MenuModel.getInstance().getMenuList(new ZHHttpCallBack<RESPONSE<CASECATEITEM>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatue() == 0){
                    casecateitems = response.getData();
                    casecateitems.get(currentIndex).setSelected(true);
                    for (CASEITEM caseItem: casecateitems.get(currentIndex).getCases()) {
                        caseItem.setCateIndex(currentIndex);
                    }
                    initCateList();
                    initCaseList();
                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

            }
        });

    }

    /*重置*/
    private void resotre(){
        orderedItems.clear();
        if (casecateitems == null){
            return;
        }
        for (int i = 0; i < casecateitems.size(); i++) {
            for (CASEITEM caseItem :casecateitems.get(i).getCases()) {
                caseItem.setOrderNum(0);
                if (caseItem.getOrderCases().size() != 0){
                    caseItem.getOrderCases().clear();
                }
            }
        }
        Public.totalOrderNum = 0;
        setTagNum(Public.totalOrderNum);
        caseAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCenterTitle() {
        setTitleStr(getResources().getString(R.string.menu));
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
        if (Public.totalOrderNum != 0){
            final SMDialog sm = new SMDialog(MenuActivity.this);
            sm.setTitle(getResources().getString(R.string.tips));
            sm.setMessage("返回将清空订单列表");

            sm.setPositiveButton(getResources().getString(R.string.go_on), new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("orderItems", null);
                    setResult(REQUESTCODE, intent);
                    ActivityCollectorUtils.pop();
                    sm.dismiss();
                }
            });

            sm.setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    sm.dismiss();
                }
            });
        } else {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("orderItems", null);
            setResult(REQUESTCODE, intent);
            ActivityCollectorUtils.pop();
        }

    }

    @Override
    public void tapRightNaviBar(View view) {

    }

    /*商品数量添加减少*/
    @Override
    public void tapAddButton(int index, TextView orderNumView, RelativeLayout reduceView) {
        int orderNum = Integer.valueOf(orderNumView.getText().toString());
        if (orderNum == 0) {
            orderNum++;
            orderNumView.setVisibility(View.VISIBLE);
            reduceView.setVisibility(View.VISIBLE);
            orderNumView.setText(String.valueOf(orderNum));
            orderedItems.add(casecateitems.get(currentIndex).getCases().get(index));
        } else {
            orderNum++;
            orderNumView.setText(String.valueOf(orderNum));
        }
        Public.totalOrderNum++;
        casecateitems.get(currentIndex).getCases().get(index).setOrderNum(orderNum);
        setTagNum(Public.totalOrderNum);
    }

    @Override
    public void tapReduceButton(int index, TextView orderNumView, RelativeLayout reduceView) {
        int orderNum = Integer.valueOf(orderNumView.getText().toString());
        orderNum--;
        orderNumView.setText(String.valueOf(orderNum));
        if (orderNum == 0) {
            orderNumView.setVisibility(View.GONE);
            reduceView.setVisibility(View.GONE);
            orderedItems.remove(casecateitems.get(currentIndex).getCases().get(index));
        }
        Public.totalOrderNum--;
        casecateitems.get(currentIndex).getCases().get(index).setOrderNum(orderNum);
        setTagNum(Public.totalOrderNum);
    }

    @Override
    public void tapChooseStandard(int index, TextView orderTag) {
        ChooseStandardPanel csd = new ChooseStandardPanel(MenuActivity.this, casecateitems.get(currentIndex).getCases().get(index));
        csd.setCaseIndex(index);
        csd.setOrderTag(orderTag);
        csd.setOrderedItems(orderedItems);
        csd.setTapDelegate(this);
    }

    @Override
    /*ChooseStandardPanel点击事件的代理方法*/
    public void tap(int index, int orderNum) {
        setTagNum(Public.totalOrderNum);
    }

    private void setTagNum(int num) {
        if (num == 0) {
            orderTag.setVisibility(View.GONE);
        } else {
            orderTag.setVisibility(View.VISIBLE);
            orderTag.setText(String.valueOf(num));
        }
    }

    @Override
    public void clearOrders() {
        resotre();
        popOrderView.dismiss();
        isShowShadow = false;
    }

    /*已点单列表的添加和减少按钮*/
    @Override
    public void tapReduce(CASEITEM caseitem) {
        /*找出带有规格的case*/
        CASEITEM caseItem = ArrayUtils.findCaseId(caseitem, casecateitems.get(caseitem.getCateIndex()).getCases());
        if(caseItem.getOrderCases().size() != 0){
            caseItem.setOrderNum(caseItem.getOrderNum() - 1);
            if (caseitem.getOrderNum() == 0){
                caseItem.getOrderCases().remove(caseitem);
            }
        }
        caseAdapter.notifyDataSetChanged();
        if (Public.totalOrderNum == 0){
            popOrderView.dismiss();
        }
        setTagNum(Public.totalOrderNum);
    }

    @Override
    public void tapAdd(CASEITEM caseitem) {
        CASEITEM caseItem = ArrayUtils.findCaseId(caseitem, casecateitems.get(caseitem.getCateIndex()).getCases());
        if(caseItem.getOrderCases().size() != 0){
            caseItem.setOrderNum(caseItem.getOrderNum() + 1);
        }
        caseAdapter.notifyDataSetChanged();
        setTagNum(Public.totalOrderNum);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Public.totalOrderNum != 0){
                final SMDialog sm = new SMDialog(MenuActivity.this);
                sm.setTitle(getResources().getString(R.string.tips));
                sm.setMessage("返回将清空订单列表");

                sm.setPositiveButton(getResources().getString(R.string.go_on), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("orderItems", null);
                        setResult(REQUESTCODE, intent);
                        ActivityCollectorUtils.pop();
                        sm.dismiss();
                    }
                });

                sm.setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        sm.dismiss();
                    }
                });
            } else {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("orderItems", null);
                setResult(REQUESTCODE, intent);
                ActivityCollectorUtils.pop();
            }
        }
        return false;
    }
}
