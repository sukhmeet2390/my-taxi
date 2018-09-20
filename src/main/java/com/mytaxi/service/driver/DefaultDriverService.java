package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.DriverCarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverNotFoundException;
import com.mytaxi.specification.DriverCarSpecificationBuilder;
import com.mytaxi.specification.DriverSpecificationBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>
 * Service to encapsulate the link between DAO and controller and to have business logic and search for some driver specific things.
 * <p/>
 */
@Service
@Slf4j
public class DefaultDriverService implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverCarRepository driverCarRepository;

    public DefaultDriverService(DriverRepository driverRepository, DriverCarRepository driverCarRepository) {
        this.driverRepository = driverRepository;
        this.driverCarRepository = driverCarRepository;
    }

    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws DriverNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws DriverNotFoundException {
        log.debug("Find driver {}", driverId);
        return findDriverChecked(driverId);
    }

    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return created driver
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
        log.debug("Create driver {}", driverDO);
        DriverDO driver;
        try {
            driver = driverRepository.save(driverDO);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            log.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }

    /**
     * Deletes an existing driver by id.
     *
     * @param driverId long
     * @return deleted driver
     * @throws DriverNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public DriverDO delete(Long driverId) throws DriverNotFoundException {
        log.debug("Delete driver {}", driverId);
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
        return driverRepository.save(driverDO);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId  long
     * @param longitude double
     * @param latitude  double
     * @return updated driver
     * @throws DriverNotFoundException Exception
     */
    @Override
    @Transactional
    public DriverDO updateLocation(long driverId, double longitude, double latitude) throws DriverNotFoundException {
        log.debug("Update location {} / {} / {} ", driverId, longitude, latitude);
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
        return driverDO;
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus OnlineStatus
     * @return list of drivers
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus) {
        log.debug("Find Driver {}", onlineStatus);
        return driverRepository.findByOnlineStatus(onlineStatus);
    }

    /**
     * @param query String
     * @return List of matching Drivers
     */
    @Override
    public List<DriverCarDO> searchSelected(String query) {
        log.debug("Search Selected Driver {}", query);
        DriverCarSpecificationBuilder builder = new DriverCarSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(query + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        final Specification<DriverCarDO> specification = builder.build();
        final Iterable<DriverCarDO> result = driverCarRepository.findAll(specification);
        return StreamSupport
                .stream(result.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * @param query String
     * @return list of matching drivers
     */
    @Override
    public List<DriverDO> searchUnselected(String query) {
        log.debug("Search Unselected Driver {}", query);
        DriverSpecificationBuilder builder = new DriverSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),");
        Matcher matcher = pattern.matcher(query + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        final Specification<DriverDO> specification = builder.build();
        final Iterable<DriverDO> result = driverRepository.findAll(specification);
        return StreamSupport
                .stream(result.spliterator(), false)
                .collect(Collectors.toList());
    }

    private DriverDO findDriverChecked(Long driverId) throws DriverNotFoundException {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Could not find driver with id: " + driverId));
    }
}
