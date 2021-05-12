package tqs.airquality.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.airquality.model.City;

import static org.assertj.core.api.Assertions.assertThat;

class CacheManagerTest {

    @Autowired
    private CacheManager cacheManager;

    private City cityA;

    @BeforeEach
    void startUp(){

        cacheManager = new CacheManager();

        cityA = new City();
        cityA.setCity_name("Aveiro");
        cityA.setCountry_code("PT");
        cityA.setLon("-8.64554");
        cityA.setLat("40.64427");
        cityA.setState_code("02");
        cityA.setTimezone("Europe/Lisbon");

    }

    @Test
    void test_whenEmptyCache_returnEmpty(){
        assertThat(cacheManager.getCache()).isEmpty();
    }

    @Test
    void test_whenInCacheHasValue_returnValue(){

        String url = "test";
        cacheManager.addToCache(url, cityA);

        CachePair pair = cacheManager.getCache(url);
        assertThat(pair.getValue()).contains(cityA);
        assertThat(pair.getTime()).isGreaterThan(System.currentTimeMillis());
        assertThat(cacheManager.getHits()).isEqualTo(1);
        assertThat(cacheManager.getRequests()).isEqualTo(1);
        assertThat(cacheManager.getMisses()).isZero();
        assertThat(cacheManager.getCache()).hasSize(1);

    }

    @Test
    void test_whenInCacheNotHasValue_returnEmpty(){
        String url = "test";
        cacheManager.addToCache(url, null);

        CachePair pair = cacheManager.getCache(url);
        assertThat(pair.getValue()).isEmpty();
        assertThat(pair.getTime()).isGreaterThan(System.currentTimeMillis());
        assertThat(cacheManager.getHits()).isEqualTo(1);
        assertThat(cacheManager.getRequests()).isEqualTo(1);
        assertThat(cacheManager.getMisses()).isZero();
        assertThat(cacheManager.getCache()).hasSize(1);

    }

    @Test
    void test_whenNotInCache_returnNull(){
        String url = "test";

        CachePair pair = cacheManager.getCache(url);

        assertThat(pair).isNull();
        assertThat(cacheManager.getMisses()).isEqualTo(1);
        assertThat(cacheManager.getRequests()).isEqualTo(1);
        assertThat(cacheManager.getHits()).isZero();
        assertThat(cacheManager.getCache()).isEmpty();

    }

    @Test
    void test_whenTimeExpires_returnNull(){
        cacheManager.setTimeToLive(0);
        String url = "test";

        cacheManager.addToCache(url, cityA);

        CachePair pair = cacheManager.getCache(url);

        assertThat(pair).isNull();
        assertThat(cacheManager.getMisses()).isEqualTo(1);
        assertThat(cacheManager.getRequests()).isEqualTo(1);
        assertThat(cacheManager.getHits()).isZero();
        assertThat(cacheManager.getCache()).isEmpty();

    }

}
