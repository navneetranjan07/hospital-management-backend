package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String UPLOAD_DIR = "uploads/doctors/";

    // ---------------- PAGINATED (ALL) ----------------
    @GetMapping("/fetch")
    public Page<Doctor> fetch(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        return service.getDoctorsPaged(page, size);
    }

    // ---------------- BY SPECIALIZATION ----------------
    @GetMapping("/specialization/{name}")
    public Page<Doctor> bySpecialization(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        return service.getDoctorsBySpecialty(name, page, size);
    }

    // ---------------- SEARCH ----------------
    @GetMapping("/search")
    public Page<Doctor> search(
            @RequestParam String specialization,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        return service.searchDoctors(specialization, keyword, page, size);
    }

    // ---------------- FETCH ALL ----------------
    @GetMapping("/fetchall")
    public List<Doctor> fetchAll() {
        return service.getAllDoctors();
    }

    // ---------------- CREATE (WITH IMAGE) ----------------
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Doctor save(
            @RequestPart("doctor") String doctorJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        Doctor doctor = objectMapper.readValue(doctorJson, Doctor.class);

        if (image != null && !image.isEmpty()) {
            doctor.setImagePath(saveImage(image));
        }

        return service.addDoctor(doctor);
    }

    // ---------------- UPDATE (WITH IMAGE) ----------------
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Doctor update(
            @PathVariable Long id,
            @RequestPart("doctor") String doctorJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        Doctor doctor = objectMapper.readValue(doctorJson, Doctor.class);

        if (image != null && !image.isEmpty()) {
            doctor.setImagePath(saveImage(image));
        }

        return service.updateDoctor(id, doctor);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteDoctor(id);
    }

    // ---------------- IMAGE SAVE ----------------
    private String saveImage(MultipartFile image) throws Exception {

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + filename);

        Files.write(path, image.getBytes());

        return "/uploads/doctors/" + filename;
    }
}
