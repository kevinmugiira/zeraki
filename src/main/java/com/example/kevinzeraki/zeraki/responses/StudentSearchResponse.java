package com.example.kevinzeraki.zeraki.responses;

import com.example.kevinzeraki.zeraki.models.Student;

import java.util.List;

public class StudentSearchResponse {

    private List<Student> students;
    private String errorMessage;

    public StudentSearchResponse() {
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
