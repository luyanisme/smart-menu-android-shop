package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by luyan on 31/05/2017.
 */

public class ORDERITEM implements Parcelable {
    private Integer clientType = 1;//客户端类型
    private String orderKey;//消息的唯一标识
    private Long orderId;//店铺id
    private Long shopId;//店铺id
    private Long noticeType;//消息类型
    private Long deskId;//桌面id
    private String deskNum;//桌号
    private boolean orderIsDealed;//是否处理过
    private boolean orderIsPayed;//是否支付过
    private boolean orderIsOrdered;//是否已下单
    private boolean orderIsUsing;//是否正在使用
    private String dateTime;//时间
    private String orderContent;//简述

    public ORDERITEM() {

    }

    protected ORDERITEM(Parcel in) {
        orderKey = in.readString();
        shopId = in.readLong();
        noticeType = in.readLong();
        deskId = in.readLong();
        deskNum = in.readString();
        orderIsDealed = in.readByte() != 0;
        orderIsPayed = in.readByte() != 0;
        orderIsOrdered = in.readByte() != 0;
        orderIsUsing = in.readByte() != 0;
        dateTime = in.readString();
        orderContent = in.readString();
    }

    public static final Creator<ORDERITEM> CREATOR = new Creator<ORDERITEM>() {
        @Override
        public ORDERITEM createFromParcel(Parcel in) {
            return new ORDERITEM(in);
        }

        @Override
        public ORDERITEM[] newArray(int size) {
            return new ORDERITEM[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderKey);
        parcel.writeLong(shopId);
        parcel.writeLong(noticeType);
        parcel.writeLong(deskId);
        parcel.writeString(deskNum);
        parcel.writeByte((byte) (orderIsDealed ? 1 : 0));
        parcel.writeByte((byte) (orderIsPayed ? 1 : 0));
        parcel.writeByte((byte) (orderIsOrdered ? 1 : 0));
        parcel.writeByte((byte) (orderIsUsing ? 1 : 0));
        parcel.writeString(dateTime);
        parcel.writeString(orderContent);
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public boolean isOrderIsDealed() {
        return orderIsDealed;
    }

    public void setOrderIsDealed(boolean orderIsDealed) {
        this.orderIsDealed = orderIsDealed;
    }

    public boolean isOrderIsPayed() {
        return orderIsPayed;
    }

    public void setOrderIsPayed(boolean orderIsPayed) {
        this.orderIsPayed = orderIsPayed;
    }

    public boolean isOrderIsOrdered() {
        return orderIsOrdered;
    }

    public void setOrderIsOrdered(boolean orderIsOrdered) {
        this.orderIsOrdered = orderIsOrdered;
    }

    public boolean isOrderIsUsing() {
        return orderIsUsing;
    }

    public void setOrderIsUsing(boolean orderIsUsing) {
        this.orderIsUsing = orderIsUsing;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }
}
