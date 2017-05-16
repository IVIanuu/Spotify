package com.ivianuu.spotify.helper;

import android.support.annotation.NonNull;

import com.ivianuu.spotify.api.models.AlbumSimple;
import com.ivianuu.spotify.api.models.Artist;
import com.ivianuu.spotify.api.models.PlaylistBase;
import com.ivianuu.spotify.api.models.Track;
import com.ivianuu.spotify.api.models.TrackSimple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UriHelper {

    public static final String TRACK_URI = "spotify:track:";
    public static final String TRACK_URL = "https://open.spotify.com/track/";
    public static final String ARTIST_URL = "https://open.spotify.com/artist/";
    public static final String ALBUM_URL = "https://open.spotify.com/album/";
    public static final String PLAYLIST_URL1 = "https://open.spotify.com/user/";
    public static final String PLAYLIST_URL2 = "/playlist/";
    private static final String ARTIST_URI = "spotify:artist:";
    private static final String PLAYLIST_URI1 = "spotify:user:";
    private static final String PLAYLIST_URI2 = ":playlist:";
    private static final String ALBUM_URI = "spotify:album:";

    @NonNull
    public static String getTrackUrl(@NonNull Track track) {
        return TRACK_URL + track.id;
    }

    @NonNull
    public static String getAlbumUrl(@NonNull AlbumSimple album) {
        return ALBUM_URL + album.id;
    }

    @NonNull
    public static String getArtistUrl(@NonNull Artist artist) {
        return ARTIST_URL + artist.id;
    }

    @NonNull
    public static String getPlaylistUrl(@NonNull PlaylistBase playlist) {
        return getPlaylistUrl(playlist.owner.id, playlist.id);
    }

    @NonNull
    private static String getPlaylistUrl(String userId, String id) {
        return PLAYLIST_URL1 + userId + PLAYLIST_URL2 + id;
    }

    @NonNull
    public static String getTrackUri(@NonNull Track track) {
        return TRACK_URI + track.id;
    }

    @NonNull
    public static String getAlbumUri(@NonNull AlbumSimple album) {
        return ALBUM_URI + album.id;
    }

    @NonNull
    public static String getArtistUri(@NonNull Artist artist) {
        return ARTIST_URI + artist.id;
    }

    @NonNull
    public static String getPlaylistUri(@NonNull PlaylistBase playlist) {
        return getPlaylistUrl(playlist.owner.id, playlist.id);
    }

    @NonNull
    public static String getPlaylistUri(String userId, String id) {
        return PLAYLIST_URI1 + userId + PLAYLIST_URI2 + id;
    }

    public static String getIdFromTrackUrl(@NonNull String url) {
        return url.replace(TRACK_URL, "");
    }

    public static String getIdFromArtistUrl(@NonNull String url) {
        return url.replace(ARTIST_URL, "");
    }

    public static String getIdFromAlbumUrl(@NonNull String url) {
        return url.replace(ALBUM_URL, "");
    }

    public static String getUserIdFromPlaylistUrl(@NonNull String url) {
        List<String> asList = Arrays.asList(url.split(PLAYLIST_URL2));
        return asList.get(0).replace(PLAYLIST_URL1, "");
    }

    public static String getIdFromPlaylistUrl(@NonNull String url) {
        List<String> asList = Arrays.asList(url.split(PLAYLIST_URL2));
        return asList.get(1);
    }

    public static String getIdFromTrackUri(@NonNull String uri) {
        return uri.replace(TRACK_URI, "");
    }

    public static String getIdFromArtistUri(@NonNull String uri) {
        return uri.replace(ARTIST_URI, "");
    }

    public static String getIdFromAlbumUri(@NonNull String uri) {
        return uri.replace(ALBUM_URI, "");
    }

    public static String getUserIdFromPlaylistUri(@NonNull String uri) {
        List<String> asList = Arrays.asList(uri.split(PLAYLIST_URI2));
        return asList.get(0).replace(PLAYLIST_URI1, "");
    }

    public static String getIdFromPlaylistUri(@NonNull String uri) {
        List<String> asList = Arrays.asList(uri.split(PLAYLIST_URI2));
        return asList.get(1);
    }

}