package com.example.kevinzeraki.zeraki.controllers;


import com.example.kevinzeraki.zeraki.Requests.CourseRequest;
import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private final CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);


    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody CourseRequest request) {
        courseService.addCourse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course added successfully!");
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Course>> getSortedCourses(@RequestParam("order") String order) {
        try {
            List<Course> sortedCourses = courseService.getSourtedCourses(order);
            return ResponseEntity.ok(sortedCourses);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

    }

    @GetMapping("/{institutionId}/courses")
    public ResponseEntity<List<Course>> getCoursesByInstitution(@PathVariable ("institutionId") Long institution_id) {
        List<Course> courseList = courseService.coursesByInstitution(institution_id);
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/search/")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam("keyword") String keyword) {
        List<Course> courses = courseService.searchCourseByKeyword(keyword);
        logger.info("retrieved courses: {}", courses);
        return ResponseEntity.ok(courses);
    }

//    @DeleteMapping("/delete/{courseId}")
//    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
//        try {
//            courseService.deleteCourse(courseId);
//            return ResponseEntity.ok("Course deleted successfully");
//        } catch (IllegalStateException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while deleting the course");
//        }
//    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<String> updateCourseName(@PathVariable Long courseId, @RequestParam String newName) {
        try {
            courseService.updateCourseName(courseId, newName);
            return ResponseEntity.ok("Course name update successfully");
        } catch(NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the course name");
        }
    }


}
