package com.example.kevinzeraki.zeraki.controllers;


import com.example.kevinzeraki.zeraki.Requests.StudentRequest;
import com.example.kevinzeraki.zeraki.models.Student;
import com.example.kevinzeraki.zeraki.responses.StudentSearchResponse;
import com.example.kevinzeraki.zeraki.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody StudentRequest request) {
        studentService.addStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully!");
    }

    @PutMapping("/edit/{student_id}")
    public ResponseEntity<String> updateCourseName(@PathVariable Long student_id, @RequestParam String newFirstName, String newLastName) {
        try {
            studentService.editStudentName(student_id, newFirstName, newLastName);
            return ResponseEntity.ok("Student name edited successfully");
        } catch(NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while editing the student name");
        }
    }

    @PutMapping("/{student_id}/course")
    public ResponseEntity<String> changeStudentCourse(@PathVariable Long student_id, @RequestParam Long newCourseId) {
        try {
            studentService.changeStudentCourse(student_id, newCourseId);
            return ResponseEntity.ok("Student course changed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error changing student course: " + e.getMessage());
        }
    }

    @PutMapping("/{student_id}/transfer")
    public ResponseEntity<String> transferStudentToAnotherInstitution(
            @PathVariable Long student_id,
            @RequestParam Long newInstitutionId,
            @RequestParam Long newCourseId
    ) {
        try {
            studentService.transferStudentToAnotherInstitution(student_id, newInstitutionId, newCourseId);
            return ResponseEntity.ok("Student transferred successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error transferring student: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<StudentSearchResponse> searchStudents(
            @RequestParam(required = false) Long institutionId,
            @RequestParam(required = false) Long courseId
    ) {
        StudentSearchResponse response = new StudentSearchResponse();

        try {
            List<Student> students = studentService.getAllStudentsByInstitutionAndCourse(institutionId, courseId);
            response.setStudents(students);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setErrorMessage("Error retrieving students: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{student_id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long student_id) {
        try {
            studentService.deleteStudent(student_id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the student");
        }
    }
}
