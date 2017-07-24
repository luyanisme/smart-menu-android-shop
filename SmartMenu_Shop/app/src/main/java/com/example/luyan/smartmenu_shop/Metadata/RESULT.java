package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 19/07/2017.
 */

public class RESULT implements Parcelable{

    private int noticeType;//消息类型
    private int callbackNoticeType;//消息类型
    private int statue;//消息成功还是失败0,1
    private String msg;//返回的消息内容


    protected RESULT(Parcel in) {
        noticeType = in.readInt();
        callbackNoticeType = in.readInt();
        statue = in.readInt();
        msg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noticeType);
        dest.writeInt(callbackNoticeType);
        dest.writeInt(statue);
        dest.writeString(msg);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public int getCallbackNoticeType() {
        return callbackNoticeType;
    }

    public void setCallbackNoticeType(int callbackNoticeType) {
        this.callbackNoticeType = callbackNoticeType;
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
}
