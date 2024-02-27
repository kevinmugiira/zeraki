package com.example.kevinzeraki.zeraki.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Entity
@Data
@Table(name = "institution")
public class Institution {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", nullable = false)
    private Long Id;
    private String name;
    private String address;

    @ManyToMany(mappedBy = "institutions")
    @ToString.Exclude
//    @JoinTable(
//            name = "institution_courses",
//            joinColumns = @JoinColumn(name = "institution_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;
    private String email;
    private String phoneNumber;



}
