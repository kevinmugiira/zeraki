package com.example.kevinzeraki.zeraki.Requests;

import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.models.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
@AllArgsConstructor
public class CourseRequest {

    private String name;
    private String code;
    private String department;
    private List<Institution> institutions;
    private String description;
    private Student student;
}
