package com.ibm.practica.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ParkingDTO {

    private Integer parkingNo;

    private ZonedDateTime startDate;

    private Integer parkingZone;

    private String licensePlateNo;

    private Float duration;
}
