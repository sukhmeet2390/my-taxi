package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.GeoCoordinate;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class DriverDTO {
    @JsonIgnore
    private Long id;
    @NotNull(message = "Username can not be null!")
    private String username;
    @NotNull(message = "Password can not be null!")
    private String password;
    private GeoCoordinate coordinate;
}
