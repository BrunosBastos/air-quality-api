package tqs.airquality.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import tqs.airquality.model.City;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherBitRepositoryTest {


    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherBitRepository repository;

    private static final long AVEIRO_ID = 2742611L;
    private static final String BASE_URL = "https://api.weatherbit.io/v2.0/current/airquality";
    private static final String KEY = "8813e52882da41bb8ed899fdbdf7f7df";

    @Test
    void test_getValidCityInfoById_thenReturnCorrectData(){

        City data = new City();
        data.setCity_name("Aveiro");
        data.setCountry_code("PT");
        data.setLon("-8.64554");
        data.setLat("40.64427");
        data.setState_code("02");
        data.setTimezone("Europe/Lisbon");

        String url = BASE_URL + "?city_id=" + AVEIRO_ID + "&key=" + KEY;
        when(restTemplate.getForEntity(
                url, City.class))
          .thenReturn(new ResponseEntity<City>(data, HttpStatus.OK));

        City ct = repository.getDetailsByCityId(AVEIRO_ID);
        assertThat(ct.getCity_name()).isEqualTo("Aveiro");
        assertThat(ct.getCountry_code()).isEqualTo("PT");
        verify(restTemplate, times(1)).getForEntity(url, City.class);
        assertThat(repository.getDetailsByCityId(AVEIRO_ID)).isInstanceOf(City.class);

    }

    @Test
    void test_getValidCityInfoByName_thenReturnCorrectData(){
        assertThat(repository.getDetailsByCityNameAndCountry("Aveiro", "PT")).isInstanceOf(City.class);
    }

    @Test
    void test_getInValidCityInfoById_thenReturnNull(){
        assertThat(repository.getDetailsByCityId(1L)).isNull();
    }

    @Test
    void test_getInValidCityInfoByName_thenReturnNull(){
        assertThat(repository.getDetailsByCityNameAndCountry("Aveiro", "PT")).isNull();
    }


}