package com.example.kevinzeraki.zeraki.repos;

import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstitutionRepo extends JpaRepository<Institution, Long> {

    Optional<Institution> findById(Long id);

    List<Institution> findByName(String name);
//    List<InstitutionModel> findBySort(Sort sort);
    void deleteInstitutionById(Long Id);
    List<Institution> findAllByOrderByNameAsc();
    List<Institution> findAllByOrderByNameDesc();

}
