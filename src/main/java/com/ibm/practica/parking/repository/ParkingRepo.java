package com.ibm.practica.parking.repository;

import com.ibm.practica.parking.entity.ParkingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepo extends JpaRepository<ParkingEntry, Long> {

}
