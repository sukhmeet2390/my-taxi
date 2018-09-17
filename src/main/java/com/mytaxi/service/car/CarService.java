package com.mytaxi.service.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;

public interface CarService {
    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO car) throws ConstraintsViolationException;

    CarDO update(Long id, CarDO car) throws EntityNotFoundException, ConstraintsViolationException;

    List<CarDO> getCars();

    void delete(Long carId) throws EntityNotFoundException;
}
