package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Activity.BaseActivity;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 22/08/2017.
 */

public class OrderAdapter extends BaseAdapter{

    private ArrayList<CASEITEM> caseItems;
    private LayoutInflater mInflater;
    private Context context;
    private OrderAdapter.TapDelegate tapDelegate;

    @Override
    public int getCount() {
        return caseItems.size();
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
        OrderAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.case_ordered_list_item, null);
            holder = new OrderAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.caseName = (TextView) convertView.findViewById(R.id.case_name);
            holder.casePrice = (TextView) convertView.findViewById(R.id.case_price);
            holder.caseNum = (TextView) convertView.findViewById(R.id.case_num);
            holder.standardProperty = (TextView) convertView.findViewById(R.id.standard_property);
            holder.add = (RelativeLayout) convertView.findViewById(R.id.add_icon);
            holder.reduce = (RelativeLayout) convertView.findViewById(R.id.reduce_icon);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (OrderAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.caseName.setText((position+1) + "."+ caseItems.get(position).getCaseName());
        holder.casePrice.setText(String.valueOf(caseItems.get(position).getCasePrice()*caseItems.get(position).getOrderNum())+"¥");
        if (caseItems.get(position).getStandardDesc() != null) {
            holder.standardProperty.setVisibility(View.VISIBLE);
            holder.standardProperty.setText(caseItems.get(position).getStandardDesc());
        } else {
            holder.standardProperty.setVisibility(View.GONE);
        }
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderNum = caseItems.get(position).getOrderNum();
                orderNum ++;
                caseItems.get(position).setOrderNum(orderNum);
                if (tapDelegate != null) {
                    tapDelegate.tapAdd(caseItems.size());
                }
                notifyDataSetChanged();
            }
        });
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderNum = caseItems.get(position).getOrderNum();
                orderNum --;
                if (orderNum == 0){
                    caseItems.remove(position);
                } else {
                    caseItems.get(position).setOrderNum(orderNum);
                }
                if (tapDelegate != null) {
                    tapDelegate.tapReduce(caseItems.size());
                }
                notifyDataSetChanged();
            }
        });
        if (caseItems.get(position).isOrdered()){
            holder.caseNum.setText("x"+caseItems.get(position).getOrderNum());
            holder.caseName.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.casePrice.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.caseNum.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.add.setVisibility(View.GONE);
            holder.reduce.setVisibility(View.GONE);
        } else {
            holder.caseNum.setText(""+caseItems.get(position).getOrderNum());
            holder.caseName.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.casePrice.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.caseNum.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.add.setVisibility(View.VISIBLE);
            holder.reduce.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public OrderAdapter(Context mContext, ArrayList<CASEITEM> caseItems){
        this.context = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.caseItems = caseItems;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView caseName;
        public TextView casePrice;
        public TextView caseNum;
        public TextView standardProperty;
        public RelativeLayout add;//增加
        public RelativeLayout reduce;//减少
    }

    public TapDelegate getTapDelegate() {
        return tapDelegate;
    }

    public void setTapDelegate(TapDelegate tapDelegate) {
        this.tapDelegate = tapDelegate;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface TapDelegate {
        public void tapReduce(int caseNums);
        public void tapAdd(int caseNums);
    }

    public ArrayList<CASEITEM> getCaseItems() {
        return caseItems;
    }

    public void setCaseItems(ArrayList<CASEITEM> caseItems) {
        this.caseItems = caseItems;
    }
}
