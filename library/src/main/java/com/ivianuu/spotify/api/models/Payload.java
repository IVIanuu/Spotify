package com.ivianuu.spotify.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Payload implements Parcelable {

    public List<Device> devices;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(devices);
    }

    public Payload() {
    }

    protected Payload(Parcel in) {
        this.devices = in.createTypedArrayList(Device.CREATOR);
    }

    public static final Parcelable.Creator<Payload> CREATOR = new Parcelable.Creator<Payload>() {
        public Payload createFromParcel(Parcel source) {
            return new Payload(source);
        }

        public Payload[] newArray(int size) {
            return new Payload[size];
        }
    };
}
