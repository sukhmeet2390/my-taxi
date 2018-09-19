package com.mytaxi.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericErrorDTO {
    private Integer status;
    private String message;
}
