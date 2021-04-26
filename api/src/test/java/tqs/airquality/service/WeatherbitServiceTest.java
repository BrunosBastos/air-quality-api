package tqs.airquality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.airquality.model.City;
import tqs.airquality.repository.WeatherBitRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherbitServiceTest {


    @Mock( lenient = true)
    private WeatherBitRepository repository;

    @InjectMocks
    private WeatherbitService service;

    private final long AVEIRO_ID = 2742611l;

    @BeforeEach
    public void setUp(){

        City aveiro = new City();
        aveiro.setCity_name("Aveiro");
        aveiro.setCountry_code("PT");
        aveiro.setLon(-8.64554);
        aveiro.setLat(40.64427);
        aveiro.setState_code("02");
        aveiro.setTimezone("Europe/Lisbon");

        when(repository.getDetailsByCityId(AVEIRO_ID)).thenReturn(aveiro);
        when(repository.getDetailsByCityId(-1L)).thenReturn(null);
        when(repository.getDetailsByCityNameAndCountry("Aveiro", "PT")).thenReturn(aveiro);
        when(repository.getDetailsByCityNameAndCountry("Aveiro", "ES")).thenReturn(null);
    }

    @Test
    public void test_whenValidId_thenReturnCity() throws Exception{

        Optional<City> fromDb = service.getCityById(AVEIRO_ID);

        assertThat(fromDb.get().getCity_name()).isEqualTo("Aveiro");
        assertThat(fromDb.get().getCountry_code()).isEqualTo("PT");
        assertThat(fromDb.get().getLon()).isEqualTo(-8.64554);
        assertThat(fromDb.get().getLat()).isEqualTo(40.64427);
        assertThat(fromDb.get().getState_code()).isEqualTo("02");
        assertThat(fromDb.get().getTimezone()).isEqualTo("Europe/Lisbon");

        verify(repository, VerificationModeFactory.times(1))
                .getDetailsByCityId(anyLong());
    }

    @Test
    public void test_whenInValidId_thenReturnEmpty() throws Exception{

        Optional<City> fromDb = service.getCityById(-1L);

        assertThat(fromDb.isEmpty());

        verify(repository, VerificationModeFactory.times(1))
                .getDetailsByCityId(anyLong());
    }

    @Test
    public void test_whenValidNameCountry_thenReturnCity() throws Exception{

        Optional<City> fromDb = service.getCityByNameAndCountry("Aveiro", "PT");

        assertThat(fromDb.get().getCity_name()).isEqualTo("Aveiro");
        assertThat(fromDb.get().getCountry_code()).isEqualTo("PT");
        assertThat(fromDb.get().getLon()).isEqualTo(-8.64554);
        assertThat(fromDb.get().getLat()).isEqualTo(40.64427);
        assertThat(fromDb.get().getState_code()).isEqualTo("02");
        assertThat(fromDb.get().getTimezone()).isEqualTo("Europe/Lisbon");

        verify(repository, VerificationModeFactory.times(1))
                .getDetailsByCityNameAndCountry("Aveiro", "PT");
    }

    @Test
    public void test_whenInValidNameCountry_thenReturnEmpty() throws Exception{

        Optional<City> fromDb = service.getCityByNameAndCountry("Aveiro", "ES");

        assertThat(fromDb.isEmpty());

        verify(repository, VerificationModeFactory.times(1))
                .getDetailsByCityNameAndCountry("Aveiro", "ES");
    }


}