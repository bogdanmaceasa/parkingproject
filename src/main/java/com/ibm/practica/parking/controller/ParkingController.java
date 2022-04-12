package com.ibm.practica.parking.controller;


import com.ibm.practica.parking.dto.BookingRequestDTO;
import com.ibm.practica.parking.dto.BookingResponseDTO;
import com.ibm.practica.parking.dto.LongTermBookingPaymentRequestDTO;
import com.ibm.practica.parking.dto.LongTermBookingResponseDTO;
import com.ibm.practica.parking.dto.ParkingDTO;
import com.ibm.practica.parking.service.ParkingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @ApiOperation(value = "Retrieve all bookings.", response = ParkingDTO.class, responseContainer = "List")
    @GetMapping("/getAll")
    public List<ParkingDTO> getAllBookings(){
        List<ParkingDTO> all = parkingService.getAll();
        return all;
    }

    @PutMapping("/bookParking")
    public BookingResponseDTO bookParking(@RequestBody @Valid BookingRequestDTO dto){
        log.info("bookParking() started with request information: " + dto);
        return parkingService.bookParking(dto);
    }

    @ApiOperation(value = "Retrieve all active bookings.", response = ParkingDTO.class, responseContainer = "List")
    @GetMapping("/getAllActive")
    public ResponseEntity<List<ParkingDTO>> getAllActiveBookings(){
        return new ResponseEntity<>(parkingService.getAllActiveBookings(),HttpStatus.OK);
    }

    @GetMapping("/getAllStartedBetween")
    public List<ParkingDTO> getAllParkingBookingsStartedBetween( @ApiParam(name = "from", required = false) @RequestParam(value = "from", required = false) Long from,
        @ApiParam(name = "to", required = false) @RequestParam(value = "to", required = false) Long to){
        ZonedDateTime fromDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli(from), ZoneId.systemDefault());
        ZonedDateTime toDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli(to), ZoneId.systemDefault());
        log.info("getAllParkingBookingsStartedBetween() process started with values: startDate: " + fromDate + ", endDate: " + toDate);
        return parkingService.getAllParkingBookingsStartedBetween(fromDate,toDate);
    }

    @PutMapping("/booklongterm")
    public LongTermBookingResponseDTO bookLongTermParking(@RequestParam String licensePlate, @RequestParam int noOfDays){
        log.info("bookLongTermParking() process started with values: licensePlate: " + licensePlate + " and number of days: " + noOfDays);
        LongTermBookingResponseDTO responseDTO = parkingService.longTermBooking(licensePlate,noOfDays);
        return responseDTO;
    }

    @PutMapping("/paylongtermbooking")
    public ResponseEntity<String> payLongTermParking(@RequestBody @Valid LongTermBookingPaymentRequestDTO dto){
        boolean process = parkingService.payLongTermParking(dto);
        String message = process ? "payment successful" : "something went wrong, please try again";
        HttpStatus status = process ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(message, status);
    }

    @DeleteMapping("/cancelbooking/{licensePlate}")
    public ResponseEntity<String> cancelBooking(@PathVariable String licensePlate){

        boolean process = parkingService.cancelBooking(licensePlate);
        String message = process ? "booking was cancelled" : "something went wrong, booking cannot be cancelled";
        HttpStatus status = process ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(message, status);
    }

}
