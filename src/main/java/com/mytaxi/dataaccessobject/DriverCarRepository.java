package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.DriverCarDO;
import org.springframework.data.repository.CrudRepository;

/**
 * Database Access Object for driver_car table.
 * <p/>
 */
public interface DriverCarRepository extends CrudRepository<DriverCarDO, Long> {
    DriverCarDO findByDriverIdAndCarId(Long driverId, Long carId);

    DriverCarDO findByCarId(Long carId);
}
