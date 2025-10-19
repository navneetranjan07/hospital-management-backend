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

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;


@RestController
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorController {
	@Autowired
	DoctorService service;

	@GetMapping
	public List<Doctor> getAll(){
		return service.getAllDoctors();
	}

	@GetMapping("/{id}")
	public Doctor get(@PathVariable Long id) {
		return service.getDoctor(id);
	}

	@PostMapping
	public Doctor add(@RequestBody Doctor d) {
		return service.addDoctor(d);
	}

	@PutMapping("/{id}")
	public Doctor update(@PathVariable Long id, @RequestBody Doctor d) {
		return service.updateDoctor(id, d);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.deleteDoctor(id);
	}
}
