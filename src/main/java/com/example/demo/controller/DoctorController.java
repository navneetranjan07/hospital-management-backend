package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;


@RestController
@RequestMapping("/doctors")
//@CrossOrigin
public class DoctorController {
    @Autowired
    DoctorService service;

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @GetMapping("/fetchall")
    public List<Doctor> getAll() {
            logger.info("Received request to fetch all doctors");
        try {
            logger.info("Fetching all doctors");
            return service.getAllDoctors();
        } catch (Exception e) {
            logger.error("Error fetching all doctors", e);
            throw e;
        }
    }

    @GetMapping("/find/{id}")
    public Doctor get(@PathVariable Long id) {
            logger.info("Received request to fetch doctor with ID: " + id);
        try {
            logger.info("Fetching doctor with ID: " + id);
            return service.getDoctor(id);
        } catch (Exception e) {
            logger.error("Error fetching doctor with ID: " + id, e);
            throw e;
        }
    }

    @PostMapping("/save")
    public Doctor add(@RequestBody Doctor d) {
            logger.info("Received request to add new doctor");
        try {
            logger.info("Adding new doctor");
            return service.addDoctor(d);
        } catch (Exception e) {
            logger.error("Error adding new doctor", e);
            throw e;
        }
    }

    @PutMapping("/update/{id}")
    public Doctor update(@PathVariable Long id, @RequestBody Doctor d) {
            logger.info("Received request to update doctor with ID: " + id);
        try {
            logger.info("Updating doctor with ID: " + id);
            return service.updateDoctor(id, d);
        } catch (Exception e) {
            logger.error("Error updating doctor with ID: " + id, e);
            throw e;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
            logger.info("Received request to delete doctor with ID: " + id);
        try {
            logger.info("Deleting doctor with ID: " + id);
            service.deleteDoctor(id);
        } catch (Exception e) {
            logger.error("Error deleting doctor with ID: " + id, e);
            throw e;
        }
    }
}
