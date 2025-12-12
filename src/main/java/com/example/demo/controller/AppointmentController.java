package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.entity.Appointment;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
//@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    // ---------------------------------------------------------
    // FETCH ALL APPOINTMENTS
    // ---------------------------------------------------------
    @GetMapping("/fetchall")
    public ResponseEntity<List<Appointment>> getAll() {
        logger.info("Received request to fetch all appointments");
        try {
            return ResponseEntity.ok(service.getAllAppointment());
        } catch (Exception e) {
            logger.error("Error fetching all appointments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ---------------------------------------------------------
    // GET APPOINTMENT BY ID
    // ---------------------------------------------------------
    @GetMapping("/find/{id}")
    public ResponseEntity<Appointment> get(@PathVariable Long id) {
        logger.info("Received request to fetch appointment with ID: {}", id);
        try {
            return ResponseEntity.ok(service.getAppointment(id));
        } catch (Exception e) {
            logger.error("Error fetching appointment with ID: {}", id, e);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // SAVE NEW APPOINTMENT
    // ---------------------------------------------------------
    @PostMapping("/save")
    public ResponseEntity<?> add(@RequestBody AppointmentRequest req) {
        logger.info("Received request to add new appointment");

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
            // Doctor busy or patient busy
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Booking failed: Doctor already booked at this time.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            logger.error("Error adding new appointment", e);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // UPDATE APPOINTMENT
    // ---------------------------------------------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id,
                                              @RequestBody AppointmentRequest req) {

        logger.info("Received request to update appointment with ID: {}", id);

        try {
            Appointment a = new Appointment();
            a.setAppointmentTime(LocalDateTime.parse(req.getAppointmentTime()));
            a.setPatient(patientRepo.findById(req.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found")));
            a.setDoctor(doctorRepo.findById(req.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found")));

            return ResponseEntity.ok(service.updateAppointment(id, a));

        } catch (Exception e) {
            logger.error("Error updating appointment with ID: {}", id, e);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // DELETE APPOINTMENT
    // ---------------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Received request to delete appointment with ID: {}", id);

        try {
            service.deleteAppointment(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            logger.error("Error deleting appointment with ID: {}", id, e);
            throw e;
        }
    }

    // ---------------------------------------------------------
    // FILTER: DATE RANGE
    // ---------------------------------------------------------
    @GetMapping("/filter/date-range")
    public ResponseEntity<List<Appointment>> getByDateRange(@RequestParam LocalDate start,
                                                            @RequestParam LocalDate end) {
        logger.info("Filtering appointments from {} to {}", start, end);
        return ResponseEntity.ok(service.getAppointmentsByDateRange(start, end));
    }

    // ---------------------------------------------------------
    // FILTER: DOCTOR SPECIALIZATION
    // ---------------------------------------------------------
    @GetMapping("/filter/specialization")
    public ResponseEntity<List<Appointment>> getBySpecialization(@RequestParam String specialization) {
        logger.info("Filtering appointments by doctor specialization: {}", specialization);
        return ResponseEntity.ok(service.getByDoctorSpecialization(specialization));
    }
}
