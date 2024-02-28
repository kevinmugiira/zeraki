package com.example.kevinzeraki.zeraki.services;


import com.example.kevinzeraki.zeraki.Requests.StudentRequest;
import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.models.Student;
import com.example.kevinzeraki.zeraki.repos.CourseRepo;
import com.example.kevinzeraki.zeraki.repos.InstitutionRepo;
import com.example.kevinzeraki.zeraki.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    StudentRepo studentRepo;

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    InstitutionRepo institutionRepo;

    public void addStudent(StudentRequest request) {
        Student student = new Student();
        student.setFirstname(request.getFirstname());
        student.setLastname(request.getLastname());
        student.setEmail(request.getEmail());
        student.setDob(request.getDob());

        studentRepo.save(student);
    }

    public void editStudentName(Long student_id, String newFirstName, String newLastName) {
        Optional<Student> student = studentRepo.findById(student_id);
        if (student.isPresent()) {
            Student student1 = student.get();
            student1.setFirstname(newFirstName);
            student1.setLastname(newLastName);
            studentRepo.save(student1);
        } else {
            throw new NoSuchElementException("Student with ID: " + student_id + " not found!");
        }
    }

    public void changeStudentCourse(Long student_id, Long newCourseId) {
        // Retrieve the student from the database
        Student student = studentRepo.findById(student_id).orElse(null);
        if (student == null) {
            // Handle error: student not found
            return;
        }

        // Retrieve the new course from the database
        Course newCourse = courseRepo.findById(newCourseId).orElse(null);
        if (newCourse == null) {
            // Handle error: course not found
            return;
        }

        // Update the course of the student
        student.setCourse(newCourse);

        // Save the updated student entity to the database
        studentRepo.save(student);
    }

//    public void transferStudentToAnotherInstitution(Long studentId, Long newInstitutionId, Long newCourseId) {
//        Student student = studentRepo.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        Institution newInstitution = institutionRepo.findById(newInstitutionId)
//                .orElseThrow(() -> new RuntimeException("Institution not found"));
//
//        Course newCourse = courseRepo.findById(newCourseId)
//                .orElseThrow(() -> new RuntimeException("Course not found"));
//
//        student.setInstitution(newInstitution);
//        student.setCourse(newCourse);
//
//        studentRepo.save(student);
//    }


    public List<Student> getAllStudentsByInstitutionAndCourse(Long institutionId, Long courseId) {
        if (institutionId == null && courseId == null) {
            return studentRepo.findAll(); // Retrieve all students if no filtering is applied
        } else if (institutionId != null && courseId == null) {
            return studentRepo.findByInstitutionId(institutionId); // Retrieve students by institution
        } else if (institutionId == null && courseId != null) {
            return studentRepo.findByCourseId(courseId); // Retrieve students by course
        } else {
            return studentRepo.findByInstitutionIdAndCourseId(institutionId, courseId); // Retrieve students by both institution and course
        }
    }

    public Page<Student> findStudentsByInstitutionId(Long institution_id, Pageable pageable) {
        return studentRepo.findByInstitution(institution_id, pageable);
    }

    public void deleteStudent(Long student_id) {
        studentRepo.deleteById(student_id);
    }

}
