package com.ibm.practica.parking.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "long_term_parking")
@Getter
@Setter
@ToString
public class LongTermParkingEntry {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_spot_number")
    private Integer parkingNo;

    @Column(name = "license_plate")
    private String licensePlateNo;

    @Column(name = "duration")
    private int duration;

    @Column(name = "is_paid")
    private boolean isPaid;


    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

}
