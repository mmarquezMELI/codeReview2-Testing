package com.bootcampW22.EjercicioGlobal.controller;
import org.apache.tomcat.util.bcel.Const;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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


    @Test
    @DisplayName("Integration Test GetVehicles")
    void getVehicleTest() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/vehicles")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].brand").value("Pontiac"))
                .andExpect(jsonPath("$.length()").value(500));
    }

    @Test
    @DisplayName("Integration Test GetVehicler -ERROR")
    void getVehiclesTestError() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message")
                        .value("No se encontró ningun auto en el sistema."));
    }


    @Test
    @DisplayName("Integration Test getVehiclesByColorAndYear")
    void getVehiclesByColorAndYearTest() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vehicles/color/{color}/year/{year}","Mauv",1986)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("Mauv"))
                .andExpect(jsonPath("$[0].year").value(1986))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Integration Test getVehiclesByColorAndYear - ERROR")
        void getVehiclesByColorAndYearTestError() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vehicles/color/{color}/year/{year}","test","20332")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se encontraron vehículos con esos criterios."));
    }
}

