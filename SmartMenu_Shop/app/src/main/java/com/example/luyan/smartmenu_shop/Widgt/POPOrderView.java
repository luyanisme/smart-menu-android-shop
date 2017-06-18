package com.example.luyan.smartmenu_shop.Widgt;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.luyan.smartmenu_shop.Activity.Desk.MenuActivity;
import com.example.luyan.smartmenu_shop.Adapter.OrderedAdapter;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 17/06/2017.
 */

public class POPOrderView {

    private ListView orderList;
    private View contentView;
    private RelativeLayout listContent;
    private OrderedAdapter orderedAdapter;
    private TapDelegate tapDelegate;

    public POPOrderView(Context context, ArrayList<CASEITEM> orderedItems, RelativeLayout belongView) {
        initPopWin(context, orderedItems, belongView);
    }

    private void initPopWin(Context context, ArrayList<CASEITEM> orderedItems, final RelativeLayout belongView) {
        this.listContent = belongView;
        if (contentView == null) {
            contentView = LayoutInflater.from(context).inflate(
                    R.layout.pop_list_content, null);
        }
        orderList = (ListView) contentView.findViewById(R.id.order_list);
        contentView.findViewById(R.id.pop_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*点击消失*/
                belongView.removeView(view);
            }
        });
        contentView.findViewById(R.id.clear_contain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        contentView.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tapDelegate != null) {
                    tapDelegate.clearOrders();
                }
            }
        });
        orderedAdapter = new OrderedAdapter(context, orderedItems);
        orderList.setAdapter(orderedAdapter);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        contentView.setBackground(new ColorDrawable(0xb0000000));
        contentView.setLayoutParams(params);
    }

    public void show() {
        listContent.addView(contentView);
    }

    public void dismiss() {
        listContent.removeView(contentView);
    }

    public TapDelegate getTapDelegate() {
        return tapDelegate;
    }

    public void setTapDelegate(TapDelegate tapDelegate) {
        this.tapDelegate = tapDelegate;
    }

    public interface TapDelegate {
        public void clearOrders();
    }

    public OrderedAdapter getOrderedAdapter() {
        return orderedAdapter;
    }

    public void setOrderedAdapter(OrderedAdapter orderedAdapter) {
        this.orderedAdapter = orderedAdapter;
    }
}
