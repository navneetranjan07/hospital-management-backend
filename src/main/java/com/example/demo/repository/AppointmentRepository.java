package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);

    @Query(
            value = "SELECT COUNT(*) FROM appointment " +
                    "WHERE doctor_id = :doctorId " +
                    "AND TRUNC(appointment_time) = TRUNC(:day)",
            nativeQuery = true
    )
    int countDailyAppointments(
            @Param("doctorId") Long doctorId,
            @Param("day") Date day
    );
}
