package com.mytaxi.service;

import com.mytaxi.dataaccessobject.DriverCarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.DriverCarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverNotFoundException;
import com.mytaxi.service.driver.DefaultDriverService;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.specification.DriverCarSpecification;
import com.mytaxi.util.SearchCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mytaxi.TestConstants.*;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DriverServiceTest {
    private DriverRepository driverRepository;
    private DriverCarRepository driverCarRepository;
    private DriverService driverService;

    @Before
    public void setup() {
        driverRepository = Mockito.mock(DriverRepository.class);
        driverCarRepository = Mockito.mock(DriverCarRepository.class);
        driverService = new DefaultDriverService(driverRepository, driverCarRepository);
    }

    @Test
    public void findByIdSuccess() throws Exception {
        Long id = 1L;
        when(driverRepository.findById(id)).thenReturn(ofNullable(t_driverDO1));
        assertEquals(t_driverDO1, driverService.find(id));
    }

    @Test(expected = DriverNotFoundException.class)
    public void findByIdFailure() throws Exception {
        Long id = 1L;
        when(driverRepository.findById(id)).thenReturn(Optional.empty());
        driverService.find(id);
    }

    @Test
    public void createSuccess() throws Exception {
        when(driverRepository.save(t_driverDO1)).thenReturn(t_driverDO1);
        assertEquals(driverService.create(t_driverDO1), t_driverDO1);
    }

    @Test(expected = ConstraintsViolationException.class)
    public void createFailure() throws Exception {
        when(driverRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        driverService.create(t_driverDO1);
    }

    @Test
    public void deleteSuccess() throws Exception {
        Long id = 1L;
        when(driverRepository.findById(id)).thenReturn(ofNullable(t_driverDO1));
        t_driverDO1.setDeleted(true);
        when(driverRepository.save(any())).thenReturn(t_driverDO1);
        final DriverDO delete = driverService.delete(id);
        assertEquals(delete.getDeleted(), true);
        assertEquals(delete.getId(), t_driverDO1.getId());
    }

    @Test
    public void updateLocationSuccess() throws Exception {
        Long id = 1L;
        when(driverRepository.findById(id)).thenReturn(ofNullable(t_driverDO1));
        final DriverDO driverDO = driverService.updateLocation(id, 12.1, 3.3);
        t_driverDO1.setCoordinate(new GeoCoordinate(12.1, 3.3));
        assertEquals(driverDO.getCoordinate(), t_driverDO1.getCoordinate());
    }

    @Test(expected = DriverNotFoundException.class)
    public void updateLocationFailure() throws Exception {
        Long id = 1L;
        when(driverRepository.findById(id)).thenReturn(Optional.empty());
        driverService.updateLocation(id, 12.1, 3.2);
    }

    @Test
    public void findSuccess() {
        when(driverRepository.findByOnlineStatus(OnlineStatus.ONLINE)).thenReturn(new ArrayList<>(Arrays.asList(t_driverDO1)));
        final List<DriverDO> driverDOS = driverService.find(OnlineStatus.ONLINE);
        assertEquals(driverDOS.size(), 1);
        assertEquals(driverDOS.get(0), t_driverDO1);
    }

    @Test
    public void testSelectedByQuery() {
        when(driverCarRepository.findAll(any())).thenReturn(new ArrayList<>(singletonList(t_driverCarDO1)));
        final String query = "licensePlate:license";
        final List<DriverCarDO> result = driverService.searchSelected(query);
        assertThat(t_driverCarDO1, isIn(result));
        assertThat(t_driverCarDO2, not(isIn(result)));
        assertTrue(result.stream().allMatch(item -> item.getCarDO().getLicensePlate().contains("license")));
    }

    @Test
    public void testUnSelectedByQuery(){
        final Specification<DriverDO> any = any();
        final String query = "name:driver01";
        when(driverRepository.findAll(any)).thenReturn(new ArrayList<>(singletonList(t_driverDO1)));
        final List<DriverDO> result = driverService.searchUnselected(query);
        assertThat(t_driverDO1, isIn(result));
        assertThat(t_driverDO2, not(isIn(result)));
        assertTrue(result.stream().allMatch(item->item.getUsername().contains("driver01")));
    }
}
