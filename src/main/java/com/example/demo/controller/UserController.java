package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Hospital_User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/userss")
@CrossOrigin// your React app URL
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<Hospital_User> getAll(){
		return userService.getAllUsers();
    	
    }

    // Register new user
    @PostMapping("/register")
    public Hospital_User register(@RequestBody Hospital_User user) {
        return userService.registerUser(user);
    }

    // Login user
    @PostMapping("/login")
    public Hospital_User login(@RequestBody Hospital_User user) {
    	Hospital_User existingUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        }
        throw new RuntimeException("Invalid username or password");
    }
}
