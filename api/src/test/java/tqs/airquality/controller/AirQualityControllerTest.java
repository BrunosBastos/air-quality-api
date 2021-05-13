package tqs.airquality.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tqs.airquality.model.City;
import tqs.airquality.service.WeatherbitService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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
        City data = new City();
        data.setCity_name("Aveiro");
        data.setCountry_code("PT");
        data.setLon("-8.64554");
        data.setLat("40.64427");
        data.setState_code("02");
        data.setTimezone("Europe/Lisbon");

        given(service.getCityById(AVEIRO_ID))
                .willReturn(Optional.of(data));

        mockMvc.perform(MockMvcRequestBuilders.get("/airquality?id="+AVEIRO_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("Aveiro"))
                .andExpect(jsonPath("country_code").value("PT"))
                .andExpect(jsonPath("state_code").value("02"))
                .andExpect(jsonPath("lat").value(40.64427))
                .andExpect(jsonPath("timezone").value("Europe/Lisbon"))
                .andExpect(jsonPath("lon").value(-8.64554));

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


}