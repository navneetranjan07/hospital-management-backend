package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

	@Autowired
	private JavaMailSender mailsender;
	
	private final Map<String, String> otpStorage=new HashMap<>();
	
	
//	send OTP
	
	public void sendOtp(String email) {
		String otp= String.valueOf(100000 + new Random().nextInt(900000));
		otpStorage.put(email, otp);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Your OTP code");
		message.setText("Your Verification OTP is : "+otp +"\n\n This code will expire in 5 minutes");
		
		mailsender.send(message);
	}
	
//	verify OTP
	public boolean verifyOtp(String email, String Otp) {
		if(otpStorage.containsKey(email) && otpStorage.get(email).equals(Otp)) {
			otpStorage.remove(email);  //remove after verification
			return true;
		}
		return false;
	}
}
