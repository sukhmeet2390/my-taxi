package com.mytaxi.service.driver;

import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverNotFoundException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;

public interface DriverService {

    DriverDO find(Long driverId) throws DriverNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    DriverDO delete(Long driverId) throws DriverNotFoundException;

    DriverDO updateLocation(long driverId, double longitude, double latitude) throws DriverNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    List<DriverCarDO> searchSelected(String query);

    List<DriverDO> searchUnselected(String query);
}
