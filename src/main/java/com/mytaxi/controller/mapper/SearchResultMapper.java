package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.SearchResultDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainobject.DriverDO;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultMapper {
    public static SearchResultDTO makeSearchResultDTO(DriverCarDO driverCarDO) {
        final DriverDO driverDO = driverCarDO.getDriverDO();
        final CarDO carDO = driverCarDO.getCarDO();
        return SearchResultDTO.builder()
                .username(driverDO.getUsername())
                .coordinate(driverDO.getCoordinate())
                .licensePlate(carDO.getLicensePlate())
                .seatCount(carDO.getSeatCount())
                .convertible(carDO.isConvertible())
                .rating(carDO.getRating())
                .engine(carDO.getEngine().toString())
                .manufacturer(carDO.getManufacturer())
                .deleted(carDO.getDeleted())
                .color(carDO.getColor())
                .model(carDO.getModel()).build();
    }

    public static List<SearchResultDTO> makeSearchResultDTOListFromDriverCarDO(List<DriverCarDO> driverCarDOList) {
        return driverCarDOList.stream()
                .map(SearchResultMapper::makeSearchResultDTO)
                .collect(Collectors.toList());
    }

    public static SearchResultDTO makeSearchResultDTOFromDriverDO(DriverDO driverDO) {
        return SearchResultDTO.builder()
                .username(driverDO.getUsername())
                .coordinate(driverDO.getCoordinate())
                .build();
    }

    public static List<SearchResultDTO> makeSearchResultDTOListFromDriverDO(List<DriverDO> driverDOList) {
        return driverDOList.stream()
                .map(SearchResultMapper::makeSearchResultDTOFromDriverDO)
                .collect(Collectors.toList());
    }
}
