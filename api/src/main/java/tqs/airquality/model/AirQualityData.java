package tqs.airquality.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter @Getter
public class AirQualityData implements Serializable {
    private int aqi;
    private double o3;
    private double so2;
    private double no2;
    private double co;
    private double pm10;
    private double pm25;
    private int pollen_level_tree;
    private int pollen_level_grass;
    private int pollen_level_weed;
    private int mold_level;
    private String predominant_pollen_type;

}
