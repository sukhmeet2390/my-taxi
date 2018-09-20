package com.mytaxi.controller;

import com.mytaxi.controller.mapper.DriverCarMapper;
import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.controller.mapper.SearchResultMapper;
import com.mytaxi.datatransferobject.DriverCarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.datatransferobject.SearchResultDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.*;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.service.driver_car.DriverCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController {
    private final DriverService driverService;
    private final DriverCarService driverCarService;

    @Autowired
    public DriverController(final DriverService driverService, final DriverCarService driverCarService) {
        this.driverService = driverService;
        this.driverCarService = driverCarService;
    }

    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws DriverNotFoundException {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws DriverNotFoundException {
        driverService.delete(driverId);
    }

    @PutMapping("/{driverId}")
    public void updateLocation(@PathVariable long driverId, @RequestParam double longitude,
                               @RequestParam double latitude) throws DriverNotFoundException {
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus) {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }

    @PutMapping("/{driverId}/select-car/{carId}")
    public DriverCarDTO selectCarForDriver(@PathVariable(name = "driverId") Long driverId,
                                           @PathVariable(name = "carId") Long carId) throws DriverNotFoundException,
            CarNotFoundException, DriverOfflineException, CarAlreadyInUseException {
        return DriverCarMapper.makeDriverCarDTO(driverCarService.selectCar(driverId, carId));
    }

    @PutMapping("/{driverId}/deselect-car/{carId}")
    public void deselectCarForDriver(@PathVariable(name = "driverId") Long driverId,
                                     @PathVariable(name = "carId") Long carId) throws CarNotFoundException, DriverNotFoundException, DriverOfflineException, CarAlreadyInUseException {
        driverCarService.deselectCar(driverId, carId);
    }

    @GetMapping("/search/selected")
    public List<SearchResultDTO> searchSelectedDrivers(@RequestParam("q") String query) {
        return SearchResultMapper.makeSearchResultDTOListFromDriverCarDO(driverService.searchSelected(query));
    }

    @GetMapping("/search/unselected")
    public List<SearchResultDTO> searchUnSelectedDrivers(@RequestParam("q") String query) {
        return SearchResultMapper.makeSearchResultDTOListFromDriverDO(driverService.searchUnselected(query));
    }
}
