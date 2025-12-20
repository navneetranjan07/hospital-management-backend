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

    // ---------- helpers ----------
    private int safePage(int page) { return Math.max(page, 0); }
    private int safeSize(int size) { return Math.max(size, 1); }

    private int startRow(int page, int size) {
        return safePage(page) * safeSize(size);
    }

    private int endRow(int page, int size) {
        return (safePage(page) + 1) * safeSize(size);
    }

    private PageRequest pageRequest(int page, int size) {
        return PageRequest.of(safePage(page), safeSize(size));
    }

    // ---------- PAGINATED (ALL) ----------
    public Page<Doctor> getDoctorsPaged(int page, int size) {
        List<Doctor> doctors =
                repo.getDoctorsPaged(startRow(page, size), endRow(page, size));
        return new PageImpl<>(doctors, pageRequest(page, size), repo.countDoctors());
    }

    // ---------- BY SPECIALIZATION ----------
    public Page<Doctor> getDoctorsBySpecialty(String specialization, int page, int size) {
        String spec = specialization.trim();
        List<Doctor> doctors =
                repo.getDoctorsBySpecializationPaged(spec, startRow(page, size), endRow(page, size));
        return new PageImpl<>(
                doctors,
                pageRequest(page, size),
                repo.countDoctorsBySpecialization(spec)
        );
    }

    // ---------- SEARCH ----------
    public Page<Doctor> searchDoctors(String specialization, String keyword, int page, int size) {
        String spec = specialization.trim();
        String key = keyword.trim();

        List<Doctor> doctors =
                repo.searchDoctorsBySpecialization(
                        spec, key, startRow(page, size), endRow(page, size)
                );

        return new PageImpl<>(
                doctors,
                pageRequest(page, size),
                repo.countSearchDoctors(spec, key)
        );
    }

    // ---------- CRUD ----------
    public List<Doctor> getAllDoctors() {
        return repo.findAll();
    }

    public Doctor getDoctor(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found: " + id));
    }

    public Doctor addDoctor(Doctor d) {
        return repo.save(d);
    }

    public Doctor updateDoctor(Long id, Doctor d) {

        Doctor existing = getDoctor(id);

        existing.setName(d.getName());
        existing.setSpecialization(d.getSpecialization());
        existing.setPhone(d.getPhone());
        existing.setExperience(d.getExperience());
        existing.setFees(d.getFees());

        // 🔥 preserve image
        if (d.getImagePath() != null && !d.getImagePath().isBlank()) {
            existing.setImagePath(d.getImagePath());
        }

        return repo.save(existing);
    }

    public void deleteDoctor(Long id) {
        repo.deleteById(id);
    }
}
