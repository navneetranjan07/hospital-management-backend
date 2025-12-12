package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // -------- 🔍 SEARCH --------
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Patient> searchByName(@Param("keyword") String keyword);

    @Query("SELECT p FROM Patient p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.diseaseDepartment) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Patient> searchByNameOrCondition(@Param("keyword") String keyword);

    @Query("SELECT p FROM Patient p WHERE p.age BETWEEN :min AND :max")
    List<Patient> filterByAgeRange(@Param("min") int min, @Param("max") int max);

    @Query("SELECT p FROM Patient p WHERE LOWER(p.gender) = LOWER(:gender)")
    List<Patient> filterByGender(@Param("gender") String gender);

    // -------- 📄 ORACLE PAGINATION (ROWNUM) --------
    @Query(
            value = """
                    SELECT * FROM (
                        SELECT p.*, ROWNUM rnum
                        FROM (
                            SELECT * FROM patient ORDER BY id
                        ) p
                        WHERE ROWNUM <= :endRow
                    )
                    WHERE rnum > :startRow
                    """,
            nativeQuery = true
    )
    List<Patient> getPatientsPaged(
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );

    @Query("SELECT COUNT(p) FROM Patient p")
    long countPatients();
}
