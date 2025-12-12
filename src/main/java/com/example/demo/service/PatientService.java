package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repo;

    // -------- 📄 ORACLE PAGINATION --------
    public Page<Patient> getPatientsPaged(int page, int size) {

        int startRow = page * size;
        int endRow = startRow + size;

        List<Patient> patients = repo.getPatientsPaged(startRow, endRow);
        long total = repo.countPatients();

        return new PageImpl<>(patients, PageRequest.of(page, size), total);
    }

    // -------- CRUD --------
    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    public Patient getPatient(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public Patient addPatient(Patient p) {
        return repo.save(p);
    }

    public Patient updatePatient(Long id, Patient p) {

        Patient existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        existing.setName(p.getName());
        existing.setAge(p.getAge());
        existing.setGender(p.getGender());
        existing.setPhone(p.getPhone());
        existing.setDiseaseDepartment(p.getDiseaseDepartment());

        return repo.save(existing);
    }

    public void deletePatient(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        repo.deleteById(id);
    }

    // -------- 🔍 ADV SEARCH --------
    public List<Patient> searchByName(String keyword) {
        return repo.searchByName(keyword);
    }

    public List<Patient> searchByNameOrCondition(String keyword) {
        return repo.searchByNameOrCondition(keyword);
    }

    public List<Patient> filterByAge(int min, int max) {
        return repo.filterByAgeRange(min, max);
    }

    public List<Patient> filterByGender(String gender) {
        return repo.filterByGender(gender);
    }
}
