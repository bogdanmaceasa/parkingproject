package com.ibm.practica.parking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "parking")
@Getter
@Setter
@ToString
public class ParkingEntry {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_spot_number")
    private Integer parkingNo;

    @Column(name = "start_time")
    private ZonedDateTime startDate;

    // parking fee multiplier by Zone
    @Column(name = "parking_zone")
    private Integer parkingZone;

    @Column(name = "license_plate")
    private String licensePlateNo;

    @Column(name = "duration")
    private Float duration;

}
