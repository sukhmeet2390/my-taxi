package com.mytaxi.domainobject;

import com.mytaxi.domainvalue.EngineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(
        name = "car",
        uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"license_plate"})
)
@AllArgsConstructor
@NoArgsConstructor

public class CarDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(name = "license_plate", nullable = false)
    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    @Column(name = "seat_count", nullable = false)
    @NotNull(message = "Seat Count can not be null!")
    private Integer seatCount;

    @Column(nullable = false)
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type", nullable = false)
    private EngineType engineType;

    @Column(nullable = false)
    @NotNull(message = "Manufacturer can not be null!")
    private String manufacturer;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column
    private boolean convertible;

    @Column(nullable = false)
    private String color;

    @Column
    private String model;

    public CarDO(@NotNull(message = "License plate can not be null!") String licensePlate,
                 @NotNull(message = "Seat Count can not be null!") Integer seatCount,
                 Float rating, EngineType engineType,
                 @NotNull(message = "Manufacturer can not be null!") String manufacturer,
                 Boolean deleted, boolean convertible, String color,
                 String model) {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.deleted = deleted;
        this.convertible = convertible;
        this.color = color;
        this.model = model;
    }
}
