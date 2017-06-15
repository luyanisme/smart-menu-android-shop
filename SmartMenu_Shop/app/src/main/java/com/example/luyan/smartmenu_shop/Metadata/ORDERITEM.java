package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by luyan on 31/05/2017.
 */

public class ORDERITEM implements Parcelable {
    private String deskNum;//桌号
    private boolean isDealed;//是否处理过
    private boolean isReaded;//是否已读
    private String statue;
    private String date;//时间
    private String desc;//简述
    private ArrayList orderContent;//订单内容

    public ORDERITEM(Parcel in) {
        deskNum = in.readString();
        isDealed = in.readByte() != 0;
        statue = in.readString();
        date = in.readString();
        desc = in.readString();
    }

    public static final Creator<ORDERITEM> CREATOR = new Creator<ORDERITEM>() {
        @Override
        public ORDERITEM createFromParcel(Parcel in) {
            return new ORDERITEM(in);
        }

        @Override
        public ORDERITEM[] newArray(int size) {
            return new ORDERITEM[size];
        }
    };

    public ORDERITEM() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(deskNum);
        parcel.writeByte((byte) (isDealed ? 1 : 0));
        parcel.writeString(statue);
        parcel.writeString(date);
        parcel.writeString(desc);
    }

    public String getDeskNum() {
        return deskNum;
    }

    public void setDeskNum(String deskNum) {
        this.deskNum = deskNum;
    }

    public boolean isDealed() {
        return isDealed;
    }

    public void setDealed(boolean dealed) {
        isDealed = dealed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(ArrayList orderContent) {
        this.orderContent = orderContent;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }
}
