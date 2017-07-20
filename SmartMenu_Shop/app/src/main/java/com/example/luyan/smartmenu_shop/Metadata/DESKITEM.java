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
    private int id;
    private String deskName;//桌位名称
    private int capacity;//桌位人数
    private int statue;//桌位状态(0.空闲1.预订2.满座)
    private boolean isHall;//是否是大厅

    public static int DESK_FREE = 0;
    public static int DESK_BOOK = 1;
    public static int DESK_FULL = 2;

    public DESKITEM(Parcel in) {
        id = in.readInt();
        deskName = in.readString();
        capacity = in.readInt();
        statue = in.readInt();
        isHall = in.readByte() != 0;
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

    public DESKITEM() {

    }

    @Generated(hash = 1909793377)
    public DESKITEM(int id, String deskName, int capacity, int statue,
            boolean isHall) {
        this.id = id;
        this.deskName = deskName;
        this.capacity = capacity;
        this.statue = statue;
        this.isHall = isHall;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(deskName);
        parcel.writeInt(capacity);
        parcel.writeInt(statue);
        parcel.writeByte((byte) (isHall ? 1 : 0));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public boolean isHall() {
        return isHall;
    }

    public void setHall(boolean hall) {
        isHall = hall;
    }

    public boolean getIsHall() {
        return this.isHall;
    }

    public void setIsHall(boolean isHall) {
        this.isHall = isHall;
    }
}
