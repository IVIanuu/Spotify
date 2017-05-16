package com.ivianuu.spotify.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CurrentlyPlayingContext implements Parcelable {

    public Device device;
    public String repeat_state;
    public boolean shuffle_state;
    public Context context;
    public long timestamp;
    public int progress_ms;
    public boolean is_playing;
    public Track item;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(device, 0);
        dest.writeString(repeat_state);
        dest.writeInt(shuffle_state ? 1 : 0);
        dest.writeParcelable(context, 0);
        dest.writeLong(timestamp);
        dest.writeInt(progress_ms);
        dest.writeInt(is_playing ? 1 : 0);
        dest.writeParcelable(item, 0);
    }

    public CurrentlyPlayingContext() {
    }

    protected CurrentlyPlayingContext(Parcel in) {
        this.device = in.readParcelable(Device.class.getClassLoader());
        this.repeat_state = in.readString();
        this.shuffle_state = in.readInt() == 1;
        this.context = in.readParcelable(Context.class.getClassLoader());
        this.timestamp = in.readLong();
        this.progress_ms = in.readInt();
        this.is_playing = in.readInt() == 1;
        this.item = in.readParcelable(Track.class.getClassLoader());
    }

    public static final Parcelable.Creator<CurrentlyPlayingContext> CREATOR = new Parcelable.Creator<CurrentlyPlayingContext>() {
        public CurrentlyPlayingContext createFromParcel(Parcel source) {
            return new CurrentlyPlayingContext(source);
        }

        public CurrentlyPlayingContext[] newArray(int size) {
            return new CurrentlyPlayingContext[size];
        }
    };
}
