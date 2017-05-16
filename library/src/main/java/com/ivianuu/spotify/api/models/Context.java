package com.ivianuu.spotify.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class Context implements Parcelable {
    
    public String type;
    public String href;
    public Map<String, String> external_urls;
    public String uri;

    public Context() {
    }

    protected Context(Parcel in) {
        type = in.readString();
        href = in.readString();
        external_urls = in.readHashMap(Map.class.getClassLoader());
        uri = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(href);
        dest.writeMap(external_urls);
        dest.writeString(uri);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Context> CREATOR = new Creator<Context>() {
        @Override
        public Context createFromParcel(Parcel in) {
            return new Context(in);
        }

        @Override
        public Context[] newArray(int size) {
            return new Context[size];
        }
    };
}