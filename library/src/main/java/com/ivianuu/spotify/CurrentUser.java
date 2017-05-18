package com.ivianuu.spotify;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;

import com.google.gson.Gson;
import com.ivianuu.spotify.api.models.UserPrivate;
import com.ivianuu.spotify.api.models.UserPublic;
import com.ivianuu.spotify.auth.AuthActivity;
import com.ivianuu.spotify.helper.ImageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.ivianuu.spotify.ContextHolder.getContext;

public class CurrentUser {

    private UserPrivate mUser;

    private boolean mIsUpdatingUserData;

    private final Gson mGson;

    private PreferenceHelper mPreferenceHelper;

    private List<UserInfoChangedListener> mUserInfoChangedListeners = new ArrayList<>();

    CurrentUser() {
        mPreferenceHelper = PreferenceHelper.getInstance();
        mGson = new Gson();
        mUser = mGson.fromJson(mPreferenceHelper.getCurrentUser(), UserPrivate.class);
        updateUserDataRx().subscribe();
    }

    public Observable<Object> updateUserDataRx() {
        return Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                updateUserData();
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    private void updateUserData() {
        if (mIsUpdatingUserData) return;

        mIsUpdatingUserData = true;

        mUser = Spotify.getInstance().getApi().getMe().get();

        if (mUser != null) {
            mPreferenceHelper.setCurrentUser(mGson.toJson(mUser));
            notifyChange();
        }

        mIsUpdatingUserData = false;
    }

    private void notifyChange() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (UserInfoChangedListener listener : mUserInfoChangedListeners)
                    if (listener != null)
                        listener.onUserInfoChanged();
            }
        });
    }

    public void login(Activity activity) {
        login(activity, 0);
    }

    public void login(Activity activity, int requestCode) {
        Log.d("my tag", "start login");
        Intent intent = new Intent(getContext(), AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, requestCode);
    }

    public void logout() {
        Spotify.getInstance().setAccessToken(null);
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {

                }
            });
        } else {
            cookieManager.removeAllCookie();
        }
    }

    public boolean isLoggedIn() {
        return !(Spotify.getInstance().getAccessToken() == null
                || Spotify.getInstance().getAccessToken().isEmpty());
    }

    public UserPrivate getUser() {
        return mUser;
    }

    @NonNull
    public String getUserId() {
        return mUser != null ? mUser.id : "";
    }

    @NonNull
    public String getUserName() {
        return mUser != null ? mUser.display_name : "";
    }

    @NonNull
    public String getUserEmail() {
        return mUser != null ? mUser.email : "";
    }

    @NonNull
    public String getUserImage() {
        return mUser != null ? ImageHelper.getImage(mUser) : "";
    }

    @NonNull
    public String getUserCountry() {
        return mUser != null ? mUser.country : "";
    }

    public boolean hasPremium() {
        if (mUser == null) return false;
        String product = mUser.product;
        return !(product.equals("free") || product.equals("open"));
    }

    public void addUserInfoChangedListener(UserInfoChangedListener listener) {
        if (listener == null) return;
        mUserInfoChangedListeners.add(listener);
    }

    public void removeUserInfoChangedListener(UserInfoChangedListener listener) {
        mUserInfoChangedListeners.remove(listener);
    }

    public interface UserInfoChangedListener {
        void onUserInfoChanged();
    }
}
