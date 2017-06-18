package com.example.luyan.smartmenu_shop.Widgt;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Common.Public;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEPROPERTYITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASESTANDARDITEM;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.ArrayUtils;
import com.example.luyan.smartmenu_shop.Utils.DHDensityUtil;

import java.util.ArrayList;

import cn.lankton.flowlayout.FlowLayout;

/**
 * Created by luyan on 12/06/2017.
 */

public class ChooseStandardPanel implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private LinearLayout standardsContent;
    private TextView standardProperty;
    private TextView casePrice;
    private TextView caseName;
    private RelativeLayout orderNumOperate;
    private TextView addToCart;
    private int totalNum = 0;//各种不同属性商品总和
    private RelativeLayout add;//增加
    private RelativeLayout reduce;//减少
    private TextView orderNumView;
    private String standardStr = "";
    private String propertyStr = "";
    private CASESTANDARDITEM choosecasestandarditem;//已选择的规格
    private CASEPROPERTYITEM choosecasepropertyitem;//已选择的属性
    private CASEITEM caseItem;
    private int caseIndex;
    private TextView orderTag;
    private ArrayList<CASEITEM> orderedItems = new ArrayList<>();
    ArrayList<TextView> standardTextViews = new ArrayList<>();
    ArrayList<TextView> propertyTextViews = new ArrayList<>();

    TapDelegate tapDelegate;

    public ChooseStandardPanel(Context context, final CASEITEM caseItem) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.caseItem = caseItem;
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        dialog.setContentView(R.layout.choose_standard_panel);
        standardsContent = (LinearLayout) dialog.findViewById(R.id.standards_content);
        standardProperty = (TextView) dialog.findViewById(R.id.standard_property);
        casePrice = (TextView) dialog.findViewById(R.id.case_price);
        caseName = (TextView) dialog.findViewById(R.id.case_name);
        caseName.setText(caseItem.getCaseName());
        orderNumOperate = (RelativeLayout) dialog.findViewById(R.id.order_num_operate);
        addToCart = (TextView) dialog.findViewById(R.id.add_to_chart);
        addToCart.setOnClickListener(this);
        add = (RelativeLayout) dialog.findViewById(R.id.add_icon);
        add.setOnClickListener(this);
        reduce = (RelativeLayout) dialog.findViewById(R.id.reduce_icon);
        reduce.setOnClickListener(this);
        orderNumView = (TextView) dialog.findViewById(R.id.case_order_num);
        if (caseItem.getOrderCases().size() != 0){
            Long propertyId = caseItem.getOrderCases().get(caseItem.getOrderCases().size()-1).getCasepropertyitem().getCasePropertyValId();
            Long standardId = caseItem.getOrderCases().get(caseItem.getOrderCases().size()-1).getCasestandarditem().getCaseStandardValId();
            ArrayUtils.findPropertyId(propertyId,caseItem.getCasePropertyVals()).setSelected(true);
            ArrayUtils.findStandardId(standardId,caseItem.getCaseStandardVals()).setSelected(true);
            choosecasestandarditem = ArrayUtils.findStandardId(standardId,caseItem.getCaseStandardVals());
            choosecasepropertyitem = ArrayUtils.findPropertyId(propertyId,caseItem.getCasePropertyVals());
            addToCart.setVisibility(View.GONE);
            orderNumOperate.setVisibility(View.VISIBLE);
            orderNumView.setText(String.valueOf(caseItem.getOrderCases().get(caseItem.getOrderCases().size()-1).getOrderNum()));
        } else {
            caseItem.getCaseStandardVals().get(0).setSelected(true);
            caseItem.getCasePropertyVals().get(0).setSelected(true);
            choosecasestandarditem = caseItem.getCaseStandardVals().get(0);
            choosecasepropertyitem = caseItem.getCasePropertyVals().get(0);
        }

        if (caseItem.getOrderNum() != 0){
            totalNum = caseItem.getOrderNum();
        }
        initFlowLayout(caseItem);

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                for (int i = 0; i < caseItem.getCaseStandardVals().size(); i++) {
                    caseItem.getCaseStandardVals().get(i).setSelected(false);
                }
                for (int i = 0; i < caseItem.getCasePropertyVals().size(); i++) {
                    caseItem.getCasePropertyVals().get(i).setSelected(false);
                }
            }
        });
    }

    private void initFlowLayout(CASEITEM caseItem) {

        if (caseItem.getCaseStandardVals().size() != 0) {
            TextView standardName = new TextView(context);
            standardName.setText(caseItem.getCaseStandardVals().get(0).getCaseStandardName());
            standardName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(DHDensityUtil.dip2px(context, 10), 0, 0, 0);
            standardName.setLayoutParams(lp);
            standardsContent.addView(standardName);

            FlowLayout standardFlowLayout = new FlowLayout(context);
            standardFlowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            standardFlowLayout.setPadding(DHDensityUtil.dip2px(context, 10),
                    DHDensityUtil.dip2px(context, 10),
                    DHDensityUtil.dip2px(context, 10),
                    DHDensityUtil.dip2px(context, 10));
            for (int i = 0; i < caseItem.getCasePropertyVals().size(); i++) {
                int ranHeight = DHDensityUtil.dip2px(context, 25);
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
                mlp.setMargins(DHDensityUtil.dip2px(context, 5), 0, DHDensityUtil.dip2px(context, 5), DHDensityUtil.dip2px(context, 5));
                FLTextView tv = new FLTextView(context);
                tv.setPadding(DHDensityUtil.dip2px(context, 15), 0, DHDensityUtil.dip2px(context, 15), 0);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv.setText(caseItem.getCaseStandardVals().get(i).getCaseStandardValue());
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setLines(1);
                tv.setType(FLTextView.STANDARD);
                tv.setId(i);
                tv.setOnClickListener(this);
                if (caseItem.getCaseStandardVals().get(i).isSelected()) {
                    tv.setSelected(true);
                    standardStr = caseItem.getCaseStandardVals().get(i).getCaseStandardValue();
                    casePrice.setText("¥" + caseItem.getCaseStandardVals().get(i).getCasePrice());
                } else {
                    tv.setSelected(false);
                }
                standardTextViews.add(tv);
                standardFlowLayout.addView(tv, mlp);
            }
            standardsContent.addView(standardFlowLayout);
        }

        if (caseItem.getCasePropertyVals().size() != 0) {
            TextView propertyName = new TextView(context);
            propertyName.setText(caseItem.getCasePropertyVals().get(0).getCasePropertyName());
            propertyName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(DHDensityUtil.dip2px(context, 10), 0, 0, 0);
            propertyName.setLayoutParams(lp);
            standardsContent.addView(propertyName);

            FlowLayout propertyFlowLayout = new FlowLayout(context);
            propertyFlowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            propertyFlowLayout.setPadding(DHDensityUtil.dip2px(context, 10),
                    DHDensityUtil.dip2px(context, 10),
                    DHDensityUtil.dip2px(context, 10),
                    DHDensityUtil.dip2px(context, 10));
            for (int i = 0; i < caseItem.getCasePropertyVals().size(); i++) {
                int ranHeight = DHDensityUtil.dip2px(context, 25);
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
                mlp.setMargins(DHDensityUtil.dip2px(context, 5), 0, DHDensityUtil.dip2px(context, 5), DHDensityUtil.dip2px(context, 5));
                FLTextView tv = new FLTextView(context);
                tv.setPadding(DHDensityUtil.dip2px(context, 15), 0, DHDensityUtil.dip2px(context, 15), 0);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv.setText(caseItem.getCasePropertyVals().get(i).getCasePropertyValue());
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setType(FLTextView.PROPERTY);
                tv.setId(i);
                tv.setLines(1);
                tv.setOnClickListener(this);
                if (caseItem.getCasePropertyVals().get(i).isSelected()) {
                    tv.setSelected(true);
                    propertyStr = caseItem.getCasePropertyVals().get(i).getCasePropertyValue();
                } else {
                    tv.setSelected(false);
                }
                propertyTextViews.add(tv);
                propertyFlowLayout.addView(tv, mlp);
            }
            standardsContent.addView(propertyFlowLayout);
        }

        standardProperty.setText("(" + standardStr + "+" + propertyStr + ")");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_to_chart:
                int orderNum = Integer.valueOf(orderNumView.getText().toString());
                orderNum++;
                totalNum++;
                addToCart.setVisibility(View.GONE);
                orderNumOperate.setVisibility(View.VISIBLE);
                orderNumView.setText(String.valueOf(orderNum));
                if (tapDelegate != null) {
                    Public.totalOrderNum ++;
                    tapDelegate.tap(caseIndex, orderNum);
                }
                orderTag.setVisibility(View.VISIBLE);
                orderTag.setText(String.valueOf(totalNum));
                CASEITEM caseitem = weakCopy(caseItem);
                caseitem.setCasestandarditem(choosecasestandarditem);
                caseitem.setCasepropertyitem(choosecasepropertyitem);
                caseitem.setOrderNum(orderNum);
                caseitem.setStandardDesc("(" + standardStr + "+" + propertyStr + ")");
                caseItem.getOrderCases().add(caseitem);//添加到规格
                orderedItems.add(caseitem);//添加到总订单
                caseItem.setOrderNum(totalNum);
                break;

            case R.id.add_icon:
                int addOrderNum = Integer.valueOf(orderNumView.getText().toString());
                addOrderNum++;
                totalNum++;
                orderNumView.setText(String.valueOf(addOrderNum));
                if (tapDelegate != null) {
                    Public.totalOrderNum ++;
                    tapDelegate.tap(caseIndex, addOrderNum);
                }
                ArrayUtils.findStandardCaseItem(standardProperty.getText().toString(), caseItem.getOrderCases()).setOrderNum(addOrderNum);
                ArrayUtils.findStandardCaseItem(standardProperty.getText().toString(), orderedItems).setOrderNum(addOrderNum);
                orderTag.setText(String.valueOf(totalNum));
                caseItem.setOrderNum(totalNum);
                break;

            case R.id.reduce_icon:
                int reduceOrderNum = Integer.valueOf(orderNumView.getText().toString());
                reduceOrderNum--;
                totalNum--;
                orderNumView.setText(String.valueOf(reduceOrderNum));
                orderTag.setText(String.valueOf(totalNum));
                caseItem.setOrderNum(totalNum);
                //查找已添加的商品
                ArrayUtils.findStandardCaseItem(standardProperty.getText().toString(), caseItem.getOrderCases()).setOrderNum(reduceOrderNum);
                ArrayUtils.findStandardCaseItem(standardProperty.getText().toString(), orderedItems).setOrderNum(reduceOrderNum);
                if (reduceOrderNum == 0) {
                    addToCart.setVisibility(View.VISIBLE);
                    orderNumOperate.setVisibility(View.GONE);
                    //删除商品
                    caseItem.getOrderCases().remove(ArrayUtils.findStandardCaseItem(standardProperty.getText().toString(), caseItem.getOrderCases()));
                    orderedItems.remove(ArrayUtils.findStandardCaseItem(standardProperty.getText().toString(), orderedItems));
                    if(totalNum == 0){
                        orderTag.setVisibility(View.GONE);
                    }
                }
                if (tapDelegate != null) {
                    Public.totalOrderNum --;
                    tapDelegate.tap(caseIndex, reduceOrderNum);
                }
                break;
        }

        if (view instanceof FLTextView) {
            FLTextView flt = (FLTextView) view;
            switch (flt.getType()) {
                case 0:
                    for (int i = 0; i < standardTextViews.size(); i++) {
                        if (flt.getId() == i) {
                            standardTextViews.get(i).setSelected(true);
                            caseItem.getCaseStandardVals().get(i).setSelected(true);
                            choosecasestandarditem = caseItem.getCaseStandardVals().get(i);
                            standardStr = caseItem.getCaseStandardVals().get(i).getCaseStandardValue();
                            casePrice.setText("¥" + caseItem.getCaseStandardVals().get(i).getCasePrice());
                        } else {
                            standardTextViews.get(i).setSelected(false);
                            caseItem.getCaseStandardVals().get(i).setSelected(false);
                        }
                    }
                    break;

                case 1:
                    for (int i = 0; i < propertyTextViews.size(); i++) {
                        if (flt.getId() == i) {
                            propertyTextViews.get(i).setSelected(true);
                            caseItem.getCasePropertyVals().get(i).setSelected(true);
                            choosecasepropertyitem = caseItem.getCasePropertyVals().get(i);
                            propertyStr = caseItem.getCasePropertyVals().get(i).getCasePropertyValue();
                        } else {
                            propertyTextViews.get(i).setSelected(false);
                            caseItem.getCasePropertyVals().get(i).setSelected(false);
                        }
                    }
                    break;
            }

            standardProperty.setText("(" + standardStr + "+" + propertyStr + ")");
            CASEITEM result = ArrayUtils.findStandardCaseItem(standardProperty.getText().toString(), caseItem.getOrderCases());
            if (result != null) {
                addToCart.setVisibility(View.GONE);
                orderNumOperate.setVisibility(View.VISIBLE);
                orderNumView.setText(String.valueOf(result.getOrderNum()));
            } else {
                addToCart.setVisibility(View.VISIBLE);
                orderNumOperate.setVisibility(View.GONE);
                orderNumView.setText("0");
            }
        }
    }

    //浅拷贝
    private CASEITEM weakCopy(CASEITEM caseItem) {
        CASEITEM caseitem = new CASEITEM();
        caseitem.setCateIndex(caseItem.getCateIndex());
        caseitem.setCaseId(caseItem.getCaseId());
        caseitem.setCasePrice(caseItem.getCasePrice());
        caseitem.setCaseName(caseItem.getCaseName());
        caseitem.setCaseHot(caseItem.getCaseHot());
        caseitem.setCaseTypeId(caseItem.getCaseTypeId());
        caseitem.setCaseImagePath(caseItem.getCaseImagePath());
        caseitem.setCaseScaling(caseItem.isCaseScaling());
        caseitem.setCaseScaledNums(caseItem.getCaseScaledNums());
        caseitem.setUpdateTime(caseitem.getUpdateTime());
        return caseitem;
    }

    public TapDelegate getTapDelegate() {
        return tapDelegate;
    }

    public void setTapDelegate(TapDelegate tapDelegate) {
        this.tapDelegate = tapDelegate;
    }

    public interface TapDelegate {
        public void tap(int index, int orderNum);
    }

    public ArrayList<CASEITEM> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(ArrayList<CASEITEM> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public TextView getOrderTag() {
        return orderTag;
    }

    public void setOrderTag(TextView orderTag) {
        this.orderTag = orderTag;
    }

    public int getCaseIndex() {
        return caseIndex;
    }

    public void setCaseIndex(int caseIndex) {
        this.caseIndex = caseIndex;
    }

}
