package com.example.demo.controller;

import com.example.demo.service.OtpService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@CrossOrigin
public class OtpController {

    @Autowired
    private OtpService otpService;


    @PostMapping("/send")
    public String sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        otpService.sendOtp(email);
        System.out.println("OTP SEND");
        return "OTP sent successfully!";
        
    }

    @PostMapping("/verify")
    public boolean verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        return otpService.verifyOtp(email, otp);
    }
}
