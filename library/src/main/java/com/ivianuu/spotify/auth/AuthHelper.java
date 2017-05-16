package com.ivianuu.spotify.auth;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ivianuu.spotify.Spotify;
import com.ivianuu.spotify.api.models.AuthenticationResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AuthHelper {

    public static Observable<String> getFreshAccessTokenRx() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getFreshAccessToken();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static String getFreshAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();

        CookieManager cookieManager = new CookieManager();

        HttpCookie spotifyCookie = new HttpCookie("spotify", android.webkit.CookieManager.getInstance().getCookie("https://accounts.spotify.com/authorize"));

        if (!cookieManager.getCookieStore().getCookies().contains(spotifyCookie))
            cookieManager.getCookieStore().add(URI.create("accounts.spotify.com"), spotifyCookie);

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("accounts.spotify.com")
                .addPathSegment("authorize")
                .addQueryParameter("client_id", Spotify.getInstance().getClientId())
                .addQueryParameter("response_type", "token")
                .addQueryParameter("redirect_uri", Spotify.getInstance().getRedirectUri())
                .addQueryParameter("scope", TextUtils.join(" ", Spotify.getInstance().getScopes()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", TextUtils.join(",", cookieManager.getCookieStore().getCookies()))
                .build();

        String accessToken = null;
        AuthenticationResponse authenticationResponse;
        authenticationResponse = convertResponse(client.newCall(request).execute());
        if (authenticationResponse != null)
            accessToken = authenticationResponse.getAccessToken();

        return accessToken != null ? accessToken : "";
    }

    @Nullable
    private static AuthenticationResponse convertResponse(@NonNull okhttp3.Response response) {
        AuthenticationResponse authenticationResponse = null;
        try {
            String uriString = null;
            String data = null;
            try {
                data = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (data.length() <= 0) {
                uriString = response.header("Location");
            } else {
                try {
                    
                } catch (Exception e) {
                    uriString = new JSONObject(data).optString("redirect", null);
                }
            }
            if (uriString != null) {
                authenticationResponse = AuthenticationResponse.fromUri(Uri.parse(uriString));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return authenticationResponse;
    }
}
