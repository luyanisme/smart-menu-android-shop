package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyan on 21/06/2017.
 */

public class RESPONSE<T> implements Parcelable{
    private int statue;//返回状态信息
    private String msg;//返回信息
    private ArrayList<T> data;//返回数据

    protected RESPONSE(Parcel in) {
        statue = in.readInt();
        msg = in.readString();
    }

    public static final Creator<RESPONSE> CREATOR = new Creator<RESPONSE>() {
        @Override
        public RESPONSE createFromParcel(Parcel in) {
            return new RESPONSE(in);
        }

        @Override
        public RESPONSE[] newArray(int size) {
            return new RESPONSE[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(statue);
        parcel.writeString(msg);
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
