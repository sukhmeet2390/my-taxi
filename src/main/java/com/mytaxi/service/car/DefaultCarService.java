package com.mytaxi.service.car;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DefaultCarService implements CarService {
    private final CarRepository carRepository;

    public DefaultCarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDO find(Long carId) throws EntityNotFoundException {
        return carRepository.findById(carId)
                .orElseThrow(() ->
                        new EntityNotFoundException(("Could not find entity with id: " + carId)));
    }

    @Override
    public CarDO create(CarDO car) throws ConstraintsViolationException {
        log.trace("Create Car");
        CarDO carDO;
        try {
            carDO = carRepository.save(car);
        } catch (DataIntegrityViolationException e) {
            log.warn("ConstraintsViolationException while creating a driver: {}", car, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return carDO;
    }

    @Override
    @Transactional
    public CarDO update(Long id, CarDO car) throws EntityNotFoundException, ConstraintsViolationException {
        log.trace("Update car " + car);
        CarDO updateCar = find(id);
        updateCar.setConvertible(car.isConvertible());
        updateCar.setEngineType(car.getEngineType());
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
        log.trace("Demanded all cars");
        return carRepository.findAll();
    }

    @Override
    public void delete(Long carId) throws EntityNotFoundException {
        log.trace("Delete Car " + carId);
        carRepository.deleteById(carId);
    }
}
