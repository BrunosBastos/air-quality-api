package tqs.airquality.services;

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

        return Optional.empty();
    }

    public Optional<City> getCityById(long id){

        return Optional.empty();
    }

}
