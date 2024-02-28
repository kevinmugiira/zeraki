package com.example.kevinzeraki.zeraki.controllers;


import com.example.kevinzeraki.zeraki.Requests.InstitutionRequest;
import com.example.kevinzeraki.zeraki.models.Course;
import com.example.kevinzeraki.zeraki.models.Institution;
import com.example.kevinzeraki.zeraki.repos.InstitutionRepo;
import com.example.kevinzeraki.zeraki.services.InstitutionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editInstitutionName(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
            String newName = requestBody.get("newName");
            institutionService.editInstitutionName(id, newName);
            return ResponseEntity.ok("Institution name edited successfully");
        } catch(NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while editing the institution's name");
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Institution>> getSortedCourses(@RequestParam("order") String order) {
        try {
            List<Institution> sortedCourses = institutionService.getSourtedCourses(order);
            return ResponseEntity.ok(sortedCourses);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable Long id) {
        try {
            institutionService.deleteInstitution(id);
            return ResponseEntity.ok("Institution deleted successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the institution ");
        }
    }
}
