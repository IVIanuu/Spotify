package com.ivianuu.spotify.api;

import com.ivianuu.dynamiccalladapter.DynamicCallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyServiceBuilder {

    private static final String BASE_URL = "https://api.spotify.com/v1/";

    private OkHttpClient okHttpClient = null;
    private boolean loggingEnabled = true;

    public SpotifyServiceBuilder setHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public SpotifyServiceBuilder setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        return this;
    }

    public SpotifyService build() {
        OkHttpClient.Builder okHttpBuilder = okHttpClient != null ? okHttpClient.newBuilder() : new OkHttpClient.Builder();

        if (loggingEnabled) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(DynamicCallAdapterFactory.create())
                .client(okHttpBuilder.build())
                .build();

        return restAdapter.create(SpotifyService.class);
    }
}
