package com.ivianuu.spotify.api;

import java.util.HashMap;

public class OptionsBuilder {

    private HashMap<String, Object> options = new HashMap<>();

    public OptionsBuilder() {}

    public OptionsBuilder(HashMap<String, Object> options) {
        this.options.putAll(options);
    }

    public OptionsBuilder withLimit(int limit) {
        options.put(SpotifyService.LIMIT, limit);
        return this;
    }

    public OptionsBuilder withOffset(int offset) {
        options.put(SpotifyService.OFFSET, offset);
        return this;
    }

    public OptionsBuilder withAlbumType(String albumType) {
        options.put(SpotifyService.ALBUM_TYPE, albumType);
        return this;
    }

    public OptionsBuilder withMarket(String market) {
        options.put(SpotifyService.MARKET, market);
        return this;
    }

    public OptionsBuilder withCountry(String country) {
        options.put(SpotifyService.COUNTRY, country);
        return this;
    }

    public OptionsBuilder withLocale(String locale) {
        options.put(SpotifyService.LOCALE, locale);
        return this;
    }

    public OptionsBuilder withTimeStamp(String timeStamp) {
        options.put(SpotifyService.TIMESTAMP, timeStamp);
        return this;
    }

    public OptionsBuilder withTimeRange(String timeRange) {
        options.put(SpotifyService.TIME_RANGE, timeRange);
        return this;
    }

    public OptionsBuilder withType(String type) {
        options.put(SpotifyService.TYPE, true);
        return this;
    }

    public OptionsBuilder withAfter(String after) {
        options.put(SpotifyService.AFTER, after);
        return this;
    }

    public OptionsBuilder withUris(String uris) {
        options.put(SpotifyService.URIS, uris);
        return this;
    }

    public OptionsBuilder withIds(String ids) {
        options.put(SpotifyService.IDS, ids);
        return this;
    }

    public OptionsBuilder withName(String name) {
        options.put(SpotifyService.NAME, name);
        return this;
    }

    public OptionsBuilder withDeviceId(String deviceId) {
        options.put(SpotifyService.DEVICE_ID, deviceId);
        return this;
    }

    public OptionsBuilder withContextUri(String contextUri) {
        options.put(SpotifyService.CONTEXT_URI, contextUri);
        return this;
    }

    public OptionsBuilder withPublic(boolean isPublic) {
        options.put(SpotifyService.PUBLIC, isPublic);
        return this;
    }

    public OptionsBuilder withCollaborative(boolean collaborative) {
        options.put(SpotifyService.COLLABORATIVE, collaborative);
        return this;
    }

    public OptionsBuilder withDescription(String description) {
        options.put(SpotifyService.DESCRIPTION, description);
        return this;
    }

    public OptionsBuilder withPosition(int position) {
        options.put(SpotifyService.POSITION, position);
        return this;
    }

    public OptionsBuilder withCustomField(String key, Object value) {
        options.put(key, value);
        return this;
    }

    public HashMap<String, Object> build() {
        return options;
    }
}
