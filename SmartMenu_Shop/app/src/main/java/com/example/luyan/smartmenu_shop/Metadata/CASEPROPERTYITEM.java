package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 10/06/2017.
 */

public class CASEPROPERTYITEM implements Parcelable{
    private long ruleId;//规格id
    private String ruleName;//规格名称
    private long id;//规格值id
    private String value;//规格值名称
    private boolean isSelected;//是否选中

    public CASEPROPERTYITEM() {

    }


    protected CASEPROPERTYITEM(Parcel in) {
        ruleId = in.readLong();
        ruleName = in.readString();
        id = in.readLong();
        value = in.readString();
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
        parcel.writeLong(ruleId);
        parcel.writeString(ruleName);
        parcel.writeLong(id);
        parcel.writeString(value);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
