package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 02/06/2017.
 */

public class NOTICEITEM implements Parcelable {

    private String deskNum;//桌号
    private boolean isDealed;//是否处理过
    private boolean isReaded;//是否已读
    private String date;//时间
    private String noticeContent;//简述

    public NOTICEITEM(Parcel in) {
        deskNum = in.readString();
        isDealed = in.readByte() != 0;
        isReaded = in.readByte() != 0;
        date = in.readString();
        noticeContent = in.readString();
    }

    public static final Creator<NOTICEITEM> CREATOR = new Creator<NOTICEITEM>() {
        @Override
        public NOTICEITEM createFromParcel(Parcel in) {
            return new NOTICEITEM(in);
        }

        @Override
        public NOTICEITEM[] newArray(int size) {
            return new NOTICEITEM[size];
        }
    };

    public NOTICEITEM() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(deskNum);
        parcel.writeByte((byte) (isDealed ? 1 : 0));
        parcel.writeByte((byte) (isReaded ? 1 : 0));
        parcel.writeString(date);
        parcel.writeString(noticeContent);
    }

    public String getDeskNum() {
        return deskNum;
    }

    public void setDeskNum(String deskNum) {
        this.deskNum = deskNum;
    }

    public boolean isDealed() {
        return isDealed;
    }

    public void setDealed(boolean dealed) {
        isDealed = dealed;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
