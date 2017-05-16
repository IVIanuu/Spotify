package com.ivianuu.spotify.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Copyright implements Parcelable {
    public String text;
    public String type;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.type);
    }

    public Copyright() {
    }

    protected Copyright(Parcel in) {
        this.text = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Copyright> CREATOR = new Parcelable.Creator<Copyright>() {
        public Copyright createFromParcel(Parcel source) {
            return new Copyright(source);
        }

        public Copyright[] newArray(int size) {
            return new Copyright[size];
        }
    };
}