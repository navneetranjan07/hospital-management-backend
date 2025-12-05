package com.example.demo.controller;

import java.util.List;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
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

    private static final Logger logger = Logger.getLogger(DepartmentController.class.getName());

    // Create
    @PostMapping("/save")
    public Department createDepartment(@RequestBody Department dept) {
        logger.info("Received request to create new department");
        try {
            logger.info("Creating new department");
            return service.addDepartment(dept);
        } catch (Exception e) {
            logger.severe("Error creating new department: " + e.getMessage());
            throw e;
        }
    }

    // Update
    @PutMapping("/update/{id}")
    public Department updateDepartment(
            @PathVariable Long id,
            @RequestBody Department dept) {
        logger.info("Received request to update department with ID: " + id);
        try {
            logger.info("Updating department with ID: " + id);
            dept.setId(id);
            return service.updateDepartment(id, dept);
        } catch (Exception e) {
            logger.severe("Error updating department with ID " + id + ": " + e.getMessage());
            throw e;
        }
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        logger.info("Received request to delete department with ID: " + id);
        try {
            service.deleteDepartment(id);
            logger.info("Deleted department with ID: " + id);
            return "Department deleted with ID: " + id;
        } catch (Exception e) {
            logger.severe("Error deleting department with ID " + id + ": " + e.getMessage());
            throw e;
        }
    }

    // Get by ID
    @GetMapping("/find/{id}")
    public Department getOne(@PathVariable Long id) {
        logger.info("Received request to fetch department with ID: " + id);
        try {
            logger.info("Fetching department with ID: " + id);
            return service.getDepartmentById(id);
        } catch (Exception e) {
            logger.severe("Error fetching department with ID " + id + ": " + e.getMessage());
            throw e;
        }
    }

    // Get all
    @GetMapping("/fetchall")
    public List<Department> getAll() {
        try {
            logger.info("Fetching all departments");
            return service.getAllDepartments();
        } catch (Exception e) {
            logger.severe("Error fetching all departments: " + e.getMessage());
            throw e;
        }
    }
}
