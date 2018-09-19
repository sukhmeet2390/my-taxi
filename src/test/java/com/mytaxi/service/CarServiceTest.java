package com.mytaxi.service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.CarNotFoundException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.car.DefaultCarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mytaxi.TestConstants.t_carDO1;
import static java.util.Optional.ofNullable;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CarServiceTest {
    private CarService carService;
    private CarRepository carRepository;

    @Before
    public void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        carService = new DefaultCarService(carRepository);
    }

    @Test
    public void findCarsByIdSuccess() throws Exception {
        when(carRepository.findById(anyLong())).thenReturn(ofNullable(t_carDO1));
        final CarDO carDO = carService.find(1L);
        assertEquals(carDO, t_carDO1);
    }

    @Test(expected = CarNotFoundException.class)
    public void findCarByIdFailure() throws Exception {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        carService.find(1L);
    }

    @Test
    public void createCarSuccess() throws Exception {
        when(carRepository.save(t_carDO1)).thenReturn(t_carDO1);
        final CarDO carDO = carService.create(t_carDO1);
        assertEquals(carDO, t_carDO1);
    }

    @Test(expected = ConstraintsViolationException.class)
    public void createCarFailure() throws Exception {
        CarDO t_carDO1 = new CarDO("license", null, 3.0f, EngineType.GAS, "Hyndai", false, true, "Red", "i19");
        when(carRepository.save(any())).thenThrow(ConstraintViolationException.class);
        carService.create(t_carDO1);
    }

    @Test
    public void updateCarSuccess() throws Exception {
        Long id = 1L;
        CarDO carDO = new CarDO("new_license", 2, 3.0f, EngineType.GAS, "Hyndai", false,
                true, "Grey", "Zoom");
        when(carRepository.findById(anyLong())).thenReturn(ofNullable(t_carDO1));
        when(carRepository.save(any())).thenReturn(carDO);
        final CarDO update = carService.update(id, carDO);
        assertEquals(update.getLicensePlate(), "new_license");
        assertEquals(update.getModel(), "Zoom");
    }

    @Test(expected = ConstraintsViolationException.class)
    public void updateCarFailure() throws Exception {
        Long id = 1L;
        CarDO carDO = new CarDO("new_license", null, 3.0f, EngineType.GAS, "Hyndai", false,
                true, "Grey", "Zoom");
        when(carRepository.findById(anyLong())).thenReturn(ofNullable(t_carDO1));
        when(carRepository.save(any())).thenThrow(ConstraintViolationException.class);
        carService.update(id, carDO);
    }

    @Test
    public void getAllCarsSuccess(){
        when(carRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(t_carDO1)));
        final List<CarDO> cars = carService.getCars();
        assertEquals(cars.size(), 1);
        assertEquals(cars.get(0), t_carDO1);
    }

    @Test
    public void deleteCarSuccess() throws Exception{
        Long id = 1L;
        when(carRepository.findById(id)).thenReturn(ofNullable(t_carDO1));
        t_carDO1.setDeleted(true);
        when(carRepository.save(any())).thenReturn(t_carDO1);
        final CarDO delete = carService.delete(id);
        assertEquals(delete.getDeleted().booleanValue(), true);
        assertEquals(delete.getId(), id);
    }
}
