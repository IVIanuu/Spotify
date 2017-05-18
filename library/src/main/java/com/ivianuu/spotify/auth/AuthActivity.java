package com.ivianuu.spotify.auth;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ivianuu.spotify.CurrentUser;
import com.ivianuu.spotify.R;
import com.ivianuu.spotify.Spotify;
import com.ivianuu.spotify.api.models.AuthenticationResponse;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class AuthActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Log.d("testtt", "on create auth");

        WebView webView = (WebView) findViewById(R.id.web_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, @NonNull String url) {
                Log.d("testtt", "override url loading " + url);
                Uri uri = Uri.parse(url);
                if (url.startsWith(Spotify.getInstance().getRedirectUri())) {
                    Log.d("testtt", "starts with redirect uri " + url);
                    AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
                    Spotify.getInstance().setAccessToken(response.getAccessToken());
                    setResult(RESULT_OK);
                    finish();
                    return true;
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

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
