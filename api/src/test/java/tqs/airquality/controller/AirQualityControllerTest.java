package tqs.airquality.controller;

import org.apache.commons.exec.ExecuteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tqs.airquality.model.AirQualityData;
import tqs.airquality.model.CacheStats;
import tqs.airquality.model.City;
import tqs.airquality.service.WeatherbitService;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@WebMvcTest(AirQualityController.class)
class AirQualityControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherbitService service;

    private final long AVEIRO_ID = 2742611l;

    @Test
    void test_getCityValidNameCountry_thenReturnData() throws  Exception{

        City data = new City();
        data.setCity_name("Aveiro");
        data.setCountry_code("PT");
        data.setLon("-8.64554");
        data.setLat("40.64427");
        data.setState_code("02");
        data.setTimezone("Europe/Lisbon");

        given(service.getCityByNameAndCountry("Aveiro", "PT"))
                .willReturn(Optional.of(data));

        mockMvc.perform(MockMvcRequestBuilders.get("/airquality?city=Aveiro&country=PT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("Aveiro"))
                .andExpect(jsonPath("country_code").value("PT"))
                .andExpect(jsonPath("state_code").value("02"))
                .andExpect(jsonPath("lat").value(40.64427))
                .andExpect(jsonPath("timezone").value("Europe/Lisbon"))
                .andExpect(jsonPath("lon").value(-8.64554));

        verify(service, times(1)).getCityByNameAndCountry("Aveiro", "PT");
    }

    @Test
    void test_getCityByInvalidNameCountry_thenReturnNotFound() throws Exception{
        given(service.getCityByNameAndCountry("Aveiro", "ES"))
                .willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/airquality?city=Aveiro&country=ES"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCityByNameAndCountry("Aveiro", "ES");

    }


    @Test
    void test_getCityByValidId_thenReturnData() throws Exception{
        City city = new City();
        city.setCity_name("Aveiro");
        city.setCountry_code("PT");
        city.setLon("-8.64554");
        city.setLat("40.64427");
        city.setState_code("02");
        city.setTimezone("Europe/Lisbon");

        AirQualityData data = new AirQualityData();
        data.setAqi(1);data.setCo(1);data.setNo2(1);data.setO3(1);data.setSo2(1);data.setPm10(1);
        data.setPm25(1);data.setPollen_level_grass(1);data.setPollen_level_tree(1);data.setPollen_level_weed(1);
        data.setMold_level(1);data.setPredominant_pollen_type("");

        city.setData(Arrays.asList(data));

        given(service.getCityById(AVEIRO_ID))
                .willReturn(Optional.of(city));

        mockMvc.perform(MockMvcRequestBuilders.get("/airquality?id="+AVEIRO_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("Aveiro"))
                .andExpect(jsonPath("country_code").value("PT"))
                .andExpect(jsonPath("state_code").value("02"))
                .andExpect(jsonPath("lat").value(40.64427))
                .andExpect(jsonPath("timezone").value("Europe/Lisbon"))
                .andExpect(jsonPath("lon").value(-8.64554))
                .andExpect(jsonPath("data[0].aqi").exists())
                .andExpect(jsonPath("data[0].o3").exists())
                .andExpect(jsonPath("data[0].so2").exists())
                .andExpect(jsonPath("data[0].no2").exists())
                .andExpect(jsonPath("data[0].co").exists())
                .andExpect(jsonPath("data[0].pm10").exists())
                .andExpect(jsonPath("data[0].pm25").exists())
                .andExpect(jsonPath("data[0].mold_level").exists())
                .andExpect(jsonPath("data[0].pollen_level_tree").exists())
                .andExpect(jsonPath("data[0].pollen_level_grass").exists())
                .andExpect(jsonPath("data[0].pollen_level_weed").exists())
                .andExpect(jsonPath("data[0].predominant_pollen_type").exists());


        verify(service, times(1)).getCityById(AVEIRO_ID);
    }

    @Test
    void test_getCityByInvalidId_thenReturnNotFound() throws Exception{
        given(service.getCityById(-1l))
                .willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/airquality?id=-1"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCityById(-1l);

    }

    @Test
    void test_getCacheEmpty_thenReturnEmpty() throws Exception{
        given(service.getCacheStats()).willReturn(new CacheStats(0,0,0));

        mockMvc.perform(MockMvcRequestBuilders.get("/cache"))
                .andExpect(status().isOk()).andExpect(jsonPath("requests").value(0))
                .andExpect(jsonPath("hits").value(0)).andExpect(jsonPath("misses").value(0));

        verify(service, times(1)).getCacheStats();

    }

    @Test
    void test_getCacheResults_thenReturnResults() throws Exception{
        CacheStats stats = new CacheStats(5,5,5);
        given(service.getCacheStats()).willReturn(stats);
        mockMvc.perform(MockMvcRequestBuilders.get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("requests").value(stats.getRequests()))
                .andExpect(jsonPath("hits").value(stats.getHits()))
                .andExpect(jsonPath("misses").value(stats.getMisses()));
        verify(service, times(1)).getCacheStats();

    }

}