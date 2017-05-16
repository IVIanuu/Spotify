package com.ivianuu.spotify;

import android.content.Context;

class ContextHolder {

    private static Context mContext;

    static void setContext(Context context) {
        mContext = context.getApplicationContext();
    }

    static Context getContext() {
        if (mContext == null) throw new IllegalStateException("you have to call Spotify.init first");
        return mContext;
    }
}
