package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 10/06/2017.
 */

public class CASESTANDARDITEM implements Parcelable{
    private long caseStandardId;//规格id
    private String caseStandardName;//规格名称
    private long caseStandardValId;//规格值id
    private String caseStandardValue;//规格值名称
    private float casePrice;//规格价格
    private boolean isSelected;//是否选中

    public CASESTANDARDITEM() {

    }

    protected CASESTANDARDITEM(Parcel in) {
        caseStandardId = in.readLong();
        caseStandardName = in.readString();
        caseStandardValId = in.readLong();
        caseStandardValue = in.readString();
        casePrice = in.readFloat();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<CASESTANDARDITEM> CREATOR = new Creator<CASESTANDARDITEM>() {
        @Override
        public CASESTANDARDITEM createFromParcel(Parcel in) {
            return new CASESTANDARDITEM(in);
        }

        @Override
        public CASESTANDARDITEM[] newArray(int size) {
            return new CASESTANDARDITEM[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(caseStandardId);
        parcel.writeString(caseStandardName);
        parcel.writeLong(caseStandardValId);
        parcel.writeString(caseStandardValue);
        parcel.writeFloat(casePrice);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }

    public long getCaseStandardId() {
        return caseStandardId;
    }

    public void setCaseStandardId(long caseStandardId) {
        this.caseStandardId = caseStandardId;
    }

    public String getCaseStandardName() {
        return caseStandardName;
    }

    public void setCaseStandardName(String caseStandardName) {
        this.caseStandardName = caseStandardName;
    }

    public long getCaseStandardValId() {
        return caseStandardValId;
    }

    public void setCaseStandardValId(long caseStandardValId) {
        this.caseStandardValId = caseStandardValId;
    }

    public String getCaseStandardValue() {
        return caseStandardValue;
    }

    public void setCaseStandardValue(String caseStandardValue) {
        this.caseStandardValue = caseStandardValue;
    }

    public float getCasePrice() {
        return casePrice;
    }

    public void setCasePrice(float casePrice) {
        this.casePrice = casePrice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
