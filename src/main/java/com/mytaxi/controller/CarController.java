package com.mytaxi.controller;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.exception.CarNotFoundException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * </p>
 * All operations with a car will be routed by this controller.
 * </p>
 */
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
        return CarMapper.makeCarDTOList(carService.getCars());
    }

    @GetMapping("/{carId}")
    public CarDTO findCar(@PathVariable long carId) throws CarNotFoundException {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO car) throws ConstraintsViolationException {
        return CarMapper.makeCarDTO(carService.create(CarMapper.makeCarDO(car)));
    }

    @PutMapping("/{carId}")
    public CarDTO updateCar(@PathVariable(value = "carId") Long id, @RequestBody CarDTO carDTO) throws ConstraintsViolationException, CarNotFoundException {
        carDTO.setId(id);
        return CarMapper.makeCarDTO(carService.update(id, CarMapper.makeCarDO(carDTO)));
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable(value = "carId") Long id) throws CarNotFoundException {
        carService.delete(id);
    }
}
