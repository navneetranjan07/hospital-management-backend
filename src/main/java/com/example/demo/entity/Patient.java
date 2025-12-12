package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO ID
    private Long id;

    private String name;
    private int age;
    private String gender;
    private String phone;

    @Column(name = "disease_department")
    private String diseaseDepartment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiseaseDepartment() {
        return diseaseDepartment;
    }

    public void setDiseaseDepartment(String diseaseDepartment) {
        this.diseaseDepartment = diseaseDepartment;
    }
}
