package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PATIENT")
public class Patient {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_seq_gen"
    )
    @SequenceGenerator(
            name = "patient_seq_gen",
            sequenceName = "PATIENT_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DISEASE_DEPARTMENT")
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
