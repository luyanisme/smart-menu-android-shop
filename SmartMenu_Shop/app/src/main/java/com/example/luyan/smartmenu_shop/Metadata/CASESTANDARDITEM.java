package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 10/06/2017.
 */

public class CASESTANDARDITEM implements Parcelable{
    private long ruleId;//规格id
    private String ruleName;//规格名称
    private long id;//规格值id
    private String value;//规格值名称
    private float casePrice;//规格价格
    private boolean isSelected;//是否选中

    public CASESTANDARDITEM() {

    }


    protected CASESTANDARDITEM(Parcel in) {
        ruleId = in.readLong();
        ruleName = in.readString();
        id = in.readLong();
        value = in.readString();
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
        parcel.writeLong(ruleId);
        parcel.writeString(ruleName);
        parcel.writeLong(id);
        parcel.writeString(value);
        parcel.writeFloat(casePrice);
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
