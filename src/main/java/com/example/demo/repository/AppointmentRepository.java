package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // ------------------------------------
    // 1️⃣ BASIC FINDER (already present)
    // ------------------------------------
    Optional<Appointment> findByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);


    // ------------------------------------
    // 2️⃣ CONFLICT DETECTION
    // ------------------------------------
    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.doctor.id = :doctorId " +
            "AND a.appointmentTime = :appointmentTime")
    boolean doctorBusy(@Param("doctorId") Long doctorId,
                       @Param("appointmentTime") LocalDateTime appointmentTime);


    @Query("SELECT COUNT(a) > 0 FROM Appointment a " +
            "WHERE a.patient.id = :patientId " +
            "AND a.appointmentTime = :appointmentTime")
    boolean patientBusy(@Param("patientId") Long patientId,
                        @Param("appointmentTime") LocalDateTime appointmentTime);


    // ------------------------------------
    // 3️⃣ DATE RANGE FILTERING
    // ------------------------------------
    @Query("SELECT a FROM Appointment a " +
            "WHERE a.appointmentTime BETWEEN :start AND :end")
    List<Appointment> filterByDateRange(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


    // ------------------------------------
    // 4️⃣ FILTER BY DOCTOR SPECIALIZATION (supports partial match)
    // ------------------------------------
    @Query("SELECT a FROM Appointment a " +
            "WHERE LOWER(a.doctor.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))")
    List<Appointment> filterByDoctorSpecialization(
            @Param("specialization") String specialization
    );


    // ------------------------------------
    // 5️⃣ ANALYTICS → Appointments per day
    // ------------------------------------
    @Query(
            value = "SELECT TRUNC(appointment_time) AS day, COUNT(*) " +
                    "FROM appointment " +
                    "GROUP BY TRUNC(appointment_time) " +
                    "ORDER BY TRUNC(appointment_time)",
            nativeQuery = true
    )
    List<Object[]> appointmentsPerDay();


    // ------------------------------------
    // 6️⃣ ANALYTICS → Doctor workload (FIXED VERSION)
    // ------------------------------------
    @Query(
            value = "SELECT d.name, COUNT(a.id) " +
                    "FROM doctor d " +
                    "LEFT JOIN appointment a ON a.doctor_id = d.id " +
                    "GROUP BY d.id, d.name",
            nativeQuery = true
    )
    List<Object[]> doctorWorkload();


    // ------------------------------------
    // 7️⃣ DAILY COUNT (already present)
    // ------------------------------------
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
