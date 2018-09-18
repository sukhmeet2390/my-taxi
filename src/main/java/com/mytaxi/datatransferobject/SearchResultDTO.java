package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.GeoCoordinate;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
