package tqs.airquality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.airquality.model.City;
import tqs.airquality.repository.WeatherBitRepository;

import java.util.Optional;

@Service
public class WeatherbitService {

    @Autowired
    private WeatherBitRepository repository;

    public Optional<City> getCityByNameAndCountry(String city, String country){

        City ct = repository.getDetailsByCityNameAndCountry(city, country);
        if(ct == null)
            return Optional.empty();
        return Optional.of(ct);
    }

    public Optional<City> getCityById(long id){

        City city = repository.getDetailsByCityId(id);
        if(city == null)
            return Optional.empty();
        return Optional.of(city);
    }



}
