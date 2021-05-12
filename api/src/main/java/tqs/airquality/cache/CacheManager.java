package tqs.airquality.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import tqs.airquality.model.City;

import java.util.HashMap;
import java.util.Optional;
import tqs.airquality.cache.CachePair;

@Getter @Setter @Component
public class CacheManager {

    private int timeToLive;
    private HashMap<String, CachePair> cache;

    private int hits = 0;
    private int misses = 0;
    private int requests = 0;

    public CacheManager(){
        cache = new HashMap<>();
        timeToLive = 1000;
    }

    public CacheManager(int timeToLive){
        cache = new HashMap<>();
        this.timeToLive = timeToLive;
    }

    public void addToCache(String url, City result){
        Optional<City> cache_value;
        if(result == null)
            cache_value = Optional.empty();
        else
            cache_value = Optional.of(result);

        CachePair cp = new CachePair(cache_value, System.currentTimeMillis() + timeToLive * 1000);
        cache.put(url, cp);

    }

    public CachePair getCache(String url){

        this.requests++;
        if(cache.containsKey(url)){
            CachePair cp = cache.get(url);
            if(cp.getTime() > System.currentTimeMillis()){
                this.hits++;
                System.out.println(hits);
                return cp;
            }
            cache.remove(url);
        }
        this.misses++;
        return null;
    }
}
