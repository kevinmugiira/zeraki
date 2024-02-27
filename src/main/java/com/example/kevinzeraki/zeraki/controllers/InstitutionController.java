package com.example.kevinzeraki.zeraki.controllers;


import com.example.kevinzeraki.zeraki.Requests.InstitutionRequest;
import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.repos.InstitutionRepo;
import com.example.kevinzeraki.zeraki.services.InstitutionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {


    @Autowired
    private final InstitutionService institutionService;
    @Autowired
    InstitutionRepo institutionRepo;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@RequestBody InstitutionRequest request) throws JsonProcessingException {

        String institutionName = String.valueOf(institutionRepo.findByName(request.getName()));
        if (!(institutionName == null)) {
            return institutionService.addInstitution(request);
        } else {
            return "Institution already exists, please select another name";
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Institution>> findInstitutions()  {
        return ResponseEntity.ok(institutionService.listInstitutions());
    }

    @DeleteMapping("/{Id}")
    public String deleteInstitution(@PathVariable Long Id) {
        institutionService.deleteInstitution(Id);
        return "institution deleted successfully!";
    }
}
