package com.mytaxi.service.driver_car;

import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.exception.EntityNotFoundException;


public interface DriverCarService {
    DriverCarDO selectCar(Long driverId, Long carId) throws EntityNotFoundException;

    void deselectCar(Long driverId, Long carId) throws EntityNotFoundException;

}
