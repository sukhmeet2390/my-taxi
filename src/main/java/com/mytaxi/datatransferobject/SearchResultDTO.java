package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.GeoCoordinate;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SearchResultDTO {
    private String username;
    private GeoCoordinate coordinate;
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
