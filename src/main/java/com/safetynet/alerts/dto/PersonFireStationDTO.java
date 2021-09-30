package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonFireStationDTO {
	
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

}
