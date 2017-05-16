package com.ivianuu.spotify.helper;

import com.ivianuu.spotify.api.models.Album;
import com.ivianuu.spotify.api.models.AlbumSimple;
import com.ivianuu.spotify.api.models.Playlist;
import com.ivianuu.spotify.api.models.PlaylistBase;
import com.ivianuu.spotify.api.models.PlaylistSimple;
import com.ivianuu.spotify.api.models.PlaylistTrack;
import com.ivianuu.spotify.api.models.SavedTrack;
import com.ivianuu.spotify.api.models.Track;
import com.ivianuu.spotify.api.models.TrackSimple;

import java.util.ArrayList;
import java.util.List;

/**
 * Author IVIanuu.
 */

public class MappingHelper {

    public static List<String> tracksToUris(List<? extends TrackSimple> tracks) {
        List<String> uris = new ArrayList<>();
        for (TrackSimple trackSimple : tracks) uris.add(trackSimple.uri);
        return uris;
    }

    public static List<String> tracksToIds(List<? extends TrackSimple> tracks) {
        List<String> ids = new ArrayList<>();
        for (TrackSimple trackSimple : tracks) ids.add(trackSimple.id);
        return ids;
    }

    public static List<TrackSimple> tracksToTrackSimples(List<Track> tracks) {
        List<TrackSimple> trackSimples = new ArrayList<>();
        for (Track track : tracks) trackSimples.add(track);
        return trackSimples;
    }

    public static List<Track> savedTracksToTracks(List<SavedTrack> savedTracks) {
        List<Track> tracks = new ArrayList<>();
        for (SavedTrack savedTrack : savedTracks) tracks.add(savedTrack.track);
        return tracks;
    }

    public static List<Track> playlistTracksToTracks(List<PlaylistTrack> playlistTracks) {
        List<Track> tracks = new ArrayList<>();
        for (PlaylistTrack playlistTrack : playlistTracks) tracks.add(playlistTrack.track);
        return tracks;
    }

    public static List<AlbumSimple> albumsToAlbumSimples(List<Album> albums) {
        List<AlbumSimple> albumSimples = new ArrayList<>();
        for (Album album : albums) albumSimples.add(album);
        return albumSimples;
    }

    public static List<PlaylistBase> playlistsToPlaylistBases(List<Playlist> playlists) {
        List<PlaylistBase> playlistBases = new ArrayList<>();
        for (Playlist playlist : playlists) playlistBases.add(playlist);
        return playlistBases;
    }

    public static List<PlaylistBase> playlistSimplesToPlaylistBases(List<PlaylistSimple> playlistSimples) {
        List<PlaylistBase> playlistBases = new ArrayList<>();
        for (PlaylistSimple playlistSimple : playlistSimples) playlistBases.add(playlistSimple);
        return playlistBases;
    }
}
