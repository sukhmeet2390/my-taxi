package com.mytaxi.service.driver_car;

import com.mytaxi.dataaccessobject.DriverCarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.driver.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultDriveCarService implements DriverCarService {
    private final DriverCarRepository driverCarRepository;
    private final CarService carService;
    private final DriverService driverService;

    public DefaultDriveCarService(DriverCarRepository driverCarRepository,
                                  CarService carService,
                                  DriverService driverService) {
        this.driverCarRepository = driverCarRepository;
        this.carService = carService;
        this.driverService = driverService;
    }

    @Override
    public DriverCarDO selectCar(Long driverId, Long carId) throws EntityNotFoundException {
        DriverCarDO driverCarDO = null;
        try {
            final DriverDO driverDO = driverService.find(driverId);
            final CarDO carDO = carService.find(carId);
            if (driverDO != null || carDO != null) {
                driverCarDO = new DriverCarDO();
                driverCarDO.setCarId(carId);
                driverCarDO.setDriverId(driverId);
                driverCarRepository.save(driverCarDO);
            }
        } catch (EntityNotFoundException e) {
            log.debug("Car Data or Driver data not found" + carId + " " + driverId);
            throw e;
        }
        return driverCarDO;
    }

    @Override
    public void deselectCar(Long driverId, Long carId) throws EntityNotFoundException {
        try {
            final DriverDO driverDO = driverService.find(driverId);
            final CarDO carDO = carService.find(carId);
            if (driverDO != null && carDO != null) {
                DriverCarDO driverCarDO = driverCarRepository.findByDriverIdAndCarId(driverId, carId);
                driverCarRepository.delete(driverCarDO);
            }
        } catch (EntityNotFoundException e) {
            log.info("Car Data or Driver data not found" + carId + " " + driverId);
            throw e;
        }
    }
}
