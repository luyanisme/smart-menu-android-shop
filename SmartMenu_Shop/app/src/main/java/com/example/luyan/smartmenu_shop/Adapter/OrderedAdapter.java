package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Common.Public;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 16/06/2017.
 */

public class OrderedAdapter extends BaseAdapter {
    private ArrayList<CASEITEM> items;
    private LayoutInflater mInflater;
    private TapDelegate tapDelegate;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        OrderedAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ordered_list_item, null);
            holder = new OrderedAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.caseName = (TextView) convertView.findViewById(R.id.case_name);
            holder.standardDesc = (TextView) convertView.findViewById(R.id.standard_property);
            holder.casePrice = (TextView) convertView.findViewById(R.id.case_price);
            holder.orderedNum = (TextView) convertView.findViewById(R.id.order_num);
            holder.add = (RelativeLayout) convertView.findViewById(R.id.add_icon);
            holder.reduce = (RelativeLayout) convertView.findViewById(R.id.reduce_icon);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (OrderedAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.caseName.setText(items.get(position).getCaseName());
        holder.casePrice.setText("¥" + items.get(position).getOrderNum() * items.get(position).getCasePrice());
        holder.orderedNum.setText(String.valueOf(items.get(position).getOrderNum()));
        if (items.get(position).getStandardDesc() != null) {
            holder.standardDesc.setVisibility(View.VISIBLE);
            holder.standardDesc.setText(items.get(position).getStandardDesc());
        } else {
            holder.standardDesc.setVisibility(View.GONE);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderNum = items.get(position).getOrderNum();
                orderNum ++;
                Public.totalOrderNum ++;
                items.get(position).setOrderNum(orderNum);
                if (tapDelegate != null) {
                    tapDelegate.tapAdd(items.get(position));
                }
                notifyDataSetChanged();
            }
        });

        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderNum = items.get(position).getOrderNum();
                orderNum --;
                Public.totalOrderNum --;
                items.get(position).setOrderNum(orderNum);
                if (tapDelegate != null) {
                    tapDelegate.tapReduce(items.get(position));
                }
                if (orderNum == 0){
                    items.remove(position);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public OrderedAdapter(Context mContext, ArrayList<CASEITEM> items) {
        this.mInflater = LayoutInflater.from(mContext);
        this.items = items;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView caseName;
        public TextView standardDesc;
        public TextView orderedNum;
        public TextView casePrice;
        public RelativeLayout add;//增加
        public RelativeLayout reduce;//减少
    }

    public TapDelegate getTapDelegate() {
        return tapDelegate;
    }

    public void setTapDelegate(TapDelegate tapDelegate) {
        this.tapDelegate = tapDelegate;
    }

    public interface TapDelegate {
        public void tapReduce(CASEITEM caseitem);
        public void tapAdd(CASEITEM caseitem);
    }

}
