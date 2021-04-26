package tqs.airquality.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.airquality.cache.CacheManager;
import tqs.airquality.model.City;
import tqs.airquality.service.WeatherbitService;
import tqs.airquality.utils.CityMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class AirQualityController {

    private CacheManager cacheManager = new CacheManager();

    @Autowired
    private WeatherbitService service;

    public AirQualityController(WeatherbitService service){
        this.service = service;
    }

    @GetMapping(value = "/", params = {"city", "country"})
    public ResponseEntity<City> getCityByNameCountry(
            HttpServletRequest request,
            @RequestParam("city") String city, @RequestParam("country") String country
    ){
        System.out.println(request.getRequestURI());
        String cache = cacheManager.getCache(request.getRequestURI());
        if(cache != null){
            City ct = CityMapper.getCity(cache);
            if(ct != null){
                return new ResponseEntity<City>(ct, HttpStatus.OK);
            }
            return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
        }

        Optional<City> aqd = service.getCityByNameAndCountry(city, country);
        return aqd.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(value = "/", params = "id")
    public ResponseEntity<City> getCityById(
            HttpServletRequest request,
            @RequestParam("id") long id
    ){
        String cache = cacheManager.getCache(request.getRequestURI());
        if(cache != null){
            City ct = CityMapper.getCity(cache);
            if(ct != null){
                return new ResponseEntity<City>(ct, HttpStatus.OK);
            }
            return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
        }

        Optional<City> aqd = service.getCityById(id);
        return aqd.map(city -> new ResponseEntity<>(city, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


}
