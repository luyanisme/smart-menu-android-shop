package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 09/06/2017.
 */

public class CaseOrderedAdapter extends BaseAdapter {

    private ArrayList<CASEITEM> caseItems;
    private LayoutInflater mInflater;

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
        CaseOrderedAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.case_ordered_list_item, null);
            holder = new CaseOrderedAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.caseName = (TextView) convertView.findViewById(R.id.case_name);
            holder.casePrice = (TextView) convertView.findViewById(R.id.case_price);
            holder.caseNum = (TextView) convertView.findViewById(R.id.case_num);
            holder.standardProperty = (TextView) convertView.findViewById(R.id.standard_property);
            holder.add = (RelativeLayout) convertView.findViewById(R.id.add_icon);
            holder.reduce = (RelativeLayout) convertView.findViewById(R.id.reduce_icon);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (CaseOrderedAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.caseName.setText(caseItems.get(position).getCaseName());
        holder.casePrice.setText(String.valueOf(caseItems.get(position).getCasePrice()*caseItems.get(position).getOrderNum())+"¥");
        holder.caseNum.setText("x"+caseItems.get(position).getOrderNum());
        if (caseItems.get(position).getStandardDesc() != null) {
            holder.standardProperty.setVisibility(View.VISIBLE);
            holder.standardProperty.setText(caseItems.get(position).getStandardDesc());
        } else {
            holder.standardProperty.setVisibility(View.GONE);
        }
        holder.add.setVisibility(View.GONE);
        holder.reduce.setVisibility(View.GONE);
        return convertView;
    }

    public CaseOrderedAdapter(Context mContext, ArrayList<CASEITEM> caseItems){
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

    public ArrayList<CASEITEM> getCaseItems() {
        return caseItems;
    }

    public void setCaseItems(ArrayList<CASEITEM> caseItems) {
        this.caseItems = caseItems;
    }
}
