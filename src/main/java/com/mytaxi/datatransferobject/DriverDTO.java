package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.GeoCoordinate;
import lombok.*;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DriverDTO {
    @JsonIgnore
    private Long id;
    @NotNull(message = "Username can not be null!")
    private String username;
    @NotNull(message = "Password can not be null!")
    private String password;
    private GeoCoordinate coordinate;
}
