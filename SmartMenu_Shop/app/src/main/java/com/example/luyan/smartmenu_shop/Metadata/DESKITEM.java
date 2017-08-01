package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by luyan on 06/06/2017.
 */

@Entity
public class DESKITEM implements Parcelable{
    @Id
    private int deskId;
    private String deskName;//桌位名称
    private int deskCapacity;//桌位人数
    private int deskStatue;//桌位状态(0.空闲1.预订2.满座)
    private int deskCateId;//桌位分类

    public static int DESK_FREE = 0;
    public static int DESK_BOOK = 1;
    public static int DESK_FULL = 2;
    @Generated(hash = 652050253)
    public DESKITEM(int deskId, String deskName, int deskCapacity, int deskStatue,
            int deskCateId) {
        this.deskId = deskId;
        this.deskName = deskName;
        this.deskCapacity = deskCapacity;
        this.deskStatue = deskStatue;
        this.deskCateId = deskCateId;
    }
    @Generated(hash = 1761532344)
    public DESKITEM() {
    }

    protected DESKITEM(Parcel in) {
        deskId = in.readInt();
        deskName = in.readString();
        deskCapacity = in.readInt();
        deskStatue = in.readInt();
        deskCateId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deskId);
        dest.writeString(deskName);
        dest.writeInt(deskCapacity);
        dest.writeInt(deskStatue);
        dest.writeInt(deskCateId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DESKITEM> CREATOR = new Creator<DESKITEM>() {
        @Override
        public DESKITEM createFromParcel(Parcel in) {
            return new DESKITEM(in);
        }

        @Override
        public DESKITEM[] newArray(int size) {
            return new DESKITEM[size];
        }
    };

    public int getDeskId() {
        return this.deskId;
    }
    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }
    public String getDeskName() {
        return this.deskName;
    }
    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }
    public int getDeskCapacity() {
        return this.deskCapacity;
    }
    public void setDeskCapacity(int deskCapacity) {
        this.deskCapacity = deskCapacity;
    }
    public int getDeskStatue() {
        return this.deskStatue;
    }
    public void setDeskStatue(int deskStatue) {
        this.deskStatue = deskStatue;
    }
    public int getDeskCateId() {
        return this.deskCateId;
    }
    public void setDeskCateId(int deskCateId) {
        this.deskCateId = deskCateId;
    }

    
}
