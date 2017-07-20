package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.Metadata.CASECATEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 10/06/2017.
 */

public class CateAdapter extends BaseAdapter {
    private ArrayList<CASECATEITEM> casecateitems;
    private LayoutInflater mInflater;
    private Context mContext;

    @Override
    public int getCount() {
        return casecateitems.size();
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
        CateAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cate_list_item, null);
            holder = new CateAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.bg = (RelativeLayout) convertView.findViewById(R.id.bg);
            holder.cateName = (TextView) convertView.findViewById(R.id.cate_name);
            holder.chooseTag = (ImageView) convertView.findViewById(R.id.choose_tag);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (CateAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.cateName.setText(casecateitems.get(position).getCaseTypeName());
        if (casecateitems.get(position).isSelected()) {
            holder.chooseTag.setVisibility(View.VISIBLE);
            holder.cateName.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            holder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            holder.chooseTag.setVisibility(View.GONE);
            holder.cateName.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.bg.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        return convertView;
    }

    public CateAdapter(Context mContext, ArrayList<CASECATEITEM> casecateitems) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.casecateitems = casecateitems;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView cateName;
        public ImageView chooseTag;
        public RelativeLayout bg;
    }
}
