package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    // Create
    @PostMapping("/save")
    public Department createDepartment(@RequestBody Department dept) {
        return service.addDepartment(dept);
    }

    // Update
    @PutMapping("/update/{id}")
    public Department updateDepartment(
            @PathVariable Long id,
            @RequestBody Department dept) {
        return service.updateDepartment(id, dept);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        service.deleteDepartment(id);
        return "Department deleted with ID: " + id;
    }

    // Get by ID
    @GetMapping("/find/{id}")
    public Department getOne(@PathVariable Long id) {
        return service.getDepartmentById(id);
    }

    // Get all
    @GetMapping("/fetchall")
    public List<Department> getAll() {
        return service.getAllDepartments();
    }
}
