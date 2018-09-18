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
        return new SearchResultDTO(driverDO.getUsername(), driverDO.getCoordinate(),
                carDO.getLicensePlate(), carDO.getSeatCount(), carDO.isConvertible(),
                carDO.getRating(), carDO.getEngine().toString(), carDO.getManufacturer(),
                carDO.getDeleted(), carDO.getColor(), carDO.getModel());
    }

    public static List<SearchResultDTO> makeSearchResultDTOList(List<DriverCarDO> driverCarDOList) {
        return driverCarDOList.stream()
                .map(SearchResultMapper::makeSearchResultDTO).collect(Collectors.toList());
    }
}
