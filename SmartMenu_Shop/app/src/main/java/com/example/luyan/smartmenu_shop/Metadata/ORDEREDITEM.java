package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by luyan on 21/06/2017.
 */

public class ORDEREDITEM implements Parcelable{
    private Long shopId;
    private Long deskId;
    private Long deskCateId;
    private ArrayList<CASEITEM> orderedItems;

    public ORDEREDITEM() {

    }

    protected ORDEREDITEM(Parcel in) {
        orderedItems = in.createTypedArrayList(CASEITEM.CREATOR);
    }

    public static final Creator<ORDEREDITEM> CREATOR = new Creator<ORDEREDITEM>() {
        @Override
        public ORDEREDITEM createFromParcel(Parcel in) {
            return new ORDEREDITEM(in);
        }

        @Override
        public ORDEREDITEM[] newArray(int size) {
            return new ORDEREDITEM[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(orderedItems);
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public Long getDeskCateId() {
        return deskCateId;
    }

    public void setDeskCateId(Long deskCateId) {
        this.deskCateId = deskCateId;
    }

    public ArrayList<CASEITEM> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(ArrayList<CASEITEM> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
