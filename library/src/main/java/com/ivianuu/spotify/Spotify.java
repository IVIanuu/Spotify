package com.ivianuu.spotify;

import android.support.annotation.Nullable;

import com.ivianuu.spotify.api.SpotifyService;
import com.ivianuu.spotify.api.SpotifyServiceBuilder;
import com.ivianuu.spotify.auth.AuthHelper;
import com.ivianuu.spotify.auth.AutoRefreshTokenReceiver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

import static com.ivianuu.spotify.ContextHolder.getContext;

public class Spotify {

    private static final long TOKEN_EXPIRES_TIME = 3600000;

    private static SpotifyConfig sSpotifyConfig;
    private static Spotify sInstance;

    private SpotifyService mApi;
    private CurrentUser mUser;

    private PreferenceHelper mPreferenceHelper;

    private List<ApiStateChangedListener> mApiStateChangedListeners = new ArrayList<>();

    public static void init(SpotifyConfig options) {
        sSpotifyConfig = options;
        ContextHolder.setContext(options.getContext());
        Spotify.getInstance().isTokenExpired();
        if (sSpotifyConfig.isAutoRefreshToken())
            AutoRefreshTokenReceiver.scheduleAlarm(sSpotifyConfig.getContext());
    }

    private Spotify() {
        mUser = new CurrentUser();
        mPreferenceHelper = PreferenceHelper.getInstance();
        refreshToken();
        updateApi();
    }

    public static synchronized Spotify getInstance() {
        Spotify spotify;
        synchronized (Spotify.class) {
            if (sInstance == null) {
                sInstance = new Spotify();
            }
            spotify = sInstance;
        }
        return spotify;
    }

    private SpotifyService getService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(getContext().getCacheDir(), 10 * 1024 * 1024))
                .build();

        return new SpotifyServiceBuilder()
                .setHttpClient(okHttpClient)
                .build();
    }

    public SpotifyService getApi() {
        if (!isApiReady()) refreshToken();
        return mApi;
    }

    public CurrentUser getCurrentUser() {
        return mUser;
    }

    private void updateApi() {
        mApi = getService();

        for (ApiStateChangedListener listener : mApiStateChangedListeners)
            if (listener != null)
                listener.onApiStateChanged();
    }

    @Nullable
    public String getAccessToken() {
        return mPreferenceHelper.getAccessToken();
    }

    public void setAccessToken(@Nullable String token) {
        mPreferenceHelper.setAccessToken(token);
        mPreferenceHelper.setTokenExpiresAt(System.currentTimeMillis() + TOKEN_EXPIRES_TIME);
        updateApi();
    }

    public String getClientId() {
        return sSpotifyConfig.getClientId();
    }

    public String getRedirectUri() {
        return sSpotifyConfig.getRedirectUri();
    }

    public String[] getScopes() {
        return sSpotifyConfig.getScopes();
    }

    public boolean isApiReady() {
        boolean isReady = !isTokenExpired();
        if (!isReady) refreshToken();
        return isReady;
    }

    private boolean isTokenExpired() {
        return tokenExpiresIn() > 0;
    }

    private long tokenExpiresIn() {
        return System.currentTimeMillis() - mPreferenceHelper.getTokenExpiresAt();
    }

    public void refreshToken() {
        AuthHelper.getFreshAccessTokenRx()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        setAccessToken(s);
                    }
                });
    }

    public void addApiStateChangedListener(ApiStateChangedListener listener) {
        if (listener == null) return;
        mApiStateChangedListeners.add(listener);
    }

    public void removeApiStateChangedListener(ApiStateChangedListener listener) {
        mApiStateChangedListeners.remove(listener);
    }

    public interface ApiStateChangedListener {
        void onApiStateChanged();
    }

}
