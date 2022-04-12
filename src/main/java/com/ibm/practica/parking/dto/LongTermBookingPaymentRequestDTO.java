package com.ibm.practica.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LongTermBookingPaymentRequestDTO {

    @NotNull
    private String parkingCode;

    @NotNull
    private int sum;

}
