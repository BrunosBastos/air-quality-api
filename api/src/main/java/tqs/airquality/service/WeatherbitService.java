package tqs.airquality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.airquality.cache.CacheManager;
import tqs.airquality.model.CacheStats;
import tqs.airquality.model.City;
import tqs.airquality.repository.WeatherBitRepository;
import tqs.airquality.cache.CachePair;
import java.util.Optional;

@Service
public class WeatherbitService {

    private CacheManager cacheManager = new CacheManager();

    @Autowired
    private WeatherBitRepository repository;

    public Optional<City> getCityByNameAndCountry(String city, String country){

        String url = "name=" + city + "&country=" + country;

        CachePair cache = cacheManager.getCache(url);
        if(cache != null)
            return cache.getValue();

        City ct = repository.getDetailsByCityNameAndCountry(city, country);
        cacheManager.addToCache(url, ct);
        if(ct == null)
            return Optional.empty();
        return Optional.of(ct);
    }

    public Optional<City> getCityById(long id){

        String url = "id=" + id;

        CachePair cache = cacheManager.getCache(url);
        if(cache != null)
            return cache.getValue();

        City ct = repository.getDetailsByCityId(id);
        cacheManager.addToCache(url, ct);
        if(ct == null)
            return Optional.empty();
        return Optional.of(ct);
    }

    public CacheStats getCacheStats() {
        return new CacheStats(
                cacheManager.getRequests(),
                cacheManager.getHits(),
                cacheManager.getMisses()
        );
    }
}
