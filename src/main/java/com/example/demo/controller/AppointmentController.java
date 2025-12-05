package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

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

    @GetMapping("/fetchall")
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(service.getAllAppointment());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Appointment> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAppointment(id));
    }

    @PostMapping("/save")
    public ResponseEntity<?> add(@RequestBody AppointmentRequest req) {
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Booking failed: Doctor already booked at this time.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id,
                                              @RequestBody AppointmentRequest req) {

        Appointment a = new Appointment();
        a.setAppointmentTime(LocalDateTime.parse(req.getAppointmentTime()));
        a.setPatient(patientRepo.findById(req.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found")));
        a.setDoctor(doctorRepo.findById(req.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found")));

        return ResponseEntity.ok(service.updateAppointment(id, a));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
