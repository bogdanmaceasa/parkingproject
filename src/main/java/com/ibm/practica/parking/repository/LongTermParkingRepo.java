package com.ibm.practica.parking.repository;

import com.ibm.practica.parking.entity.LongTermParkingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LongTermParkingRepo extends JpaRepository<LongTermParkingEntry, Long> {


    Optional<LongTermParkingEntry> findByPaymentCodeAndDuration(String paymentCode, int duration);

    Optional<LongTermParkingEntry> findByLicensePlateNo(String licensePlateNo);
}
