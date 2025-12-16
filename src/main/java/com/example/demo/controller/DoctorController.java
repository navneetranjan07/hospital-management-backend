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

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    // -------- 📄 PAGINATED FETCH --------
    @GetMapping("/fetch")
    public Page<Doctor> fetchPaginated(
            @RequestParam int page,
            @RequestParam int size
    ) {
        logger.info("Fetching paginated doctors");
        return service.getDoctorsPaged(page, size);
    }

    // -------- CRUD --------
    @GetMapping("/fetchall")
    public List<Doctor> getAll() {
        logger.info("Fetching all doctors");
        return service.getAllDoctors();
    }

    @GetMapping("/find/{id}")
    public Doctor get(@PathVariable Long id) {
        logger.info("Fetching doctor with ID: {}", id);
        return service.getDoctor(id);
    }

    @PostMapping("/save")
    public Doctor add(@RequestBody Doctor d) {
        logger.info("Adding new doctor");
        return service.addDoctor(d);
    }

    @PutMapping("/update/{id}")
    public Doctor update(@PathVariable Long id, @RequestBody Doctor d) {
        logger.info("Updating doctor with ID: {}", id);
        return service.updateDoctor(id, d);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting doctor with ID: {}", id);
        service.deleteDoctor(id);
    }
}
