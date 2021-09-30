package com.safetynet.alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FirestationDTO {
	
	private List<PersonFireStationDTO> Persons;
    private int totalAdultsNumber;
    private int totalChildrenNumber;
    
}
