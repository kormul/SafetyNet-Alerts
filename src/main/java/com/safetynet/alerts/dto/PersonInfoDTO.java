package com.safetynet.alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonInfoDTO {
	
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private int age;
    private String email;
    private List<String> medications;
    private List<String> allergies;


}
