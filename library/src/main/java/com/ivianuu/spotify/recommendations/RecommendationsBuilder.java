package com.ivianuu.spotify.recommendations;

import java.util.HashMap;

import static com.ivianuu.spotify.recommendations.RecommendationsConstants.ACOUSTICNESS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.DANCEABILITY;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.DURATION;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.ENERGY;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.INSTRUMENTALNESS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.KEY;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.LIMIT;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.LIVENESS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.LOUDNESS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.MARKET;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.MAX;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.MIN;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.MODE;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.POPULARITY;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.SPEECHINESS;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TARGET;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TEMPO;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.TIME_SIGNATURE;
import static com.ivianuu.spotify.recommendations.RecommendationsConstants.VALENCE;

public class RecommendationsBuilder {

    private HashMap<String, Object> options = new HashMap<>();

    
    public RecommendationsBuilder withPreset(HashMap<String, Object> preset) {
        options.putAll(preset);
        return this;
    }

    
    public RecommendationsBuilder withSeeds(Seeds seeds) {
        options.putAll(seeds.toHashMap());
        return this;
    }

    
    public RecommendationsBuilder withMaxAcousticness(float maxAcousticness) {
        options.put(MAX + ACOUSTICNESS, maxAcousticness);
        return this;
    }

    
    public RecommendationsBuilder withMinAcousticness(float minAcousticness) {
        options.put(MIN + ACOUSTICNESS, minAcousticness);
        return this;
    }

    
    public RecommendationsBuilder withTargetAcousticness(float targetAcousticness) {
        options.put(TARGET + ACOUSTICNESS, targetAcousticness);
        return this;
    }

    
    public RecommendationsBuilder withMaxDanceability(float maxDanceability) {
        options.put(MAX + DANCEABILITY, maxDanceability);
        return this;
    }

    
    public RecommendationsBuilder withMinDanceability(float minDanceability) {
        options.put(MIN + DANCEABILITY, minDanceability);
        return this;
    }

    
    public RecommendationsBuilder withTargetDanceability(float targetDanceability) {
        options.put(TARGET + DANCEABILITY, targetDanceability);
        return this;
    }

    
    public RecommendationsBuilder withMaxDuration(int maxDuration) {
        options.put(MAX + DURATION, maxDuration);
        return this;
    }

    
    public RecommendationsBuilder withMinDuration(int minDuration) {
        options.put(MIN + DURATION, minDuration);
        return this;
    }

    
    public RecommendationsBuilder withTargetDuration(int targetDuration) {
        options.put(TARGET + DURATION, targetDuration);
        return this;
    }

    
    public RecommendationsBuilder withMaxEnergy(float maxEnergy) {
        options.put(MAX + ENERGY, maxEnergy);
        return this;
    }

    
    public RecommendationsBuilder withMinEnergy(float minEnergy) {
        options.put(MIN + ENERGY, minEnergy);
        return this;
    }

    
    public RecommendationsBuilder withTargetEnergy(float targetEnergy) {
        options.put(TARGET + ENERGY, targetEnergy);
        return this;
    }

    
    public RecommendationsBuilder withMaxInstrumentalness(float maxInstrumentalness) {
        options.put(MAX + INSTRUMENTALNESS, maxInstrumentalness);
        return this;
    }

    
    public RecommendationsBuilder withMinInstrumentalness(float minInstrumentalness) {
        options.put(MIN + INSTRUMENTALNESS, minInstrumentalness);
        return this;
    }

    
    public RecommendationsBuilder withTargetInstrumentalness(float targetInstrumentalness) {
        options.put(TARGET + INSTRUMENTALNESS, targetInstrumentalness);
        return this;
    }

    
    public RecommendationsBuilder withKey(int key) {
        options.put(KEY, key);
        return this;
    }

    
    public RecommendationsBuilder withMaxLiveness(float maxLiveness) {
        options.put(MAX + LIVENESS, maxLiveness);
        return this;
    }

    
    public RecommendationsBuilder withMinLiveness(float minLiveness) {
        options.put(MIN + LIVENESS, minLiveness);
        return this;
    }

    
    public RecommendationsBuilder withTargetLiveness(float targetLiveness) {
        options.put(TARGET + LIVENESS, targetLiveness);
        return this;
    }

    
    public RecommendationsBuilder withMaxLoudness(float maxLoudness) {
        options.put(MAX + LOUDNESS, maxLoudness);
        return this;
    }

    
    public RecommendationsBuilder withMinLoudness(float minLoudness) {
        options.put(MIN + LOUDNESS, minLoudness);
        return this;
    }

    
    public RecommendationsBuilder withTargetLoudness(float targetLoudness) {
        options.put(TARGET + LOUDNESS, targetLoudness);
        return this;
    }

    
    public RecommendationsBuilder withMode(int mode) {
        options.put(MODE, mode);
        return this;
    }

    
    public RecommendationsBuilder withMaxPopularity(int maxPopularity) {
        options.put(MAX + POPULARITY, maxPopularity);
        return this;
    }

    
    public RecommendationsBuilder withMinPopularity(int minPopularity) {
        options.put(MIN + POPULARITY, minPopularity);
        return this;
    }

    
    public RecommendationsBuilder withTargetPopularity(int targetPopularity) {
        options.put(TARGET + POPULARITY, targetPopularity);
        return this;
    }

    
    public RecommendationsBuilder withMaxSpeechiness(float maxSpeechiness) {
        options.put(MAX + SPEECHINESS, maxSpeechiness);
        return this;
    }

    
    public RecommendationsBuilder withMinSpeechiness(float minSpeechiness) {
        options.put(MIN + SPEECHINESS, minSpeechiness);
        return this;
    }

    
    public RecommendationsBuilder withTargetSpeechiness(float targetSpeechiness) {
        options.put(TARGET + SPEECHINESS, targetSpeechiness);
        return this;
    }

    
    public RecommendationsBuilder withMaxTempo(float maxTempo) {
        options.put(MAX + TEMPO, maxTempo);
        return this;
    }

    
    public RecommendationsBuilder withMinTempo(float minTempo) {
        options.put(MIN + TEMPO, minTempo);
        return this;
    }

    
    public RecommendationsBuilder withTargetTempo(float targetTempo) {
        options.put(TARGET + TEMPO, targetTempo);
        return this;
    }

    
    public RecommendationsBuilder withTimeSignature(int timeSignature) {
        options.put(TIME_SIGNATURE, timeSignature);
        return this;
    }

    
    public RecommendationsBuilder withMaxValence(float maxValence) {
        options.put(MAX + VALENCE, maxValence);
        return this;
    }

    
    public RecommendationsBuilder withMinValence(float minValence) {
        options.put(MIN + VALENCE, minValence);
        return this;
    }

    
    public RecommendationsBuilder withTargetValence(float targetValence) {
        options.put(TARGET + VALENCE, targetValence);
        return this;
    }

    
    public RecommendationsBuilder withLimit(int limit) {
        options.put(LIMIT, limit);
        return this;
    }

    
    public RecommendationsBuilder withMarket(String market) {
        options.put(MARKET, market);
        return this;
    }

    
    public RecommendationsOptions toOptions() {
        return new RecommendationsOptions(options);
    }
}
