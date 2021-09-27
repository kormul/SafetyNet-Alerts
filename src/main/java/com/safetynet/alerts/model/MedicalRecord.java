package com.safetynet.alerts.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicalRecord {

	@NotNull(message = "MedicalRecord : firstName is null")
	private String firstName;
	
	@NotNull(message = "MedicalRecord : lastName is null")
	private String lastName;
	
	@NotNull(message = "MedicalRecord : birthdate is null")
	private String birthdate;
	
	@NotNull(message = "MedicalRecord : medications is null")
	private List<String> medications;
	
	@NotNull(message = "MedicalRecord : allergies is null")
	private List<String> allergies;
	
}
