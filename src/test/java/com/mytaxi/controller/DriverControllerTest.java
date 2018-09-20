package com.mytaxi.controller;

import com.google.gson.Gson;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.*;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.service.driver_car.DriverCarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static com.mytaxi.TestConstants.*;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverService driverService;
    @MockBean
    private DriverCarService driverCarService;

    @Test
    public void getDriver() throws Exception {
        when(driverService.find(anyLong())).thenReturn(t_driverDO1);
        mockMvc.perform(get("/v1/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(t_driverDO1.getUsername())));
    }

    @Test
    public void getDriverFailure() throws Exception {
        when(driverService.find(anyLong())).thenThrow(DriverNotFoundException.class);
        mockMvc.perform(get("/v1/drivers/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createDriverSuccess() throws Exception {
        final DriverDO d = new DriverDO("newDriver", "newPass");
        final String json = new Gson().toJson(t_driverDTO1);
        when(driverService.create(any())).thenReturn(t_driverDO1);
        mockMvc.perform(post("/v1/drivers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is(t_driverDTO1.getUsername())));
    }

    @Test
    public void createDriverFailure() throws Exception {
        when(driverService.create(any())).thenThrow(ConstraintsViolationException.class);
        mockMvc.perform(post("/v1/drivers"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteDriverSuccess() throws Exception {
        t_driverDO1.setDeleted(true);
        when(driverService.delete(anyLong())).thenReturn(t_driverDO1);
        mockMvc.perform(delete("/v1/drivers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDriverFailure() throws Exception {
        when(driverService.delete(anyLong())).thenThrow(DriverNotFoundException.class);
        mockMvc.perform(delete("/v1/drivers" + t_driverDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateLocationSuccess() throws Exception {
        t_driverDO1.setCoordinate(new GeoCoordinate(10d, 20d));
        when(driverService.updateLocation(anyLong(), anyDouble(), anyDouble()))
                .thenReturn(t_driverDO1);
        mockMvc.perform(put("/v1/drivers/1?longitude=10.0&latitude=20.0"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateLocationFailure() throws Exception {
        t_driverDO1.setCoordinate(new GeoCoordinate(2d, 3d));
        when(driverService.updateLocation(anyLong(), anyDouble(), anyDouble()))
                .thenThrow(DriverNotFoundException.class);
        mockMvc.perform(put("/v1/drivers/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findDriversSuccess() throws Exception {
        t_driverDO1.setOnlineStatus(OnlineStatus.ONLINE);
        t_driverDO1.setOnlineStatus(OnlineStatus.OFFLINE);
        when(driverService.find(any(OnlineStatus.class)))
                .thenReturn(new ArrayList<>(singletonList(t_driverDO1)));
        mockMvc.perform(get("/v1/drivers/?onlineStatus=" + OnlineStatus.ONLINE.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void selectCarForDriver() throws Exception {
        t_driverCarDO2.getDriverDO().setOnlineStatus(OnlineStatus.ONLINE);
        when(driverCarService.selectCar(anyLong(), anyLong())).thenReturn(t_driverCarDO2);
        mockMvc.perform(put("/v1/drivers/2/select-car/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId", is(t_carDO2.getId().intValue())));
    }

    @Test
    public void selectCarForDriverFailureOnDriver() throws Exception {
        when(driverCarService.selectCar(anyLong(), anyLong())).thenThrow(DriverNotFoundException.class);
        mockMvc.perform(put("/v1/1/select-car/" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void selectCarForDriverFailureOnCar() throws Exception {
        when(driverCarService.selectCar(anyLong(), anyLong())).thenThrow(CarNotFoundException.class);
        mockMvc.perform(put("/v1/" + t_driverDO1.getId() + "/select-car" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void selectCarForDriverFailureOnUsedCar() throws Exception {
        when(driverCarService.selectCar(anyLong(), anyLong())).thenThrow(CarAlreadyInUseException.class);
        mockMvc.perform(put("/v1/" + t_driverDO1.getId() + "/select-car" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void selectCarForDriverFailureOnOfflineDriver() throws Exception {
        when(driverCarService.selectCar(anyLong(), anyLong())).thenThrow(DriverOfflineException.class);
        mockMvc.perform(put("/v1/" + t_driverDO1.getId() + "/select-car" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deselectCarForDriverFailureOnDriver() throws Exception {
        doThrow(DriverNotFoundException.class).when(driverCarService).deselectCar(anyLong(), anyLong());
        mockMvc.perform(put("/v1/" + t_driverDO1.getId() + "/deselect-car" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deselectCarForDriverFailureOnCar() throws Exception {
        doThrow(CarNotFoundException.class).when(driverCarService).deselectCar(anyLong(), anyLong());
        mockMvc.perform(put("/v1/" + t_driverDO1.getId() + "/deselect-car" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deselectCarForDriverFailureOnUsedCar() throws Exception {
        doThrow(CarAlreadyInUseException.class).when(driverCarService).deselectCar(anyLong(), anyLong());
        mockMvc.perform(put("/v1/" + t_driverDO1.getId() + "/deselect-car" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deselectCarForDriverFailureOnOfflineDriver() throws Exception {
        doThrow(DriverOfflineException.class).when(driverCarService).deselectCar(anyLong(), anyLong());
        mockMvc.perform(put("/v1/" + t_driverDO1.getId() + "/deselect-car" + t_carDO1.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void searchSelected() throws Exception{
        when(driverService.searchSelected(anyString())).thenReturn(new ArrayList<>(singletonList(t_driverCarDO1)));
        String query = "color:Red";
        mockMvc.perform(get("/v1/drivers/search/selected?q="+query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$..color", hasItem("Red")));
    }
    @Test
    public void searchSelectedFailure() throws Exception{
        when(driverService.searchSelected(anyString())).thenReturn(new ArrayList<>());
        String query = "color:Yellow";
        mockMvc.perform(get("/v1/drivers/search/selected?q="+query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void searchUnSelectedSuccess() throws Exception{
        when(driverService.searchUnselected(anyString())).thenReturn(new ArrayList<>(singletonList(t_driverDO1)));
        String query = "username:driver01";
        mockMvc.perform(get("/v1/drivers/search/unselected?q="+query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$..username", hasItem("driver01")));
    }

    @Test
    public void searchUnSelectedFailure() throws Exception{
        when(driverService.searchUnselected(anyString())).thenReturn(new ArrayList<>());
        String query = "color:Yellow";
        mockMvc.perform(get("/v1/drivers/search/unselected?q="+query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
