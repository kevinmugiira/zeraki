package com.example.kevinzeraki.zeraki.repos;

import com.example.kevinzeraki.zeraki.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Long> {

//    boolean existsByCourseId(Long courseId);

    List<Student> findByInstitutionId(Long institutionId);

    List<Student> findByCourseId(Long courseId);

    List<Student> findByInstitutionIdAndCourseId(Long institutionId, Long courseId);
}
