package tqs.airquality.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import tqs.airquality.model.City;

public class CityMapper {

    public static City getCity(String value){
        try {
            ObjectMapper om = new ObjectMapper();
            City ct = om.readValue(value, City.class);
            return ct;
        }catch (Exception e){
            System.err.println("Error converting to Object");
        }
        return null;
    }


}
