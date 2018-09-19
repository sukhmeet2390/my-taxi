package com.mytaxi;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.EngineType;

import static java.time.ZonedDateTime.now;

public class TestConstants {
    public static DriverDO t_driverDO1 = new DriverDO("driver01","pass_drive01");
    public static DriverDO t_driverDO2= new DriverDO("driver02","pass_drive02");
    public static CarDO t_carDO1 = new CarDO(1L, now(),"license01", 2, 3.0f, EngineType.GAS, "Hyndai", false, true, "Red", "i19");
    public static CarDO t_carDO2 = new CarDO(2L, now(),"license02", 4, 5.0f, EngineType.ELECTRIC, "Maruti", false, true, "Blue", "800");
    public static DriverCarDO t_driverCarDO1 = new DriverCarDO(t_driverDO1, t_carDO1);
    public static DriverCarDO t_driverCarDO2 = new DriverCarDO(t_driverDO2, t_carDO2);
}
