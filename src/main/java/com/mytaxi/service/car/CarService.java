package com.mytaxi.service.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.CarNotFoundException;
import com.mytaxi.exception.ConstraintsViolationException;

import java.util.List;

public interface CarService {
    CarDO find(Long carId) throws CarNotFoundException;

    CarDO create(CarDO car) throws ConstraintsViolationException;

    CarDO update(Long id, CarDO car) throws CarNotFoundException, ConstraintsViolationException;

    List<CarDO> getCars();

    CarDO delete(Long carId) throws CarNotFoundException;
}
