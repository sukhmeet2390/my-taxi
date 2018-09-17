package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarMapper {
    public static List<CarDTO> makeDriverDTOList(Collection<CarDO> cars) {
        return cars.stream()
                .map(CarMapper::makeCarDTO)
                .collect(Collectors.toList());
    }

    public static CarDTO makeCarDTO(CarDO carDO) {
        return CarDTO.builder()
                .id(carDO.getId())
                .licensePlate(carDO.getLicensePlate())
                .seatCount(carDO.getSeatCount())
                .rating(carDO.getRating())
                .engine(carDO.getEngineType().toString())
                .manufacturer(carDO.getManufacturer())
                .deleted(carDO.getDeleted())
                .convertible(carDO.isConvertible())
                .color(carDO.getColor())
                .model(carDO.getModel())
                .build();

    }
}
