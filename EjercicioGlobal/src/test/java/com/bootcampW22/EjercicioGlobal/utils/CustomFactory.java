package com.bootcampW22.EjercicioGlobal.utils;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public final class CustomFactory {
    private CustomFactory() {}
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Long ID = 1L;
    private static final String BRAND = "Chevrolet";
    private static final String MODEL = "Prima";
    private static final String REGISTRATION = "ABC 123";
    private static final String COLOR = "Red";
    private static final int YEAR = 2020;
    private static final String MAX_SPEED = "100";
    private static final int PASSAGERS = 4;
    private static final String FUEL_TYPE = "Nafta";
    private static final String TRANSMISSION = "Automatic";
    private static final double HEIGHT = 200.0;
    private static final double WIDTH = 50.0;
    private static final double WEIGHT = 150.0;

    public static Vehicle vehicleBase() {
        return new Vehicle(
            ID, BRAND, MODEL,REGISTRATION,COLOR,YEAR,MAX_SPEED,PASSAGERS,FUEL_TYPE,TRANSMISSION,HEIGHT,WIDTH,WEIGHT);
    }
    public static VehicleDto EntitytoDto(Vehicle vehicle){
        return objectMapper.convertValue(vehicle,VehicleDto.class);
    }

    public static Vehicle vehicleWithColorAndYear(Long id,String color,Integer year){
        Vehicle vehicleBase =  vehicleBase();
        return new Vehicle(
                id,
                vehicleBase.getBrand(),
                vehicleBase.getModel(),
                vehicleBase.getRegistration(),
                color,
                year,
                vehicleBase.getMax_speed(),
                vehicleBase.getPassengers(),
                vehicleBase.getFuel_type(),
                vehicleBase.getTransmission(),
                vehicleBase.getHeight(),
                vehicleBase.getWidth(),
                vehicleBase.getWeight()
        );
    }

    public static List<Vehicle> listColorAndYear(String color, Integer year){
        List<Vehicle> vehicleListColorAndYear = new ArrayList<>();
        vehicleListColorAndYear.add(vehicleWithColorAndYear(1L,color,year));
        vehicleListColorAndYear.add(vehicleWithColorAndYear(2L,color,year));
        return vehicleListColorAndYear;
    }

    public static Vehicle vehicleWithBrandAndYear(Long id,String brand, Integer year){
        Vehicle vehicleBase =  vehicleBase();
        return new Vehicle(
                id,
                brand,
                vehicleBase.getModel(),
                vehicleBase.getRegistration(),
                vehicleBase.getColor(),
                year,
                vehicleBase.getMax_speed(),
                vehicleBase.getPassengers(),
                vehicleBase.getFuel_type(),
                vehicleBase.getTransmission(),
                vehicleBase.getHeight(),
                vehicleBase.getWidth(),
                vehicleBase.getWeight()
        );
    }

    public static List<Vehicle> listWithBrandAndYear(String brand,Integer year){
        List<Vehicle> listVehicle = new ArrayList<>();
        listVehicle.add(vehicleWithBrandAndYear(1L,brand,year));
        listVehicle.add(vehicleWithBrandAndYear(2L,brand,year));
        return listVehicle;
    }


    public static Vehicle vehicleWithBrand(Long id,String brand){
        Vehicle vehicleBase =  vehicleBase();
        return new Vehicle(
                id,
                brand,
                vehicleBase.getModel(),
                vehicleBase.getRegistration(),
                vehicleBase.getColor(),
                vehicleBase.getYear(),
                vehicleBase.getMax_speed(),
                vehicleBase.getPassengers(),
                vehicleBase.getFuel_type(),
                vehicleBase.getTransmission(),
                vehicleBase.getHeight(),
                vehicleBase.getWidth(),
                vehicleBase.getWeight()
        );
    }

    public static List<Vehicle> listWithBrand(String brand){
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicleWithBrand(1L,brand));
        vehicleList.add(vehicleWithBrand(2L,brand));
        return vehicleList;
    }

    public static Vehicle vehicleWithWeight(Long id,Double weight){
        Vehicle vehicleBase =  vehicleBase();
        return new Vehicle(
                id,
                vehicleBase.getBrand(),
                vehicleBase.getModel(),
                vehicleBase.getRegistration(),
                vehicleBase.getColor(),
                vehicleBase.getYear(),
                vehicleBase.getMax_speed(),
                vehicleBase.getPassengers(),
                vehicleBase.getFuel_type(),
                vehicleBase.getTransmission(),
                vehicleBase.getHeight(),
                vehicleBase.getWidth(),
                weight
        );
    }

    public static List<Vehicle> listWithWeight(Double weight){
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicleWithWeight(1L,weight));
        vehicleList.add(vehicleWithWeight(2L,weight));
        return vehicleList;
    }
}
