package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 31/07/2017.
 */

public class CONDITIONITEM implements Parcelable{
    private int statueCode;//状态码
    private String statue;//状态

    public CONDITIONITEM(Parcel in) {
        statueCode = in.readInt();
        statue = in.readString();
    }

    public CONDITIONITEM() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(statueCode);
        dest.writeString(statue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CONDITIONITEM> CREATOR = new Creator<CONDITIONITEM>() {
        @Override
        public CONDITIONITEM createFromParcel(Parcel in) {
            return new CONDITIONITEM(in);
        }

        @Override
        public CONDITIONITEM[] newArray(int size) {
            return new CONDITIONITEM[size];
        }
    };

    public int getStatueCode() {
        return statueCode;
    }

    public void setStatueCode(int statueCode) {
        this.statueCode = statueCode;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }
}
