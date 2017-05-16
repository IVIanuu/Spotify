package com.ivianuu.spotify.recommendations;

import com.ivianuu.spotify.api.models.Seed;

import java.util.HashMap;
import java.util.List;

import static com.ivianuu.spotify.recommendations.RecommendationsConstants.SEED_ARTISTS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.SEED_GENRES;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.SEED_TRACKS;

public class Seeds {

    private List<Seed> mSeeds;

    Seeds(List<Seed> seeds) {
        mSeeds = seeds;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> options = new HashMap<>();

        StringBuilder seedArtistsBuilder = new StringBuilder();
        StringBuilder seedTracksBuilder = new StringBuilder();
        StringBuilder seedGenresBuilder = new StringBuilder();

        for (Seed seed : mSeeds) {
            switch (seed.type) {
                case "artist":
                    seedArtistsBuilder.append(seedArtistsBuilder.length() == 0 ? seed.id : "," + seed.id);
                    break;
                case "track":
                    seedTracksBuilder.append(seedTracksBuilder.length() == 0 ? seed.id : "," + seed.id);
                    break;
                case "genre":
                    seedGenresBuilder.append(seedGenresBuilder.length() == 0 ? seed.id : "," + seed.id);
                    break;
            }
        }

        if (seedArtistsBuilder.length() != 0)
            options.put(SEED_ARTISTS, seedArtistsBuilder.toString());

        if (seedTracksBuilder.length() != 0)
            options.put(SEED_TRACKS, seedTracksBuilder.toString());

        if (seedGenresBuilder.length() != 0)
            options.put(SEED_GENRES, seedGenresBuilder.toString());

        return options;
    }
}
