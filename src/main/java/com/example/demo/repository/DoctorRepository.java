package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // ---------- PAGINATED FETCH (ALL) ----------
    @Query(
            value = """
        SELECT * FROM (
            SELECT d.*, ROWNUM rnum
            FROM (
                SELECT * FROM DOCTOR ORDER BY ID
            ) d
            WHERE ROWNUM <= :endRow
        )
        WHERE rnum > :startRow
        """,
            nativeQuery = true
    )
    List<Doctor> getDoctorsPaged(
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );

    @Query("SELECT COUNT(d) FROM Doctor d")
    long countDoctors();

    // ---------- PAGINATED BY SPECIALIZATION ----------
    @Query(
            value = """
        SELECT * FROM (
            SELECT d.*, ROWNUM rnum
            FROM (
                SELECT * FROM DOCTOR
                WHERE LOWER(SPECIALIZATION) = LOWER(:specialization)
                ORDER BY ID
            ) d
            WHERE ROWNUM <= :endRow
        )
        WHERE rnum > :startRow
        """,
            nativeQuery = true
    )
    List<Doctor> getDoctorsBySpecializationPaged(
            @Param("specialization") String specialization,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );

    @Query("""
        SELECT COUNT(d)
        FROM Doctor d
        WHERE LOWER(d.specialization) = LOWER(:specialization)
    """)
    long countDoctorsBySpecialization(
            @Param("specialization") String specialization
    );

    // ---------- SEARCH ----------
    @Query(
            value = """
        SELECT * FROM (
            SELECT d.*, ROWNUM rnum
            FROM (
                SELECT * FROM DOCTOR
                WHERE LOWER(SPECIALIZATION) = LOWER(:specialization)
                AND LOWER(NAME) LIKE LOWER(CONCAT('%', :keyword, '%'))
                ORDER BY ID
            ) d
            WHERE ROWNUM <= :endRow
        )
        WHERE rnum > :startRow
        """,
            nativeQuery = true
    )
    List<Doctor> searchDoctorsBySpecialization(
            @Param("specialization") String specialization,
            @Param("keyword") String keyword,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow
    );

    @Query("""
        SELECT COUNT(d)
        FROM Doctor d
        WHERE LOWER(d.specialization) = LOWER(:specialization)
        AND LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    long countSearchDoctors(
            @Param("specialization") String specialization,
            @Param("keyword") String keyword
    );
}
