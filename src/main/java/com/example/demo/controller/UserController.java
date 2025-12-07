package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Hospital_User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/userss")
//@CrossOrigin// your React app URL
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping
    public List<Hospital_User> getAll(){
		try {
            logger.info("Fetching all users");
            return userService.getAllUsers();
        } catch (Exception e) {
            logger.error("Error fetching users", e);
            throw e;
        }
    	
    }

    // Register new user
    @PostMapping("/register")
    public Hospital_User register(@RequestBody Hospital_User user) {
        try {
            logger.info("Registering new user: " + user.getUsername());
            return userService.registerUser(user);
        } catch (Exception e) {
            logger.error("Error registering user: " + user.getUsername(), e);
            throw e;
        }
    }

    // Login user
    @PostMapping("/login")
    public Hospital_User login(@RequestBody Hospital_User user) {
    	try{
            logger.info("User login attempts: {}", user.getUsername());
            Hospital_User existingUser = userService.loginUser(user.getUsername(), user.getPassword());
            if (existingUser != null) {
                return existingUser;
            }
            throw new RuntimeException("Invalid username or password");
        } catch (Exception e) {
            logger.error("Error during login for user: " + user.getUsername(), e);
            throw e;
        }
    }
}
