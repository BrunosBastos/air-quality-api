package tqs.airquality.model;

import lombok.Getter;

@Getter
public class CacheStats {
    private int misses;
    private int requests;
    private int hits;

    public CacheStats(int requests, int hits, int misses){
        this.requests = requests;
        this.hits = hits;
        this.misses = misses;
    }
}
