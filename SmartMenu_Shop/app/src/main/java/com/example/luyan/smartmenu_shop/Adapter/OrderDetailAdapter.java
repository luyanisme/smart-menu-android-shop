package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 30/08/2017.
 */

public class OrderDetailAdapter extends BaseAdapter {
    private ArrayList<CASEITEM> caseItems;
    private LayoutInflater mInflater;
    private Context context;

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
        OrderDetailAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.case_ordered_list_item, null);
            holder = new OrderDetailAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.caseName = (TextView) convertView.findViewById(R.id.case_name);
            holder.casePrice = (TextView) convertView.findViewById(R.id.case_price);
            holder.caseNum = (TextView) convertView.findViewById(R.id.case_num);
            holder.standardProperty = (TextView) convertView.findViewById(R.id.standard_property);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (OrderDetailAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.caseName.setText((position + 1) + "." + caseItems.get(position).getCaseName());
        holder.casePrice.setText(String.valueOf(caseItems.get(position).getCasePrice() * caseItems.get(position).getOrderNum()) + "¥");
        if (caseItems.get(position).getStandardDesc() != null) {
            holder.standardProperty.setVisibility(View.VISIBLE);
            holder.standardProperty.setText(caseItems.get(position).getStandardDesc());
        } else {
            holder.standardProperty.setVisibility(View.GONE);
        }
        holder.caseNum.setText("x" + caseItems.get(position).getOrderNum());
        holder.caseName.setTextColor(context.getResources().getColor(R.color.colorText));
        holder.casePrice.setTextColor(context.getResources().getColor(R.color.colorText));
        holder.caseNum.setTextColor(context.getResources().getColor(R.color.colorText));

        return convertView;
    }

    public OrderDetailAdapter(Context mContext, ArrayList<CASEITEM> caseItems) {
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


    public ArrayList<CASEITEM> getCaseItems() {
        return caseItems;
    }

    public void setCaseItems(ArrayList<CASEITEM> caseItems) {
        this.caseItems = caseItems;
    }
}
