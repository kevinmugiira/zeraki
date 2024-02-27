package com.example.kevinzeraki.zeraki.Requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class InstitutionRequest {

    private String name;
    private int departments;
    private String address;
    private String email;
    private String phoneNumber;
}
