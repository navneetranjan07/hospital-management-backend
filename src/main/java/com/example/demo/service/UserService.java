package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Hospital_User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
	

    public Hospital_User registerUser(Hospital_User user) {
        return userRepository.save(user);
    }

    public Hospital_User loginUser(String username, String password) {
    	Hospital_User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

	public List<Hospital_User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
}
