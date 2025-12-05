package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;

@Service
public class PatientService {
	@Autowired
	PatientRepository repo;
	
	public List<Patient> getAllPatients(){
		return repo.findAll();		
	}
	
	public Patient getPatient(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public Patient addPatient(Patient p) {
        if (p.getId() == null) {
            throw new RuntimeException("ID is required because you are adding ID manually!");
        }

        if (repo.existsById(p.getId())) {
            throw new RuntimeException("Patient ID already exists! Use update API instead.");
        }

        return repo.save(p);
	}

	public Patient updatePatient(Long id, Patient p) {
		Patient existing = repo.findById(id).orElse(null);
		if(existing != null) {
			existing.setName(p.getName());
			existing.setAge(p.getAge());
			existing.setGender(p.getGender());
			existing.setPhone(p.getPhone());
            existing.setDiseaseDepartment(p.getDiseaseDepartment());
			return repo.save(existing);
		}
		return null;
	}
	
	public void deletePatient(Long id) {
		repo.deleteById(id);
	}
}
