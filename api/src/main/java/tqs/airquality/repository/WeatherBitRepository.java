package tqs.airquality.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import tqs.airquality.model.City;


import java.util.logging.Logger;

@Repository
public class WeatherBitRepository {

    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/current/airquality";
    private static final String KEY = "8813e52882da41bb8ed899fdbdf7f7df";
    private static final Logger LOGGER = Logger.getLogger( WeatherBitRepository.class.getName() );
    @Autowired
    private RestTemplate restTemplate;

    public City getDetailsByCityId(long cityId){
        try {
            String url = BASE_URL + "?city_id=" + cityId + "&key=" + KEY;
            return this.restTemplate.getForObject(url, City.class);
        }catch (Exception e) {
            LOGGER.info(e.toString());
        }
        return null;
    }

    public City getDetailsByCityNameAndCountry(String cityName, String country){
        try{
            String url = BASE_URL + "?city=" + cityName + "&country=" + country + "&key=" + KEY;
            return this.restTemplate.getForObject(url, City.class);
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return null;
    }



}
