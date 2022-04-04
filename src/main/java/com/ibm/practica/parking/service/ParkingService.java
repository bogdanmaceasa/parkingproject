package com.ibm.practica.parking.service;

import com.ibm.practica.parking.dto.BookingRequestDTO;
import com.ibm.practica.parking.dto.BookingResponseDTO;
import com.ibm.practica.parking.dto.ParkingDTO;
import com.ibm.practica.parking.entity.ParkingEntry;
import com.ibm.practica.parking.repository.ParkingRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ParkingService {

    @Autowired
    ParkingRepo parkingRepo;

    public List<ParkingDTO> getAll(){
        List<ParkingDTO> list = parkingRepo.findAll()
            .stream()
            .map(parkingEntry -> new ParkingDTO(parkingEntry.getParkingNo(), parkingEntry.getStartDate(),
                parkingEntry.getParkingZone(), parkingEntry.getLicensePlateNo(), parkingEntry.getDuration()))
            .collect(Collectors.toList());

        return list;
    }

    public BookingResponseDTO bookParking(BookingRequestDTO requestDTO){

        ParkingEntry entry = new ParkingEntry();
        entry.setLicensePlateNo(requestDTO.getLicensePlateNo());
        entry.setParkingZone(2);
        entry.setDuration(requestDTO.getSum()/entry.getParkingZone());
        entry.setStartDate(ZonedDateTime.now());
        entry.setParkingNo(new Random().nextInt(999));

        parkingRepo.save(entry);

        return new BookingResponseDTO(entry.getParkingNo(),
            entry.getStartDate().plusSeconds((long) (entry.getDuration()*3600)));
    }
}
