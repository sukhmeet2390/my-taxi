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

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverDO driverDO;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarDO carDO;
}
