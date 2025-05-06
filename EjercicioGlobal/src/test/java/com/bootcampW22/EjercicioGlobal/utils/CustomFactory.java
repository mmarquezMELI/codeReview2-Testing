package com.bootcampW22.EjercicioGlobal.utils;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public final class CustomFactory {
    private CustomFactory() {}
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static Vehicle getVehicle(Long id){
        return new Vehicle(
                id, "test", "test", "test", "test", 1, "1", 1, "test", "test", 1.1, 1.1, 1.1
        );
    }
    public static VehicleDto getVehicleDto(Long id){
        return objectMapper.convertValue(getVehicle(id),VehicleDto.class);
    }

    public static List<Vehicle> getListVehicleEqualYearAndColor(Long id,String color, Integer year){
        List<Vehicle> lista = List.of(
                new Vehicle(id, "test", "test", "test", color, year, "1", 1, "test", "test", 1.1, 1.1, 1.1),
                new Vehicle(id+1L, "test", "test", "test", color, year, "1", 1, "test", "test", 1.1, 1.1, 1.1)
        );
        return lista;
    }

    public static List<VehicleDto> getVehicleDtoEqualYearAndColor(Long id,String color, Integer year){
        return getListVehicleEqualYearAndColor(id,color,year).stream().map(x -> objectMapper.convertValue(x,VehicleDto.class)).toList();
    }

    public static List<Vehicle> getListVehicleEqualBrandAndRangeYear(Long id,String brand){
        List<Vehicle> lista = List.of(
                new Vehicle(id, brand, "test", "test", "red", 2020, "1", 1, "test", "test", 1.1, 1.1, 1.1),
                new Vehicle(id+1L, brand, "test", "test", "green", 2020, "1", 1, "test", "test", 1.1, 1.1, 1.1)
        );
        return lista;
    }

    public static List<VehicleDto> getListVehicleDtoEqualBrandAndRangeYear(Long id,String brand){
        return getListVehicleEqualBrandAndRangeYear(id,brand).stream().map(x -> objectMapper.convertValue(x,VehicleDto.class)).toList();
    }

    public static List<Vehicle> getListVehicleEqualBrand(Long id,String brand){
        List<Vehicle> lista = List.of(
                new Vehicle(id, brand, "test", "test", "red", 2020, "1", 1, "test", "test", 1.1, 1.1, 1.1),
                new Vehicle(id+1L, brand, "test", "test", "green", 2020, "1", 1, "test", "test", 1.1, 1.1, 1.1)
        );
        return lista;
    }

    public static List<VehicleDto> getListVehicleDtoEqualBrand(Long id,String brand){
        return getListVehicleEqualBrand(id,brand).stream().map(x -> objectMapper.convertValue(x,VehicleDto.class)).toList();
    }

    public static List<Vehicle> getListVehicleEqualWeight(Long id,Double weight){
        List<Vehicle> lista = List.of(
                new Vehicle(id, "Tesla", "test", "test", "red", 2020, "1", 1, "test", "test", 1.1, 1.1, weight),
                new Vehicle(id+1L, "Tesla", "test", "test", "green", 2020, "1", 1, "test", "test", 1.1, 1.1, weight)
        );
        return lista;
    }

    public static List<VehicleDto> getListVehicleDtoEqualWeight(Long id,Double weight){
        return getListVehicleEqualWeight(id,weight).stream().map(x -> objectMapper.convertValue(x,VehicleDto.class)).toList();
    }


}
