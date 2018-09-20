package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.DriverCarDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Database Access Object for driver_car table.
 * <p/>
 */
public interface DriverCarRepository extends CrudRepository<DriverCarDO, Long>,
        JpaSpecificationExecutor<DriverCarDO> {
    DriverCarDO findByDriverDO_IdAndCarDO_Id(Long driverId, Long carId);

    DriverCarDO findByCarDO_Id(Long carId);
}
