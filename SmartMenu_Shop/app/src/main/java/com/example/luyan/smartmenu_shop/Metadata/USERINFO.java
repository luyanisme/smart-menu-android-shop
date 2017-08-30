package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 28/08/2017.
 */

public class USERINFO implements Parcelable{

    private String userId;
    private String userName;
    private String phoneNum;
    private String password;
    private String realName;
    private String email;
    private String identify;
    private String shopId;
    private String authorityId;
    private String authority;
    private String editionId;
    private String edition;
    private String isUsed;
    private String updateTime;

    protected USERINFO(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        phoneNum = in.readString();
        password = in.readString();
        realName = in.readString();
        email = in.readString();
        identify = in.readString();
        shopId = in.readString();
        authorityId = in.readString();
        authority = in.readString();
        editionId = in.readString();
        edition = in.readString();
        isUsed = in.readString();
        updateTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(phoneNum);
        dest.writeString(password);
        dest.writeString(realName);
        dest.writeString(email);
        dest.writeString(identify);
        dest.writeString(shopId);
        dest.writeString(authorityId);
        dest.writeString(authority);
        dest.writeString(editionId);
        dest.writeString(edition);
        dest.writeString(isUsed);
        dest.writeString(updateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<USERINFO> CREATOR = new Creator<USERINFO>() {
        @Override
        public USERINFO createFromParcel(Parcel in) {
            return new USERINFO(in);
        }

        @Override
        public USERINFO[] newArray(int size) {
            return new USERINFO[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getEditionId() {
        return editionId;
    }

    public void setEditionId(String editionId) {
        this.editionId = editionId;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
