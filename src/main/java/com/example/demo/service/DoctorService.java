package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Doctor;
import com.example.demo.repository.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repo;

    // -------- 📄 ORACLE PAGINATION --------
    public Page<Doctor> getDoctorsPaged(int page, int size) {

        int startRow = page * size;
        int endRow = startRow + size;

        List<Doctor> doctors = repo.getDoctorsPaged(startRow, endRow);
        long total = repo.countDoctors();

        return new PageImpl<>(doctors, PageRequest.of(page, size), total);
    }

    // -------- CRUD --------
    public List<Doctor> getAllDoctors() {
        return repo.findAll();
    }

    public Doctor getDoctor(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor addDoctor(Doctor d) {
        return repo.save(d);
    }

    public Doctor updateDoctor(Long id, Doctor d) {

        Doctor existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        existing.setName(d.getName());
        existing.setSpecialization(d.getSpecialization());
        existing.setPhone(d.getPhone());

        return repo.save(existing);
    }

    public void deleteDoctor(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
