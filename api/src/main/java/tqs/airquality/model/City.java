package tqs.airquality.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
public class City implements Serializable {

    private String lat;
    private String lon;
    private String timezone;
    private String city_name;
    private String country_code;
    private String state_code;
    private List<AirQualityData> data;


}
