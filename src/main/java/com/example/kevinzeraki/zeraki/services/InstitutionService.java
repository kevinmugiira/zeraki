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
        institutionRepo.deleteInstitutionById(Id);
    }
}
