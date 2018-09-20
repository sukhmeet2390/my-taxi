package com.mytaxi.service.car;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.CarNotFoundException;
import com.mytaxi.exception.ConstraintsViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * <p>
 * Service to encapsulate the link between DAO and controller and to have business logic and search for some car specific things.
 * <p/>
 */
@Service
@Slf4j
public class DefaultCarService implements CarService {
    private final CarRepository carRepository;

    public DefaultCarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     *
     * @param carId
     * @return CarDO found car
     * @throws CarNotFoundException Exception
     */
    @Override
    public CarDO find(Long carId) throws CarNotFoundException {
        log.debug("Find car {}", carId);
        return carRepository.findById(carId).orElseThrow(() ->
                new CarNotFoundException(("Could not find car with id: " + carId)));
    }

    /**
     *
     * @param carDO CarDO
     * @return CarDO created car
     * @throws ConstraintsViolationException exception
     */
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

    /**
     *
     * @param id long
     * @param car long
     * @return updated CarDO
     * @throws CarNotFoundException if trying to update car
     * @throws ConstraintsViolationException if updated car doesn't satisfies the not null values
     */
    @Override
    @Transactional
    public CarDO update(Long id, CarDO car) throws CarNotFoundException, ConstraintsViolationException {
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
        try {
            return carRepository.save(updateCar);
        } catch (ConstraintViolationException e) {
            log.debug("Unable to save car data {}", updateCar);
            throw new ConstraintsViolationException(e.getMessage());
        }
    }

    /**
     *
     * @return List<CarDO> - all cars
     */
    @Override
    public List<CarDO> getCars() {
        log.debug("Demanded all cars");
        return carRepository.findAll();
    }

    /**
     *
     * @param carId long
     * @return deleted carDO
     * @throws CarNotFoundException exception
     */
    @Override
    @Transactional
    public CarDO delete(Long carId) throws CarNotFoundException {
        log.debug("Delete Car " + carId);
        CarDO carDO = find(carId);
        carDO.setDeleted(true);
        return carRepository.save(carDO);
    }
}
