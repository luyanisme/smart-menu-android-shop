package com.example.luyan.smartmenu_shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luyan.smartmenu_shop.Common.ServerConfig;
import com.example.luyan.smartmenu_shop.Metadata.CASECATEITEM;
import com.example.luyan.smartmenu_shop.Metadata.CASEITEM;
import com.example.luyan.smartmenu_shop.R;

import java.util.ArrayList;

/**
 * Created by luyan on 10/06/2017.
 */

public class CaseAdapter extends BaseAdapter {
    private ArrayList<CASEITEM> caseitems;
    private LayoutInflater mInflater;
    private Context context;
    TapDelegate tapDelegate;

    @Override
    public int getCount() {
        return caseitems.size();
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
        final CaseAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.case_list_item, null);
            holder = new CaseAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.caseName = (TextView) convertView.findViewById(R.id.case_name);
            holder.casePrice = (TextView) convertView.findViewById(R.id.case_price);
            holder.orderNum = (TextView) convertView.findViewById(R.id.case_order_num);
            holder.add = (RelativeLayout) convertView.findViewById(R.id.add_icon);
            holder.reduce = (RelativeLayout) convertView.findViewById(R.id.reduce_icon);
            holder.caseImage = (ImageView) convertView.findViewById(R.id.case_image);
            holder.orderNumOperate = (RelativeLayout) convertView.findViewById(R.id.order_num_operate);
            holder.chooseStandard = (RelativeLayout) convertView.findViewById(R.id.choose_standard);
            holder.standardOrderNum = (TextView) convertView.findViewById(R.id.standard_order_num);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (CaseAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        Glide
                .with(context)
                .load(ServerConfig.IMAGE_URL+caseitems.get(position).getCaseImagePath())
                .into(holder.caseImage);
        holder.caseName.setText(caseitems.get(position).getCaseName());
        holder.casePrice.setText("￥"+String.valueOf(caseitems.get(position).getCasePrice()));
        holder.reduce.setVisibility(View.GONE);
        holder.orderNum.setVisibility(View.GONE);
        if (caseitems.get(position).getCasePropertyVals().size() == 0 && caseitems.get(position).getCaseStandardVals().size() == 0){
            holder.chooseStandard.setVisibility(View.GONE);
            holder.orderNumOperate.setVisibility(View.VISIBLE);
            if (caseitems.get(position).getOrderNum() != 0){
                holder.orderNum.setText(String.valueOf(caseitems.get(position).getOrderNum()));
                holder.orderNum.setVisibility(View.VISIBLE);
                holder.reduce.setVisibility(View.VISIBLE);
            } else {
                holder.orderNum.setText(String.valueOf("0"));
            }
        } else {
            holder.chooseStandard.setVisibility(View.VISIBLE);
            holder.orderNumOperate.setVisibility(View.GONE);
            if (caseitems.get(position).getOrderNum() != 0){
                holder.standardOrderNum.setVisibility(View.VISIBLE);
                holder.standardOrderNum.setText(String.valueOf(caseitems.get(position).getOrderNum()));
            } else {
                holder.standardOrderNum.setVisibility(View.GONE);
                holder.standardOrderNum.setText(String.valueOf(0));
            }
        }

        holder.chooseStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tapDelegate != null){
                    tapDelegate.tapChooseStandard(position, holder.standardOrderNum);
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tapDelegate != null){
                    tapDelegate.tapAddButton(position, holder.orderNum, holder.reduce);
                }
            }
        });

        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tapDelegate != null){
                    tapDelegate.tapReduceButton(position, holder.orderNum, holder.reduce);
                }
            }
        });

        return convertView;
    }

    public CaseAdapter(Context mContext, ArrayList<CASEITEM> caseitems) {
        this.mInflater = LayoutInflater.from(mContext);
        this.context = mContext;
        this.caseitems = caseitems;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView orderNum;//购买数量
        public RelativeLayout add;//增加
        public RelativeLayout reduce;//减少
        public ImageView caseImage;//商品图片
        public TextView caseName;//商品名称
        public TextView casePrice;//商品价格
        public RelativeLayout orderNumOperate;//操作
        public RelativeLayout chooseStandard;//选择商品规格
        public TextView standardOrderNum;//已点数量
    }

    public ArrayList<CASEITEM> getCaseitems() {
        return caseitems;
    }

    public void setCaseitems(ArrayList<CASEITEM> caseitems) {
        this.caseitems = caseitems;
    }

    public TapDelegate getTapDelegate() {
        return tapDelegate;
    }

    public void setTapDelegate(TapDelegate tapDelegate) {
        this.tapDelegate = tapDelegate;
    }

    public interface TapDelegate {
        public void tapAddButton(int index, TextView orderNumView, RelativeLayout reduceView);
        public void tapReduceButton(int index, TextView orderNumView, RelativeLayout reduceView);
        public void tapChooseStandard(int index, TextView orderTag);
    }

}
