package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    PatientService service;

    // -------- 📄 PAGINATED FETCH --------
    @GetMapping("/fetch")
    public Page<Patient> fetchPaginated(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return service.getPatientsPaged(page, size);
    }

    // -------- CRUD --------
    @GetMapping("/fetchall")
    public List<Patient> getAll() {
        return service.getAllPatients();
    }

    @GetMapping("/find/{id}")
    public Patient get(@PathVariable Long id) {
        return service.getPatient(id);
    }

    @PostMapping("/save")
    public Patient add(@RequestBody Patient p) {
        return service.addPatient(p);
    }

    @PutMapping("/update/{id}")
    public Patient update(@PathVariable Long id, @RequestBody Patient p) {
        return service.updatePatient(id, p);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deletePatient(id);
    }

    // -------- 🔍 ADV SEARCH --------
    @GetMapping("/search")
    public List<Patient> searchByName(@RequestParam String keyword) {
        return service.searchByName(keyword);
    }

    @GetMapping("/search/all")
    public List<Patient> searchByNameOrCondition(@RequestParam String keyword) {
        return service.searchByNameOrCondition(keyword);
    }

    @GetMapping("/filter/age")
    public List<Patient> filterByAge(
            @RequestParam int min,
            @RequestParam int max
    ) {
        return service.filterByAge(min, max);
    }

    @GetMapping("/filter/gender")
    public List<Patient> filterByGender(@RequestParam String gender) {
        return service.filterByGender(gender);
    }
}
