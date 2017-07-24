package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 02/06/2017.
 */

public class NOTICEITEM implements Parcelable {

    private Integer clientType = 1;//客户端类型
    private String noticeKey;//消息的唯一标识
    private Long noticeId;//店铺id
    private Long shopId;//店铺id
    private Long noticeType;//消息类型
    private Long deskId;//桌面id
    private String deskNum;//桌号
    private boolean noticeIsDealed;//是否处理过
    private String dateTime;//时间
    private String noticeContent;//简述

    private boolean isSendTag;//是否为发送标识的notice
    public NOTICEITEM() {

    }


    protected NOTICEITEM(Parcel in) {
        noticeKey = in.readString();
        deskNum = in.readString();
        noticeIsDealed = in.readByte() != 0;
        dateTime = in.readString();
        noticeContent = in.readString();
        isSendTag = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noticeKey);
        dest.writeString(deskNum);
        dest.writeByte((byte) (noticeIsDealed ? 1 : 0));
        dest.writeString(dateTime);
        dest.writeString(noticeContent);
        dest.writeByte((byte) (isSendTag ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getNoticeKey() {
        return noticeKey;
    }

    public void setNoticeKey(String noticeKey) {
        this.noticeKey = noticeKey;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Long noticeType) {
        this.noticeType = noticeType;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public String getDeskNum() {
        return deskNum;
    }

    public void setDeskNum(String deskNum) {
        this.deskNum = deskNum;
    }

    public boolean isNoticeIsDealed() {
        return noticeIsDealed;
    }

    public void setNoticeIsDealed(boolean noticeIsDealed) {
        this.noticeIsDealed = noticeIsDealed;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
    

    public boolean isSendTag() {
        return isSendTag;
    }

    public void setSendTag(boolean sendTag) {
        isSendTag = sendTag;
    }
}
