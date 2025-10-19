package com.example.demo.dto;

public class AppointmentRequest {
	private int id;
	

	private Long patientId;
	private Long doctorId;
	private String appointmentTime; // Must be in ISO format (yyyy-MM-ddTHH:mm:ss)

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
}
