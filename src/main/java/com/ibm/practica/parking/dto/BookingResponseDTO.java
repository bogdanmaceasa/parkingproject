package com.ibm.practica.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingResponseDTO {

    private Integer parkingNo;

    private ZonedDateTime endDate;
}
