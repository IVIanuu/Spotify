package com.ivianuu.spotify;

import android.content.Context;

public class SpotifyConfig {

    private Context mContext;
    private String mClientId;
    private String mRedirectUri;
    private String[] mScopes;
    private boolean mAutoRefreshToken;

    private SpotifyConfig(Builder builder) {
        mContext = builder.context;
        mClientId = builder.clientId;
        mRedirectUri = builder.redirectUri;
        mScopes = builder.scopes;
    }

    public Context getContext() {
        return mContext;
    }

    public String getClientId() {
        return mClientId;
    }

    public String getRedirectUri() {
        return mRedirectUri;
    }

    public String[] getScopes() {
        return mScopes;
    }

    public boolean isAutoRefreshToken() {
        return mAutoRefreshToken;
    }

    public static class Builder {

        Context context;
        String clientId;
        String redirectUri;
        String[] scopes;
        boolean autoRefreshToken;

        public Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder withScopes(String[] scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder withAutoRefreshToken(boolean autoRefreshToken) {
            this.autoRefreshToken = autoRefreshToken;
            return this;
        }

        public SpotifyConfig build() {
            String missing = "";

            if (context == null) missing += " context";
            if (clientId == null) missing += " client id";
            if (redirectUri == null) missing += " redirect uri";
            if (scopes == null) missing += "scopes";

            if (!missing.isEmpty()) throw new IllegalArgumentException("missing properties: " + missing);
            return new SpotifyConfig(this);
        }

    }
}
