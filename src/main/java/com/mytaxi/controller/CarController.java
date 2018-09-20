package com.mytaxi.controller;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<CarDTO> getAllCars() {
        return CarMapper.makeDriverDTOList(carService.getCars());
    }

    @GetMapping("/{carId}")
    public CarDTO findCar(@PathVariable long carId) throws EntityNotFoundException {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO car) throws ConstraintsViolationException {
        CarDO carDO = CarMapper.makeCarDO(car);
        return CarMapper.makeCarDTO(carService.create(carDO));
    }

    @PutMapping("/{carId}")
    public CarDTO updateCar(@PathVariable(value = "carId") Long id, @RequestBody CarDTO carDTO) throws ConstraintsViolationException, EntityNotFoundException {
        carDTO.setId(id);
        return CarMapper.makeCarDTO(carService.update(id, CarMapper.makeCarDO(carDTO)));
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable(value = "carId") Long id) throws EntityNotFoundException {
        carService.delete(id);
    }
}
