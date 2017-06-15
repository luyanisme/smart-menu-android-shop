package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by luyan on 10/06/2017.
 */

public class CASECATEITEM implements Parcelable {

    private long caseTypeId;//商品Id
    private String caseTypeName;//商品名称
    private long caseNums;//商品数量
    private long caseOnNums;//商品上架数量
    private long caseOffNums;//商品下架数量
    private boolean caseTypeSaling;//商品是否上架
    private long shopId;//商店ID
    private String updateTime;//更新时间
    private ArrayList<CASEITEM> cases;
    private boolean isSelected = false;//是否选中


    public CASECATEITEM(Parcel in) {
        caseTypeId = in.readLong();
        caseTypeName = in.readString();
        caseNums = in.readLong();
        caseOnNums = in.readLong();
        caseOffNums = in.readLong();
        caseTypeSaling = in.readByte() != 0;
        shopId = in.readLong();
        updateTime = in.readString();
        cases = in.createTypedArrayList(CASEITEM.CREATOR);
        isSelected = in.readByte() != 0;
    }

    public static final Creator<CASECATEITEM> CREATOR = new Creator<CASECATEITEM>() {
        @Override
        public CASECATEITEM createFromParcel(Parcel in) {
            return new CASECATEITEM(in);
        }

        @Override
        public CASECATEITEM[] newArray(int size) {
            return new CASECATEITEM[size];
        }
    };

    public CASECATEITEM() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(caseTypeId);
        parcel.writeString(caseTypeName);
        parcel.writeLong(caseNums);
        parcel.writeLong(caseOnNums);
        parcel.writeLong(caseOffNums);
        parcel.writeByte((byte) (caseTypeSaling ? 1 : 0));
        parcel.writeLong(shopId);
        parcel.writeString(updateTime);
        parcel.writeTypedList(cases);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }

    public long getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(long caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public String getCaseTypeName() {
        return caseTypeName;
    }

    public void setCaseTypeName(String caseTypeName) {
        this.caseTypeName = caseTypeName;
    }

    public long getCaseNums() {
        return caseNums;
    }

    public void setCaseNums(long caseNums) {
        this.caseNums = caseNums;
    }

    public long getCaseOnNums() {
        return caseOnNums;
    }

    public void setCaseOnNums(long caseOnNums) {
        this.caseOnNums = caseOnNums;
    }

    public long getCaseOffNums() {
        return caseOffNums;
    }

    public void setCaseOffNums(long caseOffNums) {
        this.caseOffNums = caseOffNums;
    }

    public boolean isCaseTypeSaling() {
        return caseTypeSaling;
    }

    public void setCaseTypeSaling(boolean caseTypeSaling) {
        this.caseTypeSaling = caseTypeSaling;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList<CASEITEM> getCases() {
        return cases;
    }

    public void setCases(ArrayList<CASEITEM> cases) {
        this.cases = cases;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
