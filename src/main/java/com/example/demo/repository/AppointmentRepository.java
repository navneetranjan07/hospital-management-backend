package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Optional; // Import this

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    // 👇 ADDED: Method to check if an appointment exists for the doctor at the given time
    Optional<Appointment> findByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);

}