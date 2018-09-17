package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long> {
}
