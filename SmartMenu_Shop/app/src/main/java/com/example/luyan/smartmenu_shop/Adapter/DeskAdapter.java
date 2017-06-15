package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Metadata.DESKITEM;
import com.example.luyan.smartmenu_shop.Metadata.NOTICEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 07/06/2017.
 */

public class DeskAdapter extends BaseAdapter {

    private ArrayList<DESKITEM> deskItems;
    private LayoutInflater mInflater;

    @Override
    public int getCount() {
        return deskItems.size();
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
            convertView = mInflater.inflate(R.layout.desk_grid_item, null);
            holder = new DeskAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.mText = (TextView) convertView.findViewById(R.id.m_text);
            holder.mTopLine = (ImageView) convertView.findViewById(R.id.top_line);
            holder.mBadge = (ImageView) convertView.findViewById(R.id.badge);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        if (position > 3){
            holder.mTopLine.setVisibility(View.GONE);
        }
        switch (deskItems.get(position).getStatue()){
            case 0:
                holder.mBadge.setVisibility(View.GONE);
                break;

            case 1:
                holder.mBadge.setImageResource(R.drawable.badge_book_icon);
                break;

            case 2:
                holder.mBadge.setImageResource(R.drawable.badge_full_icon);
                break;
        }
        holder.mText.setText(deskItems.get(position).getDeskName());
        return convertView;
    }

    public DeskAdapter(Context mContext, ArrayList<DESKITEM> deskItems){
        this.mInflater = LayoutInflater.from(mContext);
        this.deskItems = deskItems;
    }
    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView mText;
        public ImageView mTopLine;
        public ImageView mBadge;
    }
}
