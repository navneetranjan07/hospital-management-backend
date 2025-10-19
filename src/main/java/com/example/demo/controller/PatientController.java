package com.example.demo.controller;

import java.util.List;

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

	@GetMapping
	public List<Patient> getAll(){
		return service.getAllPatients();
	}

	@GetMapping("/{id}")
	public Patient get(@PathVariable Long id) {
		return service.getPatient(id);
	}

	@PostMapping
	public Patient add(@RequestBody Patient p) {
		return service.addPatient(p);
	}

	@PutMapping("/{id}")
	public Patient update(@PathVariable Long id, @RequestBody Patient p) {
		return service.updatePatient(id, p);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.deletePatient(id);
	}
}
