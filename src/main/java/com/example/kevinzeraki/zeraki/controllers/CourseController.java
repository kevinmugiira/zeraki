package com.example.kevinzeraki.zeraki.controllers;


import com.example.kevinzeraki.zeraki.Requests.AddCourseRequest;
import com.example.kevinzeraki.zeraki.Requests.CourseRequest;
import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.services.CourseService;
import com.example.kevinzeraki.zeraki.services.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private final CourseService courseService;

    @Autowired
    private final InstitutionService institutionRepo;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);


    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody CourseRequest request) {
        courseService.addCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course added successfully!");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourseToInstitution(@RequestBody AddCourseRequest request) {
        try {
            Course newCourse = courseService.addCourseToInstitution(request.getName(), (List<Institution>) request.getInstitution());
            return ResponseEntity.ok("Course added successfully: " + newCourse.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the course.");
        }
    }
//@PostMapping("/add")
//public ResponseEntity<String> addCourseToInstitution(@RequestBody AddCourseRequest request) {
//    try {
//        // Retrieve the institution from the database based on its unique identifier
//        Institution existingInstitution = institutionRepo.findById(request.getInstitution().getId());
//
//        if (existingInstitution == null) {
//            // If the institution doesn't exist, return an error response
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("The institution with ID " + request.getInstitution().getId() + " does not exist.");
//        }
//
//        // Associate the course with the existing institution
//        Course newCourse = courseService.addCourseToInstitution(request.getName(), (List<Institution>) existingInstitution);
//
//        return ResponseEntity.ok("Course added successfully: " + newCourse.getId());
//    } catch (IllegalArgumentException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the course.");
//    }
//}

    @GetMapping("/sorted")
    public ResponseEntity<List<Course>> getSortedCourses(@RequestParam("order") String order) {
        try {
            List<Course> sortedCourses = courseService.getSourtedCourses(order);
            return ResponseEntity.ok(sortedCourses);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

    }

//    @GetMapping("/{institutionId}/courses")
//    public ResponseEntity<List<Course>> getCoursesByInstitution(@PathVariable ("institutionId") Long institution_id) {
//        Optional<Course> courseList = courseService.coursesByInstitution(institution_id);
//        return (ResponseEntity<List<Course>>) ResponseEntity.ok(courseList);
//    }

    @GetMapping("/search/")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam("keyword") String keyword) {
        List<Course> courses = courseService.searchCourseByKeyword(keyword);
        logger.info("retrieved courses: {}", courses);
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/delete/{course_id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long course_id) {
        try {
            courseService.deleteCourse(course_id);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the course");
        }
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<String> updateCourseName(@PathVariable Long courseId, @RequestBody Map<String, String> requestBody) {
        try {
            String newName = requestBody.get("newName");
            courseService.updateCourseName(courseId, newName);
            return ResponseEntity.ok("Course name updated successfully");
        } catch(NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the course name");
        }
    }


}
