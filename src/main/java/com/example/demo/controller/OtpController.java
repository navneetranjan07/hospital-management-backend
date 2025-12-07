package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OtpService;

@RestController
@RequestMapping("/otp")
//@CrossOrigin

public class OtpController {

	@Autowired
	private OtpService otpService;
	
	
	@PostMapping("/send")
	public String sendOtp(@RequestBody Map<String, String> request) {
		String email=request.get("email");
		otpService.sendOtp(email);
		System.out.println("OTP Send");
		return "OTP Send Successfully";
		
	}
	
	@PostMapping("/verify")
	public boolean verifyOtp(@RequestBody Map<String, String> request) {
		String email=request.get("email");
		String otp=request.get("otp");
		return otpService.verifyOtp(email, otp);
	}
}
