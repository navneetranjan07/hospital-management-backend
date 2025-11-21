package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException; // Import this for DB error handling

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.entity.Appointment;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(service.getAllAppointment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAppointment(id));
    }

    // 👇 MODIFIED: Added try-catch blocks for robust concurrency/data integrity handling
    @PostMapping
    public ResponseEntity<?> add(@RequestBody AppointmentRequest req) {

        // This is a common practice: use `ResponseEntity<?>` to return different types (Appointment or error String)
        try {
            Appointment a = new Appointment();
            a.setId(req.getId());
            a.setAppointmentTime(LocalDateTime.parse(req.getAppointmentTime()));
            a.setPatient(patientRepo.findById(req.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found")));
            a.setDoctor(doctorRepo.findById(req.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found")));

            Appointment saved = service.addAppointment(a);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IllegalStateException e) {
            // Catch business logic conflict (from Service layer check)
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            // Catch database unique constraint violation (final security net)
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict
                    .body("Booking failed: The doctor is already booked at this exact time.");
        } catch (RuntimeException e) {
            // Catch Patient/Doctor not found or other bad request issues
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) // 400 Bad Request
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id, @RequestBody AppointmentRequest req) {
        Appointment a = new Appointment();
        a.setAppointmentTime(LocalDateTime.parse(req.getAppointmentTime()));
        a.setPatient(patientRepo.findById(req.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found")));
        a.setDoctor(doctorRepo.findById(req.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found")));

        Appointment updated = service.updateAppointment(id, a);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}