package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Appointment;
import com.example.demo.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repo;

    // ------------------------------------
    // 1️⃣ GET ALL / GET BY ID
    // ------------------------------------
    public List<Appointment> getAllAppointment() {
        return repo.findAll();
    }

    public Appointment getAppointment(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    // ------------------------------------
    // 2️⃣ ADD APPOINTMENT (with new conflict features)
    // ------------------------------------
    @Transactional
    public Appointment addAppointment(Appointment a) {

        Long doctorId = a.getDoctor().getId();
        Long patientId = a.getPatient().getId();
        LocalDateTime appointmentTime = a.getAppointmentTime();

        // Extract date only for daily limit
        LocalDate appointmentDay = appointmentTime.toLocalDate();
        Date sqlDate = Date.valueOf(appointmentDay);

        // 🔍 1. Check daily limit for the doctor
        int dailyCount = repo.countDailyAppointments(doctorId, sqlDate);
        if (dailyCount >= 5) {
            throw new IllegalStateException(
                    "Doctor already has 5 appointments on " + appointmentDay
            );
        }

        // 🔍 2. Check if doctor is busy at same time
        boolean doctorBusy = repo.doctorBusy(doctorId, appointmentTime);
        if (doctorBusy) {
            throw new IllegalStateException(
                    "Doctor is already booked at this time: " + appointmentTime
            );
        }

        // 🔍 3. Check if patient is busy at same time
        boolean patientBusy = repo.patientBusy(patientId, appointmentTime);
        if (patientBusy) {
            throw new IllegalStateException(
                    "Patient already has an appointment at this time: " + appointmentTime
            );
        }

        // All checks passed → save appointment
        return repo.save(a);
    }

    // ------------------------------------
    // 3️⃣ UPDATE APPOINTMENT
    // ------------------------------------
    public Appointment updateAppointment(Long id, Appointment a) {

        Appointment existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        existing.setPatient(a.getPatient());
        existing.setDoctor(a.getDoctor());
        existing.setAppointmentTime(a.getAppointmentTime());

        return repo.save(existing);
    }

    // ------------------------------------
    // 4️⃣ DELETE
    // ------------------------------------
    public void deleteAppointment(Long id) {
        repo.deleteById(id);
    }


    // ------------------------------------
    // 5️⃣ NEW FEATURE: FILTER BY DATE RANGE
    // ------------------------------------
    public List<Appointment> getAppointmentsByDateRange(LocalDate start, LocalDate end) {
        LocalDateTime startDT = start.atStartOfDay();
        LocalDateTime endDT = end.atTime(23, 59, 59);
        return repo.filterByDateRange(startDT, endDT);
    }

    // ------------------------------------
    // 6️⃣ NEW FEATURE: FILTER BY SPECIALIZATION
    // ------------------------------------
    public List<Appointment> getByDoctorSpecialization(String specialization) {
        return repo.filterByDoctorSpecialization(specialization);
    }

    // ------------------------------------
    // 7️⃣ NEW FEATURE: ANALYTICS → Appointments per day
    // ------------------------------------
    public List<Object[]> getAppointmentsPerDay() {
        return repo.appointmentsPerDay();
    }

    // ------------------------------------
    // 8️⃣ NEW FEATURE: ANALYTICS → Doctor workload
    // ------------------------------------
    public List<Object[]> getDoctorWorkload() {
        return repo.doctorWorkload();
    }
}
