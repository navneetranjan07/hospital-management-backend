package com.example.demo.service;

import java.util.List;
import java.util.Optional; // Import this

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import this

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

    // 👇 MODIFIED: Added @Transactional and concurrency check
    @Transactional
    public Appointment addAppointment(Appointment a) {

        // 1. Check for existing appointment (Business logic check)
        Optional<Appointment> existingAppointment = repo.findByDoctorIdAndAppointmentTime(
                a.getDoctor().getId(),
                a.getAppointmentTime()
        );

        if (existingAppointment.isPresent()) {
            throw new IllegalStateException("Doctor is already booked at this time: "
                    + a.getAppointmentTime());
        }

        // 2. Save the appointment (Database unique constraint acts as final protection)
        return repo.save(a);
    }

    public Appointment updateAppointment(Long id, Appointment a) {
        Appointment existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        // Don’t change ID
        existing.setPatient(a.getPatient());
        existing.setDoctor(a.getDoctor());
        existing.setAppointmentTime(a.getAppointmentTime());

        return repo.save(existing);
    }

    public void deleteAppointment(Long id) {
        repo.deleteById(id);
    }
}