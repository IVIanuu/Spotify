package com.ivianuu.spotify.auth;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ivianuu.spotify.CurrentUser;
import com.ivianuu.spotify.R;
import com.ivianuu.spotify.Spotify;
import com.ivianuu.spotify.api.models.AuthenticationResponse;

import io.reactivex.functions.Consumer;

public class AuthActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.web_view);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, @NonNull String url) {
                Uri uri = Uri.parse(url);
                if (url.startsWith(Spotify.getInstance().getRedirectUri())) {
                    AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
                    Spotify.getInstance().setAccessToken(response.getAccessToken());
                    CurrentUser.getInstance().updateUserDataRx().subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Object object) throws Exception {
                            finish();
                        }
                    });
                }

                return false;
            }
        });

        String url = new Uri.Builder()
                .scheme("https")
                .authority("accounts.spotify.com")
                .appendPath("authorize")
                .appendQueryParameter("client_id", Spotify.getInstance().getClientId())
                .appendQueryParameter("response_type", "token")
                .appendQueryParameter("redirect_uri", Spotify.getInstance().getRedirectUri())
                .appendQueryParameter("scope", TextUtils.join(" ", Spotify.getInstance().getScopes()))
                .appendQueryParameter("show_dialog", "false")
                .build().toString();

        mWebView.loadUrl(url);
    }
}
