package com.example.kevinzeraki.zeraki.repos;

import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Optional<Course> findById(Institution institution);

//    List<Course> findAllByInstitutions(Long institution_id);
    List<Course> findByNameContainingIgnoreCase(String keyword);
    List<Course> findAllByOrderByNameAsc();
    List<Course> findAllByOrderByNameDesc();
    Course findByNameAndInstitutions(@NonNull String name, List<Institution> institutions);
}
