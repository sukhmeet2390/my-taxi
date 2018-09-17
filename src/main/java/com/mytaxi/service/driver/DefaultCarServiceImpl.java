package com.mytaxi.service.driver;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DefaultCarServiceImpl implements CarService {
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException {
        return null;
    }

    @Override
    public CarDO create(CarDO car) throws ConstraintsViolationException {
        return null;
    }

    @Override
    public CarDO update(Long id, CarDO car) throws EntityNotFoundException, ConstraintsViolationException {
        return null;
    }

    @Override
    public List<CarDO> getCars() {
        log.info("Demanded all cars");
        return new ArrayList<>();
    }

    @Override
    public void delete(Long carId) throws EntityNotFoundException {

    }
}
