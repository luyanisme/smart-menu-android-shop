package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 19/07/2017.
 */

public class RESULT implements Parcelable{
    private Integer statue;
    private String msg;
    private String data;

    protected RESULT(Parcel in) {
        msg = in.readString();
        data = in.readString();
    }

    public static final Creator<RESULT> CREATOR = new Creator<RESULT>() {
        @Override
        public RESULT createFromParcel(Parcel in) {
            return new RESULT(in);
        }

        @Override
        public RESULT[] newArray(int size) {
            return new RESULT[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msg);
        parcel.writeString(data);
    }

    public Integer getStatue() {
        return statue;
    }

    public void setStatue(Integer statue) {
        this.statue = statue;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
