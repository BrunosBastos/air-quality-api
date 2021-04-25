package tqs.airquality.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class City implements Serializable {

    private double lat;
    private double lon;
    private String timezone;
    private String city_name;
    private String country_code;
    private String state_code;
    private List<AirQualityData> data;


}
