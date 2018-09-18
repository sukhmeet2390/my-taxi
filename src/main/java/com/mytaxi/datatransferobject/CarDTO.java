package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Builder
public class CarDTO {
    @JsonIgnore
    private Long id;
    private String licensePlate;
    private Integer seatCount;
    private Boolean convertible;
    private Float rating;
    private String engine;
    private String manufacturer;
    private Boolean deleted;
    private String color;
    private String model;
}
