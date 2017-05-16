package com.ivianuu.spotify.recommendations;

import com.ivianuu.spotify.api.models.Artist;
import com.ivianuu.spotify.api.models.ArtistSimple;
import com.ivianuu.spotify.api.models.Seed;
import com.ivianuu.spotify.api.models.Track;
import com.ivianuu.spotify.api.models.TrackSimple;

import java.util.ArrayList;
import java.util.List;

import static com.ivianuu.spotify.recommendations.RecommendationsConstants.MAX_SEEDS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TYPE_ARTIST;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TYPE_GENRE;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TYPE_TRACK;

public class SeedsBuilder {

    private List<Seed> seeds = new ArrayList<>();

    
    public SeedsBuilder withSeed(Seed seed) {
        seeds.add(seed);
        if (seeds.size() > MAX_SEEDS) throw new IllegalStateException("cannot add more than 5 seeds");
        return this;
    }
    
    public SeedsBuilder withSeed(TrackSimple track) {
        Seed seed = new Seed();
        seed.id = track.id;
        seed.type = TYPE_TRACK;
        return withSeed(seed);
    }

    public SeedsBuilder withSeed(ArtistSimple artist) {
        Seed seed = new Seed();
        seed.id = artist.id;
        seed.type = TYPE_ARTIST;
        return withSeed(seed);
    }

    
    public SeedsBuilder withSeed(String genre) {
        Seed seed = new Seed();
        seed.id = genre;
        seed.type = TYPE_GENRE;
        return withSeed(seed);
    }

    
    public SeedsBuilder withSeed(String id, String type) {
        Seed seed = new Seed();
        seed.id = id;
        seed.type = type;
        return withSeed(seed);
    }

    
    public SeedsBuilder withRandomTrackSeeds(List<? extends TrackSimple> tracks) {
        for (Seed seed : RecommendationsHelper.getRandomSeedsFromTracks(tracks))
            withSeed(seed);

        return this;
    }

    public SeedsBuilder withRandomArtistSeeds(List<? extends ArtistSimple> artists) {
        for (Seed seed : RecommendationsHelper.getRandomSeedsFromArtists(artists))
            withSeed(seed);

        return this;
    }

    
    public SeedsBuilder withRandomGenreSeeds(List<String> genres) {
        for (Seed seed : RecommendationsHelper.getRandomSeedsFromGenres(genres))
            withSeed(seed);
        return this;
    }

    
    public Seeds build() {
        if (seeds.isEmpty()) throw new IllegalStateException("have contain atleast one seed");
        return new Seeds(seeds);
    }

    
    public RecommendationsBuilder toRecommendationsBuilder() {
        if (seeds.isEmpty()) throw new IllegalStateException("have contain atleast one seed");
        return new RecommendationsBuilder().withSeeds(new Seeds(seeds));
    }
}
