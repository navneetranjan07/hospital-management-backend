package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository repo;

    public DepartmentService(DepartmentRepository repo) {
        this.repo = repo;
    }

    // Create
    public Department addDepartment(Department dept) {
        if (dept.getId() == null) {
            throw new RuntimeException("ID is required because you are adding ID manually!");
        }
        if (repo.existsById(dept.getId())) {
            throw new RuntimeException("Department ID already exists! Use update API instead.");
        }
        return repo.save(dept);
    }

    // Update
    public Department updateDepartment(Long id, Department dept) {
        Department existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found: " + id));

        existing.setName(dept.getName());
        existing.setDescription(dept.getDescription());

        return repo.save(existing);
    }

    // Delete
    public void deleteDepartment(Long id) {
        repo.deleteById(id);
    }

    // Get by ID
    public Department getDepartmentById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found: " + id));
    }

    // Get all
    public List<Department> getAllDepartments() {
        return repo.findAll();
    }
}
