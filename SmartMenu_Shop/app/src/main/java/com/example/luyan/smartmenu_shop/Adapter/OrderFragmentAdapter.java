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
 * Created by luyan on 31/05/2017.
 */

public class OrderFragmentAdapter extends BaseAdapter {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.order_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.deskNum = (TextView) convertView.findViewById(R.id.desk_num);
            holder.dateTime = (TextView) convertView.findViewById(R.id.date_time);
            holder.isDealedImg = (ImageView) convertView.findViewById(R.id.is_dealed);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.deskNum.setText(items.get(position).getDeskNum());
        holder.dateTime.setText("创建于"+items.get(position).getDateTime());
        if (items.get(position).isOrderIsDealed()){
            holder.isDealedImg.setImageResource(R.drawable.dealed_icon);
        } else {
            holder.isDealedImg.setImageResource(R.drawable.undeal_icon);
        }
        return convertView;
    }

    public OrderFragmentAdapter(Context mContext, ArrayList<ORDERITEM> items){
        this.mInflater = LayoutInflater.from(mContext);
        this.items = items;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView deskNum;
        public TextView dateTime;
        public ImageView isDealedImg;
    }
}
