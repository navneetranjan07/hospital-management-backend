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

    // ---------------- HELPER METHODS ----------------
    private int startRow(int page, int size) {
        return Math.max(page, 0) * size;
    }

    private int endRow(int page, int size) {
        return (Math.max(page, 0) + 1) * size;
    }

    private PageRequest pageRequest(int page, int size) {
        return PageRequest.of(Math.max(page, 0), Math.max(size, 1));
    }

    // ---------------- PAGINATED FETCH (ALL) ----------------
    public Page<Doctor> getDoctorsPaged(int page, int size) {

        List<Doctor> doctors = repo.getDoctorsPaged(
                startRow(page, size),
                endRow(page, size)
        );

        long total = repo.countDoctors();

        return new PageImpl<>(doctors, pageRequest(page, size), total);
    }

    // ---------------- PAGINATED BY SPECIALIZATION ----------------
    public Page<Doctor> getDoctorsBySpecialty(String specialization, int page, int size) {

        String normalizedSpecialization = specialization.trim();

        List<Doctor> doctors = repo.getDoctorsBySpecializationPaged(
                normalizedSpecialization,
                startRow(page, size),
                endRow(page, size)
        );

        long total = repo.countDoctorsBySpecialization(normalizedSpecialization);

        return new PageImpl<>(doctors, pageRequest(page, size), total);
    }

    // ---------------- SEARCH BY NAME + SPECIALIZATION ----------------
    public Page<Doctor> searchDoctors(
            String specialization,
            String keyword,
            int page,
            int size
    ) {

        String normalizedSpecialization = specialization.trim();
        String normalizedKeyword = keyword.trim();

        List<Doctor> doctors = repo.searchDoctorsBySpecialization(
                normalizedSpecialization,
                normalizedKeyword,
                startRow(page, size),
                endRow(page, size)
        );

        long total = repo.countSearchDoctors(
                normalizedSpecialization,
                normalizedKeyword
        );

        return new PageImpl<>(doctors, pageRequest(page, size), total);
    }

    // ---------------- CRUD ----------------
    public List<Doctor> getAllDoctors() {
        return repo.findAll();
    }

    public Doctor getDoctor(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor addDoctor(Doctor d) {
        return repo.save(d);
    }

    public Doctor updateDoctor(Long id, Doctor d) {

        Doctor existing = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id: " + id));

        existing.setName(d.getName());
        existing.setSpecialization(d.getSpecialization());
        existing.setPhone(d.getPhone());
        existing.setExperience(d.getExperience());
        existing.setFees(d.getFees());

        return repo.save(existing);
    }

    public void deleteDoctor(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
