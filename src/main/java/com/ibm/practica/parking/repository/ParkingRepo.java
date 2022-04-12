package com.ibm.practica.parking.repository;

import com.ibm.practica.parking.entity.ParkingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ParkingRepo extends JpaRepository<ParkingEntry, Long> {

    @Query(value = "SELECT * FROM parking WHERE start_time > ?1 AND start_time < ?2 ", nativeQuery = true)
    List<ParkingEntry> findAllParkingEntriesBetween(ZonedDateTime start, ZonedDateTime end);

}
