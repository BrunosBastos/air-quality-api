package tqs.airquality.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import tqs.airquality.AirqualityApplication;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AirqualityApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControllerIT {

    @Autowired
    private MockMvc servlet;

    private final long AVEIRO_ID = 2742611L;

    @Test
    void test_whenGetCorrectId_thenReturnData() throws Exception {
        this.servlet.perform(get("/airquality?id=" + AVEIRO_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name", is("Aveiro")))
                .andExpect(jsonPath("country_code", is("PT")));
    }

    @Test
    void test_whenGetCorrectName_thenReturnData() throws Exception{
        this.servlet.perform(get("/airquality?city=Aveiro&country=PT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name", is("Aveiro")))
                .andExpect(jsonPath("country_code", is("PT")));
    }

    @Test
    void test_whenGetIncorretName_thenReturnNotFound() throws Exception{
        this.servlet.perform(get("/airquality?city=Aveiro&country=ES"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_whenGetIncorrectId_thenReturnNotFound() throws Exception{
        this.servlet.perform(get("/airquality?id=" + -1))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_whenGetCorrectNameTwice_thenReturnFromCache() throws Exception{

        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(0)))
                .andExpect(jsonPath("requests", is(0)));
        this.servlet.perform(get("/airquality?city=Aveiro&country=PT"));
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(1)));
        this.servlet.perform(get("/airquality?city=Aveiro&country=PT"));
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(1)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(2)));
    }

    @Test
    void test_whenGetInCorrectNameTwice_thenReturnFromCache() throws Exception{

        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(0)))
                .andExpect(jsonPath("requests", is(0)));
        this.servlet.perform(get("/airquality?city=Aveiro&country=ES"))
                .andExpect(status().isNotFound());
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(1)));
        this.servlet.perform(get("/airquality?city=Aveiro&country=ES"))
                .andExpect(status().isNotFound());
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(1)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(2)));
    }

    @Test
    void test_whenGetCorrectIdTwice_thenReturnFromCache() throws Exception{

        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(0)))
                .andExpect(jsonPath("requests", is(0)));
        this.servlet.perform(get("/airquality?id="+AVEIRO_ID));
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(1)));
        this.servlet.perform(get("/airquality?id="+AVEIRO_ID));
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(1)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(2)));
    }

    @Test
    void test_whenGetInCorrectIdTwice_thenReturnFromCache() throws Exception{

        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(0)))
                .andExpect(jsonPath("requests", is(0)));
        this.servlet.perform(get("/airquality?id=-1"))
                .andExpect(status().isNotFound());
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(0)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(1)));
        this.servlet.perform(get("/airquality?id=-1"))
                .andExpect(status().isNotFound());
        this.servlet.perform(get("/cache"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hits", is(1)))
                .andExpect(jsonPath("misses", is(1)))
                .andExpect(jsonPath("requests", is(2)));
    }

}