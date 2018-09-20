package com.mytaxi.service;

import com.mytaxi.dataaccessobject.DriverCarRepository;
import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.CarNotFoundException;
import com.mytaxi.exception.DriverNotFoundException;
import com.mytaxi.exception.DriverOfflineException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.service.driver_car.DefaultDriveCarService;
import com.mytaxi.service.driver_car.DriverCarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.mytaxi.TestConstants.*;
import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DriverCarServiceTest {
    private DriverCarService driverCarService;
    @MockBean
    private CarService carService;
    @MockBean
    private DriverService driverService;
    @MockBean
    private DriverCarRepository driverCarRepository;

    @Before
    public void setup() {
        driverCarService = new DefaultDriveCarService(driverCarRepository, carService, driverService);
    }

    @Test(expected = CarAlreadyInUseException.class)
    public void selectCarAlreadySelectedFailure() throws Exception {
        when(driverCarRepository.findByCarDO_Id(any())).thenReturn(t_driverCarDO1);
        driverCarService.selectCar(1L, 1L);
    }

    @Test(expected = DriverOfflineException.class)
    public void selectCarDriverOffline() throws Exception {
        when(driverCarRepository.findByCarDO_Id(any())).thenReturn(null);
        when(driverService.find(anyLong())).thenReturn(t_driverDO1);
        when(carService.find(anyLong())).thenReturn(t_carDO1);
        driverCarService.selectCar(1L, 1L);
    }

    @Test
    public void selectCarDriverOnline() throws Exception {
        when(driverCarRepository.findByCarDO_Id(any())).thenReturn(null);
        when(driverCarRepository.save(any())).thenReturn(t_driverCarDO1);
        when(driverService.find(anyLong())).thenReturn(t_driverDO1);
        when(carService.find(anyLong())).thenReturn(t_carDO1);
        t_driverDO1.setOnlineStatus(OnlineStatus.ONLINE);
        final DriverCarDO selected = driverCarService.selectCar(1L, 1L);
        assertEquals(selected.getCarDO(), t_carDO1);
        assertEquals(selected.getDriverDO(), t_driverDO1);
    }

    @Test(expected = DriverNotFoundException.class)
    public void selectCarOnDriverNotFoundFailure() throws Exception {
        when(driverService.find(anyLong())).thenThrow(DriverNotFoundException.class);
        when(driverCarRepository.findByCarDO_Id(any())).thenReturn(null);
        when(carService.find(anyLong())).thenReturn(t_carDO1);
        driverCarService.selectCar(1L, 1L);
    }

    @Test(expected = CarNotFoundException.class)
    public void selectCarOnCarNotFoundFailure() throws Exception {
        when(driverService.find(anyLong())).thenReturn(null);
        when(driverCarRepository.findByCarDO_Id(any())).thenReturn(null);
        when(carService.find(anyLong())).thenThrow(CarNotFoundException.class);
        driverCarService.selectCar(1L, 1L);
    }

    @Test
    public void deselectCarSuccess() throws Exception{
        when(driverCarRepository.findByCarDO_Id(anyLong())).thenReturn(t_driverCarDO1);
        when(driverService.find(anyLong())).thenReturn(t_driverDO1);
        when(carService.find(anyLong())).thenReturn(t_carDO1);
        t_driverDO1.setOnlineStatus(OnlineStatus.ONLINE);
        t_driverDO1.setId(1L);
        when(driverCarRepository.findByDriverDO_IdAndCarDO_Id(anyLong(),anyLong()))
                .thenReturn(t_driverCarDO1);
        driverCarService.deselectCar(1L,1L);
    }

    @Test(expected = DriverOfflineException.class)
    public void deselectCarOfflineException() throws Exception{
        when(driverCarRepository.findByCarDO_Id(anyLong())).thenReturn(t_driverCarDO1);
        when(driverService.find(anyLong())).thenReturn(t_driverDO1);
        when(carService.find(anyLong())).thenReturn(t_carDO1);
        t_driverDO1.setOnlineStatus(OnlineStatus.OFFLINE);
        t_driverDO1.setId(1L);
        driverCarService.deselectCar(1L,1L);
    }

    @Test(expected = CarAlreadyInUseException.class)
    public void deselectCarInUseException() throws Exception{
        t_driverDO1.setId(1L);
        t_driverDO2.setId(2L);
        when(driverCarRepository.findByCarDO_Id(anyLong())).thenReturn(t_driverCarDO2);
        when(driverService.find(anyLong())).thenReturn(t_driverDO1);
        when(carService.find(anyLong())).thenReturn(t_carDO1);
        t_driverDO1.setId(1L);
        driverCarService.deselectCar(1L,1L);
    }
}
