package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.DriverCarDTO;
import com.mytaxi.domainobject.DriverCarDO;

public class DriverCarMapper {
    public static DriverCarDTO makeDriverCarDTO(DriverCarDO driverCarDO) {
        return DriverCarDTO.builder()
                .carId(driverCarDO.getCarId())
                .driverId(driverCarDO.getDriverId())
                .id(driverCarDO.getId())
                .build();
    }
}
