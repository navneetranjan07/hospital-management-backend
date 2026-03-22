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

    public List<Appointment> getAllAppointment() {
        return repo.findAll();
    }

    public Appointment getAppointment(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    @Transactional
    public Appointment addAppointment(Appointment a) {

        Long doctorId = a.getDoctor().getId();
        Long patientId = a.getPatient().getId();
        LocalDateTime appointmentTime = a.getAppointmentTime();

        LocalDate appointmentDay = appointmentTime.toLocalDate();
        Date sqlDate = Date.valueOf(appointmentDay);

        int dailyCount = repo.countDailyAppointments(doctorId, sqlDate);
        if (dailyCount >= 5) {
            throw new IllegalStateException(
                    "Doctor already has 5 appointments on " + appointmentDay
            );
        }

        boolean doctorBusy = repo.doctorBusy(doctorId, appointmentTime);
        if (doctorBusy) {
            throw new IllegalStateException(
                    "Doctor is already booked at this time: " + appointmentTime
            );
        }

        boolean patientBusy = repo.patientBusy(patientId, appointmentTime);
        if (patientBusy) {
            throw new IllegalStateException(
                    "Patient already has an appointment at this time: " + appointmentTime
            );
        }

        return repo.save(a);
    }

    public Appointment updateAppointment(Long id, Appointment a) {

        Appointment existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        existing.setPatient(a.getPatient());
        existing.setDoctor(a.getDoctor());
        existing.setAppointmentTime(a.getAppointmentTime());

        return repo.save(existing);
    }

    public void deleteAppointment(Long id) {
        repo.deleteById(id);
    }


    public List<Appointment> getAppointmentsByDateRange(LocalDate start, LocalDate end) {
        LocalDateTime startDT = start.atStartOfDay();
        LocalDateTime endDT = end.atTime(23, 59, 59);
        return repo.filterByDateRange(startDT, endDT);
    }

    public List<Appointment> getByDoctorSpecialization(String specialization) {
        return repo.filterByDoctorSpecialization(specialization);
    }

    public List<Object[]> getAppointmentsPerDay() {
        return repo.appointmentsPerDay();
    }

    public List<Object[]> getDoctorWorkload() {
        return repo.doctorWorkload();
    }
}
