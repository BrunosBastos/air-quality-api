package tqs.airquality.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tqs.airquality.model.City;
import tqs.airquality.services.WeatherbitService;

import java.util.Optional;

@RestController
public class AirQualityController {


    private WeatherbitService service;

    public AirQualityController(WeatherbitService service){
        this.service = service;
    }

    @GetMapping(value = "/", params = {"city", "country"})
    public ResponseEntity<City> getCityByNameCountry(
            @RequestParam("city") String city, @RequestParam("country") String country
    ){
        Optional<City> aqd = service.getCityByNameAndCountry(city, country);
        if(!aqd.isEmpty())
            return new ResponseEntity<>(aqd.get(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/", params = "id")
    public ResponseEntity<City> getCityById(
            @RequestParam("id") long id
    ){
        Optional<City> aqd = service.getCityById(id);
        if(!aqd.isEmpty())
            return new ResponseEntity<>(aqd.get(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
