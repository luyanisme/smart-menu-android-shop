package com.example.luyan.smartmenu_shop.Metadata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luyan on 4/28/16.
 */
public class TABBARITEMCONTENT implements Parcelable {

    private int mNormalIconId;
    private int mCheckedIconId;
    private String textTitle;

    public TABBARITEMCONTENT(int mCheckedIconId, int mNormalIconId, String textTitle){
        this.mNormalIconId = mNormalIconId;
        this.mCheckedIconId = mCheckedIconId;
        this.textTitle = textTitle;
    }

    protected TABBARITEMCONTENT(Parcel in) {
    }

    public static final Creator<TABBARITEMCONTENT> CREATOR = new Creator<TABBARITEMCONTENT>() {
        @Override
        public TABBARITEMCONTENT createFromParcel(Parcel in) {
            return new TABBARITEMCONTENT(in);
        }

        @Override
        public TABBARITEMCONTENT[] newArray(int size) {
            return new TABBARITEMCONTENT[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCheckedIconId);
        dest.writeInt(mNormalIconId);
        dest.writeString(textTitle);
    }

    public void setmCheckedIconId(int mCheckedIconId) {
        this.mCheckedIconId = mCheckedIconId;
    }

    public int getmCheckedIconId() {
        return mCheckedIconId;
    }

    public void setmNormalIconId(int mNormalIconId) {
        this.mNormalIconId = mNormalIconId;
    }

    public int getmNormalIconId() {
        return mNormalIconId;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextTitle() {
        return textTitle;
    }
}
