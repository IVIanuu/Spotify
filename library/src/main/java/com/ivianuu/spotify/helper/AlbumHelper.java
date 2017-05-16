package com.ivianuu.spotify.helper;

import android.support.annotation.NonNull;

import com.ivianuu.spotify.Spotify;
import com.ivianuu.spotify.api.OptionsBuilder;
import com.ivianuu.spotify.api.SpotifyService;
import com.ivianuu.spotify.api.models.AlbumSimple;
import com.ivianuu.spotify.api.models.Pager;
import com.ivianuu.spotify.api.models.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author IVIanuu.
 */

public class AlbumHelper {

    public static Observable<List<Track>> getAllAlbumTracksRx(@NonNull final String id) {
        return Observable.fromCallable(new Callable<List<Track>>() {
            @Override
            public List<Track> call() throws Exception {
                return getAllAlbumTracks(id);
            }
        }).subscribeOn(Schedulers.io());
    }

    @NonNull
    private static List<Track> getAllAlbumTracks(@NonNull String id) throws IOException {
        List<Track> tracks = new ArrayList<>();
        SpotifyService api = Spotify.getInstance().getApi();
        final HashMap<String, Object> options = new OptionsBuilder()
                .withLimit(50)
                .withMarket(SpotifyService.FROM_TOKEN)
                .build();
        Pager<Track> pager;

        AlbumSimple album = api.getAlbum(id).get();

        int offset = -1;
        boolean allReceived = false;

        while (!allReceived) {
            if (offset == -1)
                offset = 0;
            options.put(SpotifyService.OFFSET, offset);
            pager = api.getAlbumTracks(album.id, options).get();
            if (pager == null) throw new IOException("something went wrong");
            for (int i = 0; i < pager.items.size(); i++) {
                Track track = pager.items.get(i);
                track.album = album;
                tracks.add(track);
            }

            if (tracks.size() >= pager.total || pager.items.isEmpty()) {
                allReceived = true;
            } else {
                offset += 50;
            }
        }

        return tracks;
    }
}
