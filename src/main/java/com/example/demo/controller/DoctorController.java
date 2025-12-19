package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService service;

    private static final Logger logger =
            LoggerFactory.getLogger(DoctorController.class);

    // ------------------------------------------------
    // PAGINATED FETCH (ALL DOCTORS)
    // ------------------------------------------------
    @GetMapping("/fetch")
    public Page<Doctor> fetchPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        logger.info("Fetching doctors | page={}, size={}", page, size);
        return service.getDoctorsPaged(page, size);
    }

    // ------------------------------------------------
    // PAGINATED BY SPECIALIZATION (DEPARTMENT)
    // ------------------------------------------------
    @GetMapping("/specialization/{name}")
    public Page<Doctor> getBySpecialization(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        logger.info(
                "Fetching doctors by specialization={} | page={}, size={}",
                name, page, size
        );
        return service.getDoctorsBySpecialty(name, page, size);
    }

    // ------------------------------------------------
    // SEARCH (NAME + SPECIALIZATION)
    // ------------------------------------------------
    @GetMapping("/search")
    public Page<Doctor> searchDoctors(
            @RequestParam String specialization,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        logger.info(
                "Searching doctors | specialization={}, keyword={}, page={}, size={}",
                specialization, keyword, page, size
        );
        return service.searchDoctors(specialization, keyword, page, size);
    }

    // ------------------------------------------------
    // FETCH ALL (NO PAGINATION – ADMIN USE)
    // ------------------------------------------------
    @GetMapping("/fetchall")
    public List<Doctor> getAll() {
        logger.info("Fetching all doctors (no pagination)");
        return service.getAllDoctors();
    }

    // ------------------------------------------------
    // FETCH SINGLE DOCTOR
    // ------------------------------------------------
    @GetMapping("/find/{id}")
    public Doctor get(@PathVariable Long id) {
        logger.info("Fetching doctor with ID={}", id);
        return service.getDoctor(id);
    }

    // ------------------------------------------------
    // CREATE
    // ------------------------------------------------
    @PostMapping("/save")
    public Doctor add(@RequestBody Doctor d) {
        logger.info("Adding new doctor: {}", d.getName());
        return service.addDoctor(d);
    }

    // ------------------------------------------------
    // UPDATE
    // ------------------------------------------------
    @PutMapping("/update/{id}")
    public Doctor update(
            @PathVariable Long id,
            @RequestBody Doctor d
    ) {
        logger.info("Updating doctor with ID={}", id);
        return service.updateDoctor(id, d);
    }

    // ------------------------------------------------
    // DELETE
    // ------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting doctor with ID={}", id);
        service.deleteDoctor(id);
    }
}
