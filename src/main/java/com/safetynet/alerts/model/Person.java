package com.safetynet.alerts.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	
	@NotNull(message = "Person : Name is null")
	private String firstName;
	
	@NotNull(message = "Person : lastName is null")
	private String lastName;
	
	@NotNull(message = "Person : address is null")
	private String address;
	
	@NotNull(message = "Person : city is null")
	private String city;
	
	@NotNull(message = "Person : zip is null")
	private String zip;
	
	@NotNull(message = "Person : phone is null")
	private String phone;
	
	@NotNull(message = "Person : email is null")
	private String email;

}
