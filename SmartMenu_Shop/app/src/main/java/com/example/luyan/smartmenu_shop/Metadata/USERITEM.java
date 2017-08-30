package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 28/08/2017.
 */

public class USERITEM implements Parcelable {
    private String username;
    private String password;

    public USERITEM(Parcel in) {
        username = in.readString();
        password = in.readString();
    }

    public USERITEM() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<USERITEM> CREATOR = new Creator<USERITEM>() {
        @Override
        public USERITEM createFromParcel(Parcel in) {
            return new USERITEM(in);
        }

        @Override
        public USERITEM[] newArray(int size) {
            return new USERITEM[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
