package com.ivianuu.spotify.helper;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ivianuu.spotify.CurrentUser;
import com.ivianuu.spotify.Spotify;
import com.ivianuu.spotify.api.OptionsBuilder;
import com.ivianuu.spotify.api.SpotifyService;
import com.ivianuu.spotify.api.models.Pager;
import com.ivianuu.spotify.api.models.Playlist;
import com.ivianuu.spotify.api.models.PlaylistTrack;
import com.ivianuu.spotify.api.models.Track;
import com.ivianuu.spotify.api.models.TrackToRemove;
import com.ivianuu.spotify.api.models.TracksToRemove;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author IVIanuu.
 */

public class PlaylistHelper {

    public static Observable<Playlist> createPlaylistRx(final String name) {
        return Observable.fromCallable(new Callable<Playlist>() {
            @Override
            public Playlist call() throws Exception {
                return createPlaylist(name);
            }
        }).subscribeOn(Schedulers.io());
    }

    public static Playlist createPlaylist(String name) throws IOException {
        return Spotify.getInstance()
                .getApi()
                .createPlaylist(CurrentUser.getInstance().getUserId(), new OptionsBuilder().withName(name).build())
                .get();
    }

    public static Completable addTrackToPlaylistRx(String userId, String id, String trackId) {
        return addTracksToPlaylistRx(userId, id, Collections.singletonList(trackId));
    }

    public static void addTrackToPlaylist(String userId, String id, String trackId) {
        addTracksToPlaylist(userId, id, Collections.singletonList(trackId));
    }

    public static Completable addTracksToPlaylistRx(final String userId, final String id, final List<String> allUris) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                addTracksToPlaylist(userId, id, allUris);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void addTracksToPlaylist(String userId, String id, List<String> allUris) {
        int offset = 0;

        while (offset < allUris.size()) {
            List<String> uris = new ArrayList<>();
            for (int i = offset; i < allUris.size(); i++) {
                if (i >= offset + 75) break;
                uris.add(allUris.get(i));
                offset ++;
            }

            HashMap<String, Object> options = new OptionsBuilder()
                    .withUris(TextUtils.join(",", uris).trim())
                    .build();

            Spotify.getInstance().getApi()
                    .addTracksToPlaylist(userId, id, options, new HashMap<String, Object>())
                    .get();
        }
    }

    public static Completable removeTrackFromPlaylistRx(String userId, String id, String trackId) {
        return removeTracksFromPlaylistRx(userId, id, Collections.singletonList(trackId));
    }

    public static void removeTrackFromPlaylist(String userId, String id, String trackId) {
        removeTracksFromPlaylist(userId, id, Collections.singletonList(trackId));
    }

    public static Completable removeTracksFromPlaylistRx(final String userId, final String id, final List<String> allUris) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                removeTracksFromPlaylist(userId, id, allUris);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void removeTracksFromPlaylist(String userId, String id, List<String> allUris) {
        int offset = 0;

        while (offset < allUris.size()) {
            List<TrackToRemove> tracksToRemoveList = new ArrayList<>();
            for (int i = offset; i < allUris.size(); i++) {
                if (i >= offset + 75) break;
                TrackToRemove trackToRemove = new TrackToRemove();
                trackToRemove.uri = allUris.get(i);
                tracksToRemoveList.add(trackToRemove);
                offset ++;
            }

            TracksToRemove tracksToRemove = new TracksToRemove();
            tracksToRemove.tracks = tracksToRemoveList;

            Spotify.getInstance().getApi()
                    .removeTracksFromPlaylist(userId, id, tracksToRemove)
                    .get();
        }
    }

    public static Completable replacePlaylistTracksRx(final String userId, final String id, final List<String> allUris) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                replacePlaylistTracks(userId, id, allUris);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void replacePlaylistTracks(String userId, String id, List<String> allUris) {
        int offset = 0;

        boolean firstCall = false;
        while (offset < allUris.size()) {
            List<String> uris = new ArrayList<>();
            for (int i = offset; i < allUris.size(); i++) {
                if (i >= offset + 75) break;
                uris.add(allUris.get(i));
                offset ++;
            }

            if (!firstCall) {
                Spotify.getInstance().getApi()
                        .replaceTracksInPlaylist(userId, id, TextUtils.join(",", uris).trim(), new HashMap<String, Object>())
                        .get();
                firstCall = true;
            } else {
                addTracksToPlaylist(userId, id, uris);
            }
        }
    }
    
    public static Completable clearPlaylistRx(final String userId, final String id) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                clearPlaylist(userId, id);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void clearPlaylist(String userId, String id) {
        Spotify.getInstance().getApi()
                .replaceTracksInPlaylist(userId, id, "", new HashMap<>())
                .get();
    }

    public static Completable renamePlaylistRx(final String userId, final String id, final String name) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                renamePlaylist(userId, id, name);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void renamePlaylist(String userId, String id, String name) {
        HashMap<String, Object> options = new OptionsBuilder()
                .withName(name)
                .build();

        Spotify.getInstance().getApi()
                .changePlaylistDetails(userId, id, options).get();
    }

    public static Completable changePlaylistDescriptionRx(final String userId, final String id, final String description) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                changePlaylistDescription(userId, id, description);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void changePlaylistDescription(String userId, String id, String description) {
        HashMap<String, Object> options = new OptionsBuilder()
                .withDescription(description)
                .build();

        Spotify.getInstance().getApi()
                .changePlaylistDetails(userId, id, options).get();
    }

    public static Completable changePlaylistPublicRx(final String userId, final String id, final boolean isPublic) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                changePlaylistPublic(userId, id, isPublic);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void changePlaylistPublic(String userId, String id, boolean isPublic) {
        HashMap<String, Object> options = new OptionsBuilder()
                .withPublic(isPublic)
                .build();

        Spotify.getInstance().getApi()
                .changePlaylistDetails(userId, id, options).get();
    }

    public static Completable changePlaylistCollaborativeRx(final String userId, final String id, final boolean collaborative) {
        return Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                changePlaylistCollaborative(userId, id, collaborative);
                return new Object();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void changePlaylistCollaborative(String userId, String id, boolean collaborative) {
        HashMap<String, Object> options = new OptionsBuilder()
                .withPublic(collaborative)
                .build();

        Spotify.getInstance().getApi()
                .changePlaylistDetails(userId, id, options).get();
    }

    public static Observable<List<Track>> getAllPlaylistTracksRx(@NonNull final String userId, @NonNull final String playlistId) {
        return Observable.fromCallable(new Callable<List<Track>>() {
            @Override
            public List<Track> call() throws Exception {
                return getAllPlaylistTracks(userId, playlistId);
            }
        }).subscribeOn(Schedulers.io());
    }

    @NonNull
    public static List<Track> getAllPlaylistTracks(@NonNull String userId, @NonNull String playlistId) throws IOException {
        List<Track> playlistTracks = new ArrayList<>();

        SpotifyService api = Spotify.getInstance().getApi();

        final HashMap<String, Object> options = new OptionsBuilder()
                .withLimit(100)
                .withMarket(SpotifyService.FROM_TOKEN)
                .build();

        Pager<PlaylistTrack> pager;

        int offset = -1;
        boolean allReceived = false;

        while (!allReceived) {
            if (offset == -1) offset = 0;
            options.put(SpotifyService.OFFSET, offset);
            pager = api.getPlaylistTracks(userId, playlistId, options).get();
            if (pager == null) throw new IOException("something went wrong");
            for (int i = 0; i < pager.items.size(); i++) {
                if (pager.items.get(i).track == null) continue;
                playlistTracks.add(pager.items.get(i).track);
            }

            if (playlistTracks.size() >= pager.total || pager.total == 0) {
                allReceived = true;
            } else {
                offset += 100;
            }
        }

        return playlistTracks;
    }
}
