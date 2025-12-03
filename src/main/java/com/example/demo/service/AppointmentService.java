package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        LocalDate appointmentDay = a.getAppointmentTime().toLocalDate();

        Date sqlDate = Date.valueOf(appointmentDay);

        int dailyCount = repo.countDailyAppointments(doctorId, sqlDate);
        if (dailyCount >= 5) {
            throw new IllegalStateException(
                    "Doctor already has 10 appointments on " + appointmentDay
            );
        }

        Optional<Appointment> existingAppointment =
                repo.findByDoctorIdAndAppointmentTime(doctorId, a.getAppointmentTime());

        if (existingAppointment.isPresent()) {
            throw new IllegalStateException(
                    "Doctor is already booked at this time: " + a.getAppointmentTime()
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
}
