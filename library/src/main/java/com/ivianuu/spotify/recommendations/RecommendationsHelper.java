package com.ivianuu.spotify.recommendations;

import android.support.annotation.NonNull;

import com.ivianuu.spotify.api.models.Artist;
import com.ivianuu.spotify.api.models.ArtistSimple;
import com.ivianuu.spotify.api.models.Seed;
import com.ivianuu.spotify.api.models.Track;
import com.ivianuu.spotify.api.models.TrackSimple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ivianuu.spotify.recommendations.RecommendationsConstants.MAX_SEEDS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TYPE_ARTIST;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TYPE_GENRE;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TYPE_TRACK;

public class RecommendationsHelper {

    public static List<Seed> getRandomSeedsFromTracks(List<? extends TrackSimple> tracks) {
        List<Seed> seeds = new ArrayList<>();
        List<TrackSimple> shuffled = new ArrayList<>(tracks);
        Collections.shuffle(shuffled);

        for (int i = 0; i < shuffled.size(); i++) {
            if (seeds.size() >= MAX_SEEDS) break;
            Seed seed = new Seed();
            seed.id = shuffled.get(i).id;
            seed.type = TYPE_TRACK;

            seeds.add(seed);
        }

        return seeds;
    }

    public static List<Seed> getRandomSeedsFromArtists(List<? extends ArtistSimple> artists) {
        List<Seed> seeds = new ArrayList<>();
        List<ArtistSimple> shuffled = new ArrayList<>(artists);
        Collections.shuffle(shuffled);

        for (int i = 0; i < shuffled.size(); i++) {
            if (seeds.size() >= MAX_SEEDS) break;
            Seed seed = new Seed();
            seed.id = shuffled.get(i).id;
            seed.type = TYPE_ARTIST;

            seeds.add(seed);
        }

        return seeds;
    }

    public static List<Seed> getRandomSeedsFromGenres(List<String> genres) {
        List<Seed> seeds = new ArrayList<>();
        List<String> shuffled = new ArrayList<>(genres);
        Collections.shuffle(shuffled);

        for (int i = 0; i < shuffled.size(); i++) {
            if (seeds.size() >= MAX_SEEDS) break;
            Seed seed = new Seed();
            seed.id = genres.get(i);
            seed.type = TYPE_GENRE;

            seeds.add(seed);
        }

        return seeds;
    }

    public static int getAveragePopularityFromTracks(@NonNull List<Track> tracks) {
        if (tracks.isEmpty()) return 0;
        int averagePopularity = 0;

        for (Track track : tracks) averagePopularity += track.popularity;


        return averagePopularity / tracks.size();
    }

    public static int getAveragePopularityFromArtists(@NonNull List<Artist> artists) {
        int averagePopularity = 0;

        for (Artist artist : artists)
            averagePopularity += artist.popularity;

        return averagePopularity / artists.size();
    }

}
