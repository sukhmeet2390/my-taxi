package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.DriverCarDTO;
import com.mytaxi.domainobject.DriverCarDO;

import java.util.List;
import java.util.stream.Collectors;

public class DriverCarMapper {
    public static DriverCarDTO makeDriverCarDTO(DriverCarDO driverCarDO) {
        return DriverCarDTO.builder()
                .carId(driverCarDO.getCarDO().getId())
                .driverId(driverCarDO.getDriverDO().getId())
                .id(driverCarDO.getId())
                .build();
    }

    public static List<DriverCarDTO> makeDriverCarDTOList(List<DriverCarDO> driverCarDOList){
        return driverCarDOList.stream().map(DriverCarMapper::makeDriverCarDTO).collect(Collectors.toList());
    }
}
