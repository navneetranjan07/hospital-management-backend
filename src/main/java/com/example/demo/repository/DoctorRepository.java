package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // -------- 📄 ORACLE PAGINATION --------
    @Query(
            value = """
            SELECT * FROM (
                SELECT d.*, ROWNUM rnum
                FROM (
                    SELECT * FROM doctor ORDER BY id
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
}
