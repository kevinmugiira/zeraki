package com.example.kevinzeraki.zeraki.services;


import com.example.kevinzeraki.zeraki.Requests.CourseRequest;
import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.repos.CourseRepo;
//import com.example.kevinzeraki.zeraki.repos.InstCoursRepo;
import com.example.kevinzeraki.zeraki.repos.InstitutionRepo;
import com.example.kevinzeraki.zeraki.repos.StudentRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class CourseService {


    @Autowired
    InstitutionRepo institutionRepo;
    @Autowired
    CourseRepo courseRepo;

    @Autowired
    StudentRepo studentRepo;

//    @Autowired
//    InstCoursRepo instCoursRepo;

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    public void addCourse(CourseRequest request) {

        Course course1 = new Course();

        course1.setName(request.getName());

        // logging out to see if the request is fetching accurate data
        logger.info("name: {}", course1.getName());
        course1.setCode(request.getCode());
        course1.setDepartment(request.getDepartment());
        course1.setInstitutions(request.getInstitutions());
        course1.setDescription(request.getDescription());

        courseRepo.save(course1);

    }

    public List<Course> getSourtedCourses(String order) {
        if ("asc".equalsIgnoreCase(order)) {
            return courseRepo.findAllByOrderByNameAsc();
        } else if ("desc".equalsIgnoreCase(order)) {
            return courseRepo.findAllByOrderByNameDesc();
        } else {
            throw new IllegalArgumentException("Invalid sorting order");
        }
    }

    public Optional<Course> coursesByInstitution(Long institution_id) {
//        return instCoursRepo.findAllByInstitutionId(institution_id);
        // Fetch institution by ID
        Optional<Institution> institutionOptional = institutionRepo.findById(institution_id);
        if (institutionOptional.isEmpty()) {
            // Handle case when institution is not found
            return Optional.empty();
        }
        Institution institution = institutionOptional.get();

        // Retrieve courses associated with the institution
        return courseRepo.findById(institution);
    }


    public List<Course> searchCourseByKeyword(String keyword) {
        return courseRepo.findByNameContainingIgnoreCase(keyword);
    }

    public Course addCourseToInstitution(String name, List<Institution> institution) {

        for (Institution institution1 : institution) {
            Course existingCourse = courseRepo.findByNameAndInstitutions(name, (List<Institution>) institution1);

            if (existingCourse != null) {
                throw new IllegalArgumentException("A course with the same name already exists in this institution");
            }
        }

        Course newCourse = new Course();
        newCourse.setName(name);

        Institution institution1 = (Institution) newCourse.getInstitutions();
        if (institution1 != null) {
            institution1.getCourses().add(newCourse);
            institutionRepo.save(institution1);
        } else {
            institution1.getCourses().add(newCourse);
            institutionRepo.save(institution1);
        }
        newCourse.setInstitutions(institution);
        return courseRepo.save(newCourse);
    }

    public void deleteCourse(Long courseId) {
        boolean hasStudents = studentRepo.existsByCourseId(courseId);

        if (hasStudents) {
            throw new IllegalStateException("Cannot delete course with an assigned student");
        }
        courseRepo.deleteById(courseId);
    }

    public void updateCourseName(Long courseId, String newName) {
        Optional<Course> course = courseRepo.findById(courseId);
        if (course.isPresent()) {
            Course course1 = course.get();
            course1.setName(newName);
            courseRepo.save(course1);
        } else {
            throw new NoSuchElementException("Course with ID: " + courseId + " not found!");
        }
    }
}
