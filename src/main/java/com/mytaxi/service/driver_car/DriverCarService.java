package com.mytaxi.service.driver_car;

import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.exception.*;


public interface DriverCarService {
    DriverCarDO selectCar(Long driverId, Long carId) throws DriverNotFoundException, CarNotFoundException, CarAlreadyInUseException, ConstraintsViolationException;

    void deselectCar(Long driverId, Long carId) throws EntityNotFoundException, ConstraintsViolationException, CarAlreadyInUseException;

}
