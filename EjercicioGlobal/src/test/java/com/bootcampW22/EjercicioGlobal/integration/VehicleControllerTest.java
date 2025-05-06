package com.bootcampW22.EjercicioGlobal.integration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final String URL_BASE = "/vehicles";

//0
    @Test
    @DisplayName("Integration Test GetVehicles")
    void getVehicleTest() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get(URL_BASE)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].brand").value("Pontiac"))
                .andExpect(jsonPath("$.length()").value(500));
    }
//0
    @Test
    @DisplayName("Integration Test GetVehicler -ERROR")
    void getVehiclesTestError() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value("No se encontró ningun auto en el sistema."));
    }

//1
    @Test
    @DisplayName("Integration Test getVehiclesByColorAndYear")
    void getVehiclesByColorAndYearTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_BASE+"/color/{color}/year/{year}","Mauv",1986)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("Mauv"))
                .andExpect(jsonPath("$[0].year").value(1986))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
//1
    @Test
    @DisplayName("Integration Test getVehiclesByColorAndYear - ERROR")
        void getVehiclesByColorAndYearTestError() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_BASE+"/vehicles/color/{color}/year/{year}","test",3213)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se encontraron vehículos con esos criterios."));
    }

//2
    @Test
    @DisplayName("getVehiclesByColorAndRangeOfYear - Test")
    void getVehiclesByColorAndRangeOfYearTest_statusOk() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_BASE+"/brand/{brand}/between/{start_year}/{end_year}","Pontiac",1986,1986)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].brand").value("Pontiac"))
                .andExpect(jsonPath("$.size()").value(2));

    }

    //2
    @Test
    @DisplayName("getVehiclesByColorAndRangeOfYear - Error")
    void getVehiclesByColorAndRangeOfYearTest_notFound() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_BASE+"/brand/{brand}/between/{start_year}/{end_year}","Pontiac",1321,1321)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("No se encontraron vehículos con esos criterios."));
    }

//3
    @Test
    @DisplayName("calculateAvgSpeedByBrand - Test")
    void getVehiclesByColorAndRangeOfYear_statusOk() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_BASE+"/average_speed/brand/{brand}","Pontiac")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.average_speed").value(168.11));
    }
//3
    @Test
    @DisplayName("calculateAvgSpeedByBrand - Error")
    void calculateAvgSpeedByBrand_statusError() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get(URL_BASE+"/average_speed/brand/{brand}","SADPATH")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se encontraron vehículos de esa marca."));
    }

}

