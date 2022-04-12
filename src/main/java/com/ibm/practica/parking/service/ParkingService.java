package com.ibm.practica.parking.service;

import com.ibm.practica.parking.dto.BookingRequestDTO;
import com.ibm.practica.parking.dto.BookingResponseDTO;
import com.ibm.practica.parking.dto.LongTermBookingPaymentRequestDTO;
import com.ibm.practica.parking.dto.LongTermBookingResponseDTO;
import com.ibm.practica.parking.dto.ParkingDTO;
import com.ibm.practica.parking.entity.LongTermParkingEntry;
import com.ibm.practica.parking.entity.ParkingEntry;
import com.ibm.practica.parking.repository.LongTermParkingRepo;
import com.ibm.practica.parking.repository.ParkingRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ParkingService {

    @Autowired
    ParkingRepo parkingRepo;

    @Autowired LongTermParkingRepo longTermParkingRepo;

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

    public List<ParkingDTO> getAllActiveBookings(){
        return parkingRepo.findAll().stream()
            .filter(parkingEntry -> ZonedDateTime.now().isBefore(parkingEntry.getStartDate().plusSeconds((long) (parkingEntry.getDuration()*3600))))
            .map(parkingEntry -> new ParkingDTO(parkingEntry.getParkingNo(), parkingEntry.getStartDate(),
                parkingEntry.getParkingZone(), parkingEntry.getLicensePlateNo(), parkingEntry.getDuration()))
            .collect(Collectors.toList());
    }

    public List<ParkingDTO> getAllParkingBookingsStartedBetween(ZonedDateTime start, ZonedDateTime end){
        return parkingRepo.findAllParkingEntriesBetween(start, end).stream()
            .map(parkingEntry -> new ParkingDTO(parkingEntry.getParkingNo(), parkingEntry.getStartDate(),
                parkingEntry.getParkingZone(), parkingEntry.getLicensePlateNo(), parkingEntry.getDuration()))
            .collect(Collectors.toList());
    }

    public LongTermBookingResponseDTO longTermBooking(String licensePlate, int day){

        LongTermParkingEntry entity = new LongTermParkingEntry();
        entity.setLicensePlateNo(licensePlate);
        entity.setDuration(day);
        entity.setParkingNo(new Random().nextInt(999));
        entity.setPaid(false);
        entity.setPaymentCode(UUID.randomUUID().toString());
        entity.setStartDate(ZonedDateTime.now());

        longTermParkingRepo.save(entity);

        return new LongTermBookingResponseDTO(entity.getParkingNo(),ZonedDateTime.now().plusDays(entity.getDuration()),entity.getPaymentCode(),2 * entity.getDuration());
    }

    public boolean payLongTermParking(LongTermBookingPaymentRequestDTO dto){

        Optional<LongTermParkingEntry> result = longTermParkingRepo.findByPaymentCodeAndDuration(dto.getParkingCode(),dto.getSum()/2);

        if (result.isPresent()) {
            ParkingEntry entry = new ParkingEntry();
            entry.setLicensePlateNo(result.get().getLicensePlateNo());
            entry.setParkingZone(2);
            entry.setDuration((float)result.get().getDuration());
            entry.setStartDate(result.get().getStartDate());
            entry.setParkingNo(result.get().getParkingNo());

            result.get().setPaid(true);
            longTermParkingRepo.save(result.get());

            parkingRepo.save(entry);
            return true;
        }

        return false;
    }

    public boolean cancelBooking(String licensePlate){

        Optional<LongTermParkingEntry> result = longTermParkingRepo.findByLicensePlateNo(licensePlate);

        if (result.isPresent() && !result.get().isPaid() &&
            ZonedDateTime.now().plusMinutes(5).isAfter(result.get().getStartDate())){
            longTermParkingRepo.delete(result.get());
            return true;
        }
        return false;
    }
}
