package com.ibm.practica.parking.controller;


import com.ibm.practica.parking.dto.BookingRequestDTO;
import com.ibm.practica.parking.dto.BookingResponseDTO;
import com.ibm.practica.parking.dto.ParkingDTO;
import com.ibm.practica.parking.entity.ParkingEntry;
import com.ibm.practica.parking.service.ParkingService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @ApiOperation(value = "Retrieve all bookings.", response = ParkingDTO.class, responseContainer = "List")
    @GetMapping("/getall")
    public List<ParkingDTO> getAllBookings(){
        List<ParkingDTO> all = parkingService.getAll();
        return all;
    }

    @PutMapping("/book")
    public BookingResponseDTO bookParking(@RequestBody @Valid BookingRequestDTO dto){
        log.info("bookParking() started with request information: " + dto);
        return parkingService.bookParking(dto);
    }

    //
    @GetMapping("/getallactive/")
    public List<ParkingDTO> getAllActiveBookings(){
        return new ArrayList<>();
    }

    @GetMapping("/getall/{....}")
    public List<ParkingDTO> getBookingsWithinTimeframe(ZonedDateTime start, ZonedDateTime end){
        return new ArrayList<>();
    }

    @PutMapping("/booklongterm")
    public BookingResponseDTO bookLongTermParking(@RequestBody @Valid BookingRequestDTO dto){
        // input license plate no + number of days
        // output parking number + reservation code
        return new BookingResponseDTO();
    }

    @PutMapping("/paylongtermbooking")
    public BookingResponseDTO payLongTermParking(){
        // input will be reservation code
        // output will be payment confirmation ( receipt )
        return new BookingResponseDTO();
    }

    @DeleteMapping("/cancelbooking")
    public BookingResponseDTO cancelBooking(){
        // input will be license plate no
        // output will be ok + money back OR error -> too much time has passed
        return new BookingResponseDTO();
    }

}
