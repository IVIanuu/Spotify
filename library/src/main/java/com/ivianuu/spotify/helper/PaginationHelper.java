package com.ivianuu.spotify.helper;

import android.support.annotation.NonNull;

import com.ivianuu.spotify.api.SpotifyService;
import com.ivianuu.spotify.api.models.Pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaginationHelper<T> {

    private HashMap<String, Object> mOptions;

    private int mLimit;
    private int mOffset = 0;
    private int mTotal;
    private int mMax;

    private List<T> mItems = new ArrayList<>();

    private List<T> mLastReceivedItems = new ArrayList<>();

    private boolean mFirstItemsLoaded = false;

    private PaginationHelper(Builder<T> builder) {
        mOptions = builder.options;

        mLimit = builder.limit;
        mMax = builder.max;

        mOptions.put(SpotifyService.LIMIT, mLimit);
        mOptions.put(SpotifyService.OFFSET, mOffset);
    }

    public void handleResponse(@NonNull Pager<T> pager) {
        mTotal = pager.total;
        mOffset = pager.offset + mLimit;
        mOptions.put(SpotifyService.OFFSET, mOffset);

        mLastReceivedItems.clear();

        if (mMax != -1) {
            for (T item : pager.items) {
                if (mItems.size() == mMax) break;
                mItems.add(item);
                mLastReceivedItems.add(item);
            }
        } else {
            if (pager.items != null) {
                mItems.addAll(pager.items);
                mLastReceivedItems.addAll(pager.items);
            }
        }

        if (!mFirstItemsLoaded)
            mFirstItemsLoaded = true;
    }

    public HashMap<String, Object> getOptions() {
        return mOptions;
    }

    public List<T> getItems() {
        return mItems;
    }

    public List<T> getLastReceivedItems() {
        return mLastReceivedItems;
    }

    public boolean isFirstItemsLoaded() {
        return mFirstItemsLoaded;
    }

    public boolean allReceived() {
        if (mTotal == -1) return false;
        if (mTotal == 0) return true;
        if (mMax != -1 && mOffset >= mMax) return true;
        return mOffset >= mTotal;
    }

    public void reset() {
        mItems.clear();
        mLastReceivedItems.clear();
        mOffset = 0;
        mTotal = -1;
        mOptions.put(SpotifyService.OFFSET, mOffset);
        mFirstItemsLoaded = false;
    }

    public static class Builder<T> {

        private HashMap<String, Object> options = new HashMap<>();

        private int limit = -1;
        private int max = -1;

        public Builder<T> withOptions(HashMap<String, Object> options) {
            this.options = options;
            return this;
        }

        public Builder<T> withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder<T> withMax(int max) {
            this.max = max;
            return this;
        }

        public PaginationHelper<T> build() {
            if (limit == -1) throw new IllegalArgumentException("you have to set a limit");
            return new PaginationHelper<>(this);
        }
    }
}
