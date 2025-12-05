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

import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping("/patients")
@CrossOrigin
public class PatientController {
	@Autowired
	PatientService service;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	@GetMapping("/fetchall")
	public List<Patient> getAll(){
        try {
            logger.info("Fetching all patients");
            return service.getAllPatients();
        } catch (Exception e) {
            logger.error("Error fetching all patients", e);
            throw e;
        }
	}

	@GetMapping("/find/{id}")
	public Patient get(@PathVariable Long id) {
        try {
            logger.info("Fetching patient with ID: " + id);
            return service.getPatient(id);
        } catch (Exception e) {
            logger.error("Error fetching patient with ID: " + id, e);
            throw e;
        }
	}

	@PostMapping("/save")
	public Patient add(@RequestBody Patient p) {
		try {
            logger.info("Adding new patient");
            return service.addPatient(p);
        } catch (Exception e) {
            logger.error("Error adding new patient", e);
            throw e;
        }
	}

	@PutMapping("/update/{id}")
	public Patient update(@PathVariable Long id, @RequestBody Patient p) {
		try {
            logger.info("Updating patient with ID: " + id);
            return service.updatePatient(id, p);
        } catch (Exception e) {
            logger.error("Error updating patient with ID: " + id, e);
            throw e;
        }
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		try {
            logger.info("Deleting patient with ID: " + id);
            service.deletePatient(id);
        } catch (Exception e) {
            logger.error("Error deleting patient with ID: " + id, e);
            throw e;
        }
	}
}
