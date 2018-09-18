package com.mytaxi.service.car;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.CarNotFoundException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
@Slf4j
public class DefaultCarService implements CarService {
    private final CarRepository carRepository;

    public DefaultCarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDO find(Long carId) throws CarNotFoundException {
        log.trace("Find car {}", carId);
        return carRepository.findById(carId).orElseThrow(() ->
                new CarNotFoundException(("Could not find car with id: " + carId)));
    }

    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException {
        log.debug("Create Car {}", carDO);
        CarDO car;
        try {
            car = carRepository.save(carDO);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            log.warn("ConstraintsViolationException while creating a car: {}", carDO, e.getMessage());
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }

    @Override
    @Transactional
    public CarDO update(Long id, CarDO car) throws EntityNotFoundException, ConstraintsViolationException {
        log.debug("Update car " + car);
        CarDO updateCar = find(id);
        updateCar.setConvertible(car.isConvertible());
        updateCar.setEngine(car.getEngine());
        updateCar.setLicensePlate(car.getLicensePlate());
        updateCar.setRating(car.getRating());
        updateCar.setSeatCount(car.getSeatCount());
        updateCar.setColor(car.getColor());
        updateCar.setModel(car.getModel());
        updateCar.setManufacturer(car.getManufacturer());
        return updateCar;
    }

    @Override
    public List<CarDO> getCars() {
        log.debug("Demanded all cars");
        return carRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException {
        log.debug("Delete Car " + carId);
        CarDO carDO = find(carId);
        carDO.setDeleted(true);
    }
}
