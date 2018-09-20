package com.mytaxi.controller;

import com.google.gson.Gson;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.CarNotFoundException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.service.car.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mytaxi.TestConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CarService carService;

    @Test
    public void getAllCars() throws Exception {
        final ArrayList<CarDO> mockValues = new ArrayList<>(Arrays.asList(t_carDO1, t_carDO2));
        when(carService.getCars()).thenReturn(mockValues);
        final List<CarDTO> mockResponse = CarMapper.makeCarDTOList(mockValues);
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/cars/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn().getResponse();
    }

    @Test
    public void findCar() throws Exception {
        Long id = 1L;
        when(carService.find(id)).thenReturn(t_carDO1);
        mockMvc.perform(get("/v1/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", is(t_carDO1.getLicensePlate())));
    }

    @Test
    public void findCarFailure() throws Exception {
        Long id = 1L;
        when(carService.find(id)).thenThrow(CarNotFoundException.class);
        mockMvc.perform(get("/v1/cars/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createCar() throws Exception {
        when(carService.create(any())).thenReturn(t_carDO1);
        t_carDTO1.setId(100L);
        final String json = new Gson().toJson(t_carDTO1);
        mockMvc.perform(post("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.licensePlate", is(t_carDO1.getLicensePlate())))
                .andExpect(jsonPath("$.manufacturer", is(t_carDO1.getManufacturer())));

    }

    @Test
    public void createCarFailure() throws Exception {
        when(carService.create(any())).thenThrow(ConstraintsViolationException.class);
        t_carDTO1.setSeatCount(null);
        final String json = new Gson().toJson(t_carDTO1);
        mockMvc.perform(post("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateCar() throws Exception {
        when(carService.update(anyLong(), any())).thenReturn(t_carDO1);
        final String json = new Gson().toJson(t_carDTO1);
        mockMvc.perform(put("/v1/cars/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.licensePlate", is(t_carDO1.getLicensePlate())))
                .andExpect(jsonPath("$.manufacturer", is(t_carDO1.getManufacturer())));

    }

    @Test
    public void updateCarFailure() throws Exception {
        when(carService.update(anyLong(), any())).thenThrow(CarNotFoundException.class);
        when(carService.find(anyLong())).thenThrow(CarNotFoundException.class);
        final String json = new Gson().toJson(t_carDTO1);
        mockMvc.perform(put("/v1/cars/100")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void deleteCar() throws Exception {
        t_carDO1.setDeleted(true);
        when(carService.delete(anyLong())).thenReturn(t_carDO1);
        mockMvc.perform(delete("/v1/cars/1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteCarFailure() throws Exception {
        t_carDO1.setDeleted(true);
        when(carService.delete(anyLong())).thenThrow(CarNotFoundException.class);
        mockMvc.perform(delete("/v1/cars/100"))
                .andExpect(status().is4xxClientError());
    }


}
