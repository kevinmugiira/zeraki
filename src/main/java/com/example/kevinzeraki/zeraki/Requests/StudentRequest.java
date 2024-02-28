package com.example.kevinzeraki.zeraki.Requests;

import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@ToString
@AllArgsConstructor
public class StudentRequest {

    private String firstname;
    private String lastname;
    private String email;
    private LocalDate dob;

}
