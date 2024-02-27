package com.example.kevinzeraki.zeraki.repos;

import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Long> {
    List<Course> findByInstitution(Institution institution);

    List<Course> findAllByInstitutions(Long institution_id);
    List<Course> findByNameContainingIgnoreCase(String keyword);
    List<Course> findAllByOrderByNameAsc();
    List<Course> findAllByOrderByNameDesc();
}
