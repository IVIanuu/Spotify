package com.ivianuu.spotify.recommendations;

import com.ivianuu.spotify.api.SpotifyService;
import com.ivianuu.spotify.api.models.Recommendations;
import com.ivianuu.spotify.api.models.Track;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class RecommendationsOptions {

    private HashMap<String, Object> mOptions;

    RecommendationsOptions(HashMap<String, Object> options) {
        mOptions = options;
    }

    public HashMap<String, Object> asHashMap() {
        return mOptions;
    }

    public List<Track> asTracks(SpotifyService api) throws IOException {
        return api.getRecommendations(mOptions).get().tracks;
    }
    
    public Observable<List<Track>> asTracksRx(SpotifyService api) {
        return api.getRecommendations(mOptions)
                .asObservable()
                .map(new Function<Recommendations, List<Track>>() {
                    
                    public List<Track> apply(@NonNull Recommendations recommendations) throws Exception {
                        return recommendations.tracks;
                    }
                });
    }


}
