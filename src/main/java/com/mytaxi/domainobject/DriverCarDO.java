package com.mytaxi.domainobject;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "driver_car",
        uniqueConstraints = @UniqueConstraint(name = "uc_car_id_driver_id", columnNames = {"car_id", "driver_id"})
)
@Data
@NoArgsConstructor
public class DriverCarDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUpdated = ZonedDateTime.now();

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "car_id")
    private Long carId;
}
