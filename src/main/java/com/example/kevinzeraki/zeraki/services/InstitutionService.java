package com.example.kevinzeraki.zeraki.services;


import com.example.kevinzeraki.zeraki.Requests.InstitutionRequest;
import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.repos.InstitutionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    @Autowired
    private final InstitutionRepo institutionRepo;

    public String addInstitution(InstitutionRequest request) throws JsonProcessingException {

        Institution institutionModel1 = new Institution();

        institutionModel1.setName(request.getName());
        institutionModel1.setAddress(request.getAddress());
        institutionModel1.setEmail(request.getEmail());
        institutionModel1.setPhoneNumber(request.getPhoneNumber());

        institutionRepo.save(institutionModel1);
        ObjectMapper objectMapper = new ObjectMapper();
        String success = "Institution created successfully!";

        return objectMapper.writeValueAsString(success);
    }

    public void editInstitutionName(Long id, String newName) {
        Optional<Institution> institution = institutionRepo.findById(id);
        if (institution.isPresent()) {
            Institution institution1 = institution.get();
            institution1.setName(newName);
            institutionRepo.save(institution1);
        } else {
            throw new NoSuchElementException("Institution with ID: " + id + " not found!");
        }
    }

    public List<Institution> getSourtedCourses(String order) {
        if ("asc".equalsIgnoreCase(order)) {
            return institutionRepo.findAllByOrderByNameAsc();
        } else if ("desc".equalsIgnoreCase(order)) {
            return institutionRepo.findAllByOrderByNameDesc();
        } else {
            throw new IllegalArgumentException("Invalid sorting order");
        }
    }

    public List<Institution> listInstitutions() {
        return institutionRepo.findAll();
    }
//    public List<InstitutionModel> listInstitutionByName() {
//        Sort sort = Sort.by("name").ascending();
//        return institutionRepo.findBySort(sort);
//
//    }

//    @Override
    public void deleteInstitution(Long Id) {
        institutionRepo.deleteById(Id);
    }

//    public Long findById(Long id) {
////        Institution institution = new Institution();
//        return institutionRepo.findById(id);
//    }
}
