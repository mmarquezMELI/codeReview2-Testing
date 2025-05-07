package com.bootcampW22.EjercicioGlobal.services;
import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgCapacityByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgSpeedByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
import com.bootcampW22.EjercicioGlobal.service.VehicleServiceImpl;
import com.bootcampW22.EjercicioGlobal.utils.CustomFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
public class VehicleServiceTest {

    @Mock
    private VehicleRepositoryImpl vehicleRepository;
    @InjectMocks
    private VehicleServiceImpl vehicleService;

    //0
    @Test
    @DisplayName("Test de retorno de todos los vehiculos")
    void searchAllVehiclesTest() {
        //ARRANGE
        List<Vehicle> listVehicle = new ArrayList<>();
        listVehicle.add(CustomFactory.getVehicle(1L));
        listVehicle.add(CustomFactory.getVehicle(2L));
        List<VehicleDto> esperado = listVehicle.stream().map(x -> CustomFactory.EntitytoDto(x)).toList();
        Mockito.when(vehicleRepository.findAll()).thenReturn(listVehicle);
        //ACT
        List<VehicleDto> resultado = vehicleService.searchAllVehicles();
        //ASSERT
        Assertions.assertEquals(esperado, resultado);
        Assertions.assertEquals(esperado.get(0).getId(), resultado.get(0).getId());
        Mockito.verify(vehicleRepository,Mockito.times(1)).findAll();
    }

    //0
    @Test
    @DisplayName("Camino con error")
    void testSearchAllThrowError() {
        Mockito.when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.searchAllVehicles());
    }

    //1
    @Test
    @DisplayName("Buscar por color y año")
    void seacrhByYearAndColorTest() {
        //ARRANGE
        List<Vehicle> listaFiltrada = new ArrayList<>();
        listaFiltrada.add(CustomFactory.getVehicle(1L));
        listaFiltrada.add(CustomFactory.getVehicle(2L));
        Mockito.when(vehicleRepository.findVehiclesByYearAndColor("red", 2001)).thenReturn(listaFiltrada);
        List<VehicleDto> esperado = new ArrayList<>();
        esperado.add(CustomFactory.getVehicleDto(1L));
        esperado.add(CustomFactory.getVehicleDto(2L));
        //ACT
        List<VehicleDto> resultado = vehicleService.searchVehiclesByYearAndColor("red", 2001);
        //ASSERT
        Assertions.assertEquals(esperado, resultado);
        Assertions.assertEquals(esperado.size(), resultado.size());
        Mockito.verify(vehicleRepository).findVehiclesByYearAndColor("red", 2001);
    }

    //1
    @Test
    @DisplayName("Error search by year and color")
    void throwErroryearandcolor() {
        //ARRANGE & ACT
        Mockito.when(vehicleRepository.findVehiclesByYearAndColor("red", 2001)).thenReturn(Collections.emptyList());
        //ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.searchVehiclesByYearAndColor("red", 2001));
    }

    //2
    @Test
    @DisplayName("Test search vehicles brand and range of year")
    void searchVehiclesByBrandAndRangeOfYearTest() {
        //ARRANGE
        List<Vehicle> listVehicle = CustomFactory.getListVehicleEqualBrandAndRangeYear(1L, "Fiat");
        Mockito.when(vehicleRepository.findVehiclesByBrandAndRangeOfYear("Fiat", 2020, 2020)).thenReturn(listVehicle);
        List<VehicleDto> esperado = CustomFactory.getListVehicleDtoEqualBrandAndRangeYear(1L, "Fiat");
        //ACT
        List<VehicleDto> resultado = vehicleService.searchVehiclesByBrandAndRangeOfYear("Fiat", 2020, 2020);
        //ASSERT
        Assertions.assertEquals(esperado, resultado);
        Assertions.assertEquals(esperado.size(), resultado.size());
        Mockito.verify(vehicleRepository).findVehiclesByBrandAndRangeOfYear("Fiat", 2020, 2020);
    }

    //2
    @Test
    @DisplayName("Error - Test search vehicles brand and range of year")
    void searchVehiclesByBrandAndRangeOfYearTestError() {
        //ARRANGE & ACT
        Mockito.when(vehicleRepository.findVehiclesByBrandAndRangeOfYear("Fiat", 2020, 2020)).thenReturn(Collections.emptyList());
        //ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.searchVehiclesByBrandAndRangeOfYear("Fiat", 2020, 2020));
    }

    //3
    @Test
    @DisplayName("Calculo de la velocidad promedio por marca")
    void AvgSpeedByBrandTest() {
        //ARRANGE
        List<Vehicle> vehicleList = CustomFactory.getListVehicleEqualBrand(1L, "Fiat");
        double esperado =
                vehicleList.stream()
                        .mapToDouble(x -> Double.parseDouble(x.getMax_speed()))
                        .average()
                        .orElse(0.0);
        double esperadoRedondeo = Math.round(esperado * 100) / 100;
        VehicleAvgSpeedByBrandDto avgEsperado = new VehicleAvgSpeedByBrandDto(esperadoRedondeo);
        Mockito.when(vehicleRepository.findVehiclesByBrand("Fiat")).thenReturn(vehicleList);
        //ACT
        VehicleAvgSpeedByBrandDto result = vehicleService.calculateAvgSpeedByBrand("Fiat");
        //ASSERT
        Assertions.assertEquals(avgEsperado.getAverage_speed(), result.getAverage_speed(), 0.01);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findVehiclesByBrand("Fiat");
    }

    @Test
    @DisplayName("Calculo de la velocidad con error")
    void AvgSpeedByBrandThrownTest() {
        //ARRANGE & ACT
        Mockito.when(vehicleRepository.findVehiclesByBrand("Fiat")).thenReturn(Collections.emptyList());
        //ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.calculateAvgSpeedByBrand("Fiat"));
    }

    @Test
    @DisplayName("Calculo de capacidad de pasajeros por marca")
    void calculateAvgCapacityBrand() {
        //ARRANGE
        List<Vehicle> listVehicle = CustomFactory.getListVehicleEqualBrand(1L, "Fiat");
        Mockito.when(vehicleRepository.findVehiclesByBrand("Fiat")).thenReturn(listVehicle);
        Double avgPassager = listVehicle.stream()
                .mapToInt(x -> x.getPassengers())
                .average()
                .orElse(0.0);
        Double avgPassagerProm = Math.round(avgPassager * 100.0) / 100.0;
        VehicleAvgCapacityByBrandDto esperado = new VehicleAvgCapacityByBrandDto(avgPassagerProm);
        //ACT
        VehicleAvgCapacityByBrandDto resultado = vehicleService.calculateAvgCapacityByBrand("Fiat");
        //ASSERT
        Assertions.assertEquals(esperado.getAverage_capacity(), resultado.getAverage_capacity(), 0.1);
        Mockito.verify(vehicleRepository, Mockito.times(1)).findVehiclesByBrand("Fiat");
    }

    @Test
    @DisplayName("Calculo de capacidad de pasajeros por marca - ERROR")
    void calculateAvgCapacityBrandERROR() {
        //ARRANGE & ACT
        Mockito.when(vehicleRepository.findVehiclesByBrand("Fiat")).thenReturn(Collections.emptyList());
        //ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.calculateAvgCapacityByBrand("Fiat"));
    }

    @Test
    @DisplayName("Calculo de vehiculos por rango de peso")
    void searchVehiclesByRangeOfWeightTest() {
        //ARRANGE
        List<Vehicle> listVehicle = CustomFactory.getListVehicleEqualWeight(1L, 200.0);
        Mockito.when(vehicleRepository.findVehiclesByRangeOfWeight(200.0, 200.0)).thenReturn(listVehicle);
        List<VehicleDto> esperado = CustomFactory.getListVehicleDtoEqualWeight(1L, 200.0);
        //ACT
        List<VehicleDto> resultado = vehicleService.searchVehiclesByRangeOfWeight(200.0, 200.0);
        //ASSERT
        Mockito.verify(vehicleRepository, Mockito.times(1)).findVehiclesByRangeOfWeight(200.0, 200.0);
        Assertions.assertEquals(esperado, resultado);
        Assertions.assertEquals(esperado.size(), resultado.size());
    }

    @Test
    @DisplayName("Calculo de vehiculos por rango de peso - ERROR")
    void searchVehiclesByRangeOfWeightTestError() {
        //ARRANGE & ACT
        Mockito.when(vehicleRepository.findVehiclesByRangeOfWeight(200.0,200.0)).thenReturn(List.of());
        //ASSERT
        Assertions.assertThrows(NotFoundException.class,() -> vehicleService.searchVehiclesByRangeOfWeight(200.0,200.0));
    }
}