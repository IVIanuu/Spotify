package com.ivianuu.spotify.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

    public String id;
    public boolean is_active;
    public boolean is_restricted;
    public String name;
    public String type;
    public int volume_percent;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(is_active ? 1 : 0);
        dest.writeInt(is_restricted ? 1 : 0);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(volume_percent);
    }

    public Device() {
    }

    protected Device(Parcel in) {
        this.id = in.readString();
        this.is_active = in.readInt() == 1;
        this.is_restricted = in.readInt() == 1;
        this.name = in.readString();
        this.type = in.readString();
        this.volume_percent = in.readInt();
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
