package tqs.airquality.cache;

import lombok.Getter;
import tqs.airquality.model.City;

import java.util.Optional;

@Getter
public class CachePair {
    private final Optional<City> value;
    private final long time;

    public CachePair(Optional<City> value, Long time){
        this.value = value;
        this.time = time;
    }
}
