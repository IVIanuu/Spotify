package com.ivianuu.spotify.api.models;

/*
 * Copyright (c) 2015-2016 Spotify AB
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class AuthenticationResponse implements Parcelable {

    public enum Type {
        CODE("code"),

        TOKEN("token"),

        ERROR("error"),

        EMPTY("empty"),

        UNKNOWN("unknown");

        private final String mType;

        Type(String type) {
            mType = type;
        }

        @Override
        public String toString() {
            return mType;
        }
    }

    static final class QueryParams {
        public static final String ERROR = "error";
        public static final String CODE = "code";
        public static final String STATE = "state";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String EXPIRES_IN = "expires_in";
    }

    private final Type mType;
    private final String mCode;
    private final String mAccessToken;
    private final String mState;
    private final String mError;
    private final int mExpiresIn;

    public static class Builder {

        private Type mType;
        private String mCode;
        private String mAccessToken;
        private String mState;
        private String mError;
        private int mExpiresIn;

        public Builder setType(Type type) {
            mType = type;
            return this;
        }

        public Builder setCode(String code) {
            mCode = code;
            return this;
        }

        public Builder setAccessToken(String accessToken) {
            mAccessToken = accessToken;
            return this;
        }

        public Builder setState(String state) {
            mState = state;
            return this;
        }

        public Builder setError(String error) {
            mError = error;
            return this;
        }

        public Builder setExpiresIn(int expiresIn) {
            mExpiresIn = expiresIn;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(mType, mCode, mAccessToken, mState, mError, mExpiresIn);
        }
    }

    private AuthenticationResponse(Type type,
                                   String code,
                                   String accessToken,
                                   String state,
                                   String error,
                                   int expiresIn) {
        mType = type != null ? type : Type.UNKNOWN;
        mCode = code;
        mAccessToken = accessToken;
        mState = state;
        mError = error;
        mExpiresIn = expiresIn;
    }

    public AuthenticationResponse(Parcel source) {
        mExpiresIn = source.readInt();
        mError = source.readString();
        mState = source.readString();
        mAccessToken = source.readString();
        mCode = source.readString();
        mType = Type.values()[source.readInt()];
    }

    public static AuthenticationResponse fromUri(Uri uri) {
        AuthenticationResponse.Builder builder = new AuthenticationResponse.Builder();
        if (uri == null) {
            builder.setType(Type.EMPTY);
            return builder.build();
        }

        String possibleError = uri.getQueryParameter(AuthenticationResponse.QueryParams.ERROR);
        if (possibleError != null) {
            String state = uri.getQueryParameter(AuthenticationResponse.QueryParams.STATE);
            builder.setError(possibleError);
            builder.setState(state);
            builder.setType(Type.ERROR);
            return builder.build();
        }

        String possibleCode = uri.getQueryParameter(AuthenticationResponse.QueryParams.CODE);
        if (possibleCode != null) {
            String state = uri.getQueryParameter(AuthenticationResponse.QueryParams.STATE);
            builder.setCode(possibleCode);
            builder.setState(state);
            builder.setType(Type.CODE);
            return builder.build();
        }

        String possibleImplicit = uri.getEncodedFragment();
        if (possibleImplicit != null && possibleImplicit.length() > 0) {
            String[] parts = possibleImplicit.split("&");
            String accessToken = null;
            String state = null;
            String expiresIn = null;
            for (String part : parts) {
                String[] partSplit = part.split("=");
                if (partSplit.length == 2) {
                    if (partSplit[0].startsWith(QueryParams.ACCESS_TOKEN)) {
                        accessToken = Uri.decode(partSplit[1]);
                    }
                    if (partSplit[0].startsWith(QueryParams.STATE)) {
                        state = Uri.decode(partSplit[1]);
                    }
                    if (partSplit[0].startsWith(QueryParams.EXPIRES_IN)) {
                        expiresIn = Uri.decode(partSplit[1]);
                    }
                }
            }
            builder.setAccessToken(accessToken);
            builder.setState(state);
            if (expiresIn != null) {
                try {
                    builder.setExpiresIn(Integer.parseInt(expiresIn));
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
            builder.setType(Type.TOKEN);
            return builder.build();
        }

        builder.setType(Type.UNKNOWN);
        return builder.build();
    }

    public Type getType() {
        return mType;
    }

    public String getCode() {
        return mCode;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getState() {
        return mState;
    }

    public String getError() {
        return mError;
    }

    public int getExpiresIn() {
        return mExpiresIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mExpiresIn);
        dest.writeString(mError);
        dest.writeString(mState);
        dest.writeString(mAccessToken);
        dest.writeString(mCode);
        dest.writeInt(mType.ordinal());
    }

    public static final Parcelable.Creator<AuthenticationResponse> CREATOR = new Parcelable.Creator<AuthenticationResponse>() {
        @Override
        public AuthenticationResponse createFromParcel(Parcel source) {
            return new AuthenticationResponse(source);
        }

        @Override
        public AuthenticationResponse[] newArray(int size) {
            return new AuthenticationResponse[size];
        }
    };
}