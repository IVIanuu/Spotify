package com.ivianuu.spotify;

import android.content.Context;
import android.content.SharedPreferences;

class PreferenceHelper {

    private static PreferenceHelper sInstance;

    private static final String PREF_NAME = "spotify_prefs";

    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_TOKEN_EXPIRES_AT = "token_expires_at";
    private static final String KEY_CURRENT_USER = "current_user";

    private SharedPreferences mSharedPreferences;

    private PreferenceHelper() {
        mSharedPreferences = ContextHolder.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    static PreferenceHelper getInstance() {
        PreferenceHelper preferenceHelper;
        synchronized (PreferenceHelper.class) {
            if (sInstance == null) {
                sInstance = new PreferenceHelper();
            }
            preferenceHelper = sInstance;
        }
        return preferenceHelper;
    }

    void setAccessToken(String accessToken) {
        mSharedPreferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
    }

    String getAccessToken() {
        return mSharedPreferences.getString(KEY_ACCESS_TOKEN, "");
    }

    void setTokenExpiresAt(long expiresAt) {
        mSharedPreferences.edit().putLong(KEY_TOKEN_EXPIRES_AT, expiresAt).apply();
    }

    long getTokenExpiresAt() {
        return mSharedPreferences.getLong(KEY_TOKEN_EXPIRES_AT, 0);
    }

    void setCurrentUser(String currentUser) {
        mSharedPreferences.edit().putString(KEY_CURRENT_USER, currentUser).apply();
    }

    String getCurrentUser() {
        return mSharedPreferences.getString(KEY_CURRENT_USER, "");
    }

}
