package com.example.kevinzeraki.zeraki.repos;

import com.example.kevinzeraki.zeraki.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Long> {

    boolean existsByCourseId(Long courseId);

    List<Student> findByInstitutionId(Long institutionId);

    List<Student> findByCourseId(Long courseId);

    List<Student> findByInstitutionIdAndCourseId(Long institutionId, Long courseId);
    @Query("SELECT s FROM Student s WHERE s.institution.id = :institutionId")
    Page<Student> findByInstitution(@Param("institutionId") Long institutionId, Pageable pageable);

}
