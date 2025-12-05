package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Doctor;
import com.example.demo.repository.DoctorRepository;

@Service
public class DoctorService {
	@Autowired
	DoctorRepository repo;
	
	public List<Doctor> getAllDoctors(){
		return repo.findAll();		
	}
	
	public Doctor getDoctor(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public Doctor addDoctor(Doctor d) {
        if (d.getId() == null) {
            throw new RuntimeException("ID is required because you are adding ID manually!");
        }

        if (repo.existsById(d.getId())) {
            throw new RuntimeException("Doctor ID already exists! Use update API instead.");
        }
		return repo.save(d);
	}
	
	public Doctor updateDoctor(Long id, Doctor d) {
		Doctor existing = repo.findById(id).orElse(null);
		if(existing != null) {
			existing.setName(d.getName());
			existing.setId(d.getId());
			existing.setSpecialization(d.getSpecialization());
			existing.setPhone(d.getPhone());
			return repo.save(existing);
		}
		return null;
	}
	
	public void deleteDoctor(Long id) {
		repo.deleteById(id);
	}
}
