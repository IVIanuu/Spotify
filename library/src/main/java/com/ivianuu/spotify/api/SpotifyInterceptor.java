package com.ivianuu.spotify.api;

import android.util.Log;

import com.ivianuu.spotify.Spotify;
import com.ivianuu.spotify.auth.AuthHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class SpotifyInterceptor implements Interceptor {

    private static final String TAG = "spotify_interceptor";

    private OkHttpClient mClient;

    void setClient(OkHttpClient client) {
        mClient = client;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //Build new request
        Request.Builder builder = request.newBuilder();

        String token = Spotify.getInstance().getAccessToken(); //save token of this request for future
        setAuthHeader(builder, token); //write current token to request

        request = builder.build(); //overwrite old request
        Response response = chain.proceed(request); //perform request, here original request will be executed

        switch (response.code()) {
            case 401:
                synchronized (mClient) { //perform all 401 in sync blocks, to avoid multiply token updates
                    Log.d(TAG, "401 response " + response.request().url().toString());
                    String currentToken = Spotify.getInstance().getAccessToken(); //get currently stored token

                    if(currentToken != null && currentToken.equals(token)) { //compare current token with token that was stored before, if it was not updated - do update
                        Log.d(TAG, "is same token");
                        if (!refreshToken()) {
                            Log.d(TAG, "token refreshing failed");
                            return response;
                        }
                    }

                    if(Spotify.getInstance().getAccessToken() != null) { //retry requires new auth token,
                        setAuthHeader(builder, Spotify.getInstance().getAccessToken()); //set auth token to updated
                        request = builder.build();
                        Log.d(TAG, "retry with new token");
                        return chain.proceed(request); //repeat request with new token
                    }
                }
                break;
            case 429:
                Log.d(TAG, "429 response should apply rate limiting logic here");
                break;
            case 500:
            case 502:
            case 503:
                Log.d(TAG, "50* response");
                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < 3) {
                    Log.d(TAG, "retry request try " + tryCount);
                    tryCount++;
                    response = chain.proceed(request);
                }

                break;
        }

        return response;
    }

    private void setAuthHeader(Request.Builder builder, String token) {
        if (token != null) //Add Auth token to each request if authorized
            builder.header("Authorization", "Bearer " + token);
    }

    private boolean refreshToken() {
        try {
            String newToken = AuthHelper.getFreshAccessToken();
            Spotify.getInstance().setAccessToken(newToken);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
