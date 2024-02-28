package com.example.kevinzeraki.zeraki.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "institution")
public class Institution {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", nullable = false)
    private Long Id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @ManyToMany(mappedBy = "institutions")
    @ToString.Exclude
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "institution")
    @ToString.Exclude
    private List<Student> students;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Institution that = (Institution) o;
        return Id != null && Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
