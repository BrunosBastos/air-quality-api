package tqs.airquality.cache;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class CacheManager {

    @Getter
    private class CachePair{
        private final String value;
        private final long time;

        public CachePair(String value, Long time){
            this.value = value;
            this.time = time;
        }
    }

    private int timeToLive;
    private HashMap<String, CachePair> cache;

    private int hits = 0;
    private int misses = 0;
    private int requests = 0;

    public CacheManager(){
        cache = new HashMap<>();
        timeToLive = 10;
    }

    public CacheManager(int timeToLive){
        cache = new HashMap<>();
        this.timeToLive = timeToLive;
    }

    public void addToCache(String url, String result){

        CachePair cp = new CachePair(result, System.currentTimeMillis() + timeToLive * 1000);
        cache.put(url, cp);

    }

    public String getCache(String url){

        this.requests++;
        if(cache.containsKey(url)){
            CachePair cp = cache.get(url);
            if(cp.getTime() > System.currentTimeMillis()){
                this.hits++;
                return cp.getValue();
            }
            cache.remove(url);
        }
        this.misses++;
        return null;
    }





}
