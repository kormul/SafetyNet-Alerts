package com.safetynet.alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FireDTO {
	
    private String stationNumber;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private List<String> medications;
    private List<String> allergies;

}
