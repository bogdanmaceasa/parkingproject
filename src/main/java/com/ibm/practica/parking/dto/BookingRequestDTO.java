package com.ibm.practica.parking.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class BookingRequestDTO {

    @NotNull
    private String licensePlateNo;

    @NotNull
    private Float sum;

}
