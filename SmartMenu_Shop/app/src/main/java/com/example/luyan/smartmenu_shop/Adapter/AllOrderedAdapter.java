package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Metadata.ORDERITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 29/08/2017.
 */

public class AllOrderedAdapter extends BaseAdapter {
    private ArrayList<ORDERITEM> items;
    private LayoutInflater mInflater;

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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        AllOrderedAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.all_ordered_list_item, null);
            holder = new AllOrderedAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.deskNum = (TextView) convertView.findViewById(R.id.desk_num);
            holder.dateTime = (TextView) convertView.findViewById(R.id.date_time);
            holder.totalPrice = (TextView) convertView.findViewById(R.id.total_price);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (AllOrderedAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.deskNum.setText(items.get(position).getDeskNum());
        holder.totalPrice.setText("总消费:"+items.get(position).getOrderPrice()+"¥");
        holder.dateTime.setText("创建于"+items.get(position).getDateTime());
        return convertView;
    }

    public AllOrderedAdapter(Context mContext, ArrayList<ORDERITEM> items){
        this.mInflater = LayoutInflater.from(mContext);
        this.items = items;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView deskNum;
        public TextView dateTime;
        public TextView totalPrice;
    }
}
