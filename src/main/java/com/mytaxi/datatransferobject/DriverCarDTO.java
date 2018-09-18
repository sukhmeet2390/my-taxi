package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Builder
public class DriverCarDTO {
    @JsonIgnore
    private Long id;
    private Long driverId;
    private Long carId;
}
