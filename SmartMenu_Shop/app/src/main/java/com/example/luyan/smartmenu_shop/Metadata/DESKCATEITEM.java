package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by luyan on 31/07/2017.
 */

public class DESKCATEITEM implements Parcelable{
    private Integer deskCateId;
    private String deskCateName;
    private Integer deskNum;
    private Integer deskUsingNum;
    private Integer deskRemainNum;
    private Integer shopId;
    private String updateTime;
    private ArrayList<DESKITEM> desks;

    protected DESKCATEITEM(Parcel in) {
        deskCateName = in.readString();
        updateTime = in.readString();
        desks = in.createTypedArrayList(DESKITEM.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deskCateName);
        dest.writeString(updateTime);
        dest.writeTypedList(desks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DESKCATEITEM> CREATOR = new Creator<DESKCATEITEM>() {
        @Override
        public DESKCATEITEM createFromParcel(Parcel in) {
            return new DESKCATEITEM(in);
        }

        @Override
        public DESKCATEITEM[] newArray(int size) {
            return new DESKCATEITEM[size];
        }
    };

    public Integer getDeskCateId() {
        return deskCateId;
    }

    public void setDeskCateId(Integer deskCateId) {
        this.deskCateId = deskCateId;
    }

    public String getDeskCateName() {
        return deskCateName;
    }

    public void setDeskCateName(String deskCateName) {
        this.deskCateName = deskCateName;
    }

    public Integer getDeskNum() {
        return deskNum;
    }

    public void setDeskNum(Integer deskNum) {
        this.deskNum = deskNum;
    }

    public Integer getDeskUsingNum() {
        return deskUsingNum;
    }

    public void setDeskUsingNum(Integer deskUsingNum) {
        this.deskUsingNum = deskUsingNum;
    }

    public Integer getDeskRemainNum() {
        return deskRemainNum;
    }

    public void setDeskRemainNum(Integer deskRemainNum) {
        this.deskRemainNum = deskRemainNum;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList<DESKITEM> getDesks() {
        return desks;
    }

    public void setDesks(ArrayList<DESKITEM> desks) {
        this.desks = desks;
    }
}
