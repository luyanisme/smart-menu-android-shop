package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 10/06/2017.
 */

public class CASEPROPERTYITEM implements Parcelable{
    private long casePropertyId;//规格id
    private String casePropertyName;//规格名称
    private long casePropertyValId;//规格值id
    private String casePropertyValue;//规格值名称
    private boolean isSelected;//是否选中

    public CASEPROPERTYITEM() {

    }


    protected CASEPROPERTYITEM(Parcel in) {
        casePropertyId = in.readLong();
        casePropertyName = in.readString();
        casePropertyValId = in.readLong();
        casePropertyValue = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<CASEPROPERTYITEM> CREATOR = new Creator<CASEPROPERTYITEM>() {
        @Override
        public CASEPROPERTYITEM createFromParcel(Parcel in) {
            return new CASEPROPERTYITEM(in);
        }

        @Override
        public CASEPROPERTYITEM[] newArray(int size) {
            return new CASEPROPERTYITEM[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(casePropertyId);
        parcel.writeString(casePropertyName);
        parcel.writeLong(casePropertyValId);
        parcel.writeString(casePropertyValue);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }

    public long getCasePropertyId() {
        return casePropertyId;
    }

    public void setCasePropertyId(long casePropertyId) {
        this.casePropertyId = casePropertyId;
    }

    public String getCasePropertyName() {
        return casePropertyName;
    }

    public void setCasePropertyName(String casePropertyName) {
        this.casePropertyName = casePropertyName;
    }

    public long getCasePropertyValId() {
        return casePropertyValId;
    }

    public void setCasePropertyValId(long casePropertyValId) {
        this.casePropertyValId = casePropertyValId;
    }

    public String getCasePropertyValue() {
        return casePropertyValue;
    }

    public void setCasePropertyValue(String casePropertyValue) {
        this.casePropertyValue = casePropertyValue;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
