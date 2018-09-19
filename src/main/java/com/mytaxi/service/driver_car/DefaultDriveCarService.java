package com.mytaxi.service.driver_car;

import com.mytaxi.dataaccessobject.DriverCarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.*;
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
    public DriverCarDO selectCar(Long driverId, Long carId) throws DriverNotFoundException,CarNotFoundException,
            CarAlreadyInUseException, DriverOfflineException {
        log.debug("Select Car {} / {}", driverId, carId);
        DriverCarDO alreadySelectedCar = driverCarRepository.findByCarDO_Id(carId);
        if (alreadySelectedCar != null) throw new CarAlreadyInUseException("Car Already in use by some other driver");
        try {
            DriverDO driverDO = driverService.find(driverId);
            CarDO carDO = carService.find(carId);
            if (OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus())) {
                return driverCarRepository.save(new DriverCarDO(driverDO, carDO));
            } else {
                throw new DriverOfflineException("Driver needs to be online to select the car");
            }
        } catch (DriverNotFoundException e) {
            log.debug("Driver data not found" + carId + " " + driverId);
            throw e;
        } catch (CarNotFoundException e) {
            log.debug("Car Data not found" + carId + " " + driverId);
            throw e;
        }
    }

    @Override
    public void deselectCar(Long driverId, Long carId) throws EntityNotFoundException, DriverOfflineException, CarAlreadyInUseException {
        log.debug("Deselect Car {} / {}", driverId, carId);
        final DriverCarDO alreadySelectedCar = driverCarRepository.findByCarDO_Id(carId);
        if (alreadySelectedCar == null || !alreadySelectedCar.getDriverDO().getId().equals(driverId)) {
            throw new CarAlreadyInUseException("Car is not selected by this driver.");
        }
        try {
            final DriverDO driverDO = driverService.find(driverId);
            final CarDO carDO = carService.find(carId);
            if (OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus())) {
                DriverCarDO driverCarDO = driverCarRepository.findByDriverDO_IdAndCarDO_Id(driverId, carId);
                driverCarRepository.delete(driverCarDO);
            } else {
                throw new DriverOfflineException("Driver needs to be online to deselect the car");
            }
        } catch (EntityNotFoundException e) {
            log.info("Car Data or Driver data not found" + carId + " " + driverId);
            throw e;
        }
    }
}
