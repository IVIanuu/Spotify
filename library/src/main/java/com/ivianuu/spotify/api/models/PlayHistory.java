package com.ivianuu.spotify.api.models;


import android.os.Parcel;
import android.os.Parcelable;

public class PlayHistory implements Parcelable {

    public TrackSimple track;

    public String played_at;

    public Context context;

    public PlayHistory() {
    }

    protected PlayHistory(Parcel in) {
        track = in.readParcelable(TrackSimple.class.getClassLoader());
        played_at = in.readString();
        context = in.readParcelable(Context.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(track, 0);
        dest.writeString(played_at);
        dest.writeParcelable(context, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlayHistory> CREATOR = new Creator<PlayHistory>() {
        @Override
        public PlayHistory createFromParcel(Parcel in) {
            return new PlayHistory(in);
        }

        @Override
        public PlayHistory[] newArray(int size) {
            return new PlayHistory[size];
        }
    };
}