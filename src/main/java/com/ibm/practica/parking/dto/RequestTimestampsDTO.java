package com.ibm.practica.parking.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestTimestampsDTO {

    @NotNull(message = "The start date should not be null and have the following format: yyyy-MM-ddThh:mm:ssZ")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime fromTS;

    @NotNull(message = "The end date should not be null and have the following format: yyyy-MM-ddThh:mm:ssZ")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime toTS;

}
