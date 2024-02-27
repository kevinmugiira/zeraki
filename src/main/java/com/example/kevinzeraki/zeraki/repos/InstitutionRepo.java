package com.example.kevinzeraki.zeraki.repos;

import com.example.kevinzeraki.zeraki.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstitutionRepo extends JpaRepository<Institution, Long> {

    List<Institution> findByName(String name);
//    List<InstitutionModel> findBySort(Sort sort);
    void deleteInstitutionById(Long Id);
}
