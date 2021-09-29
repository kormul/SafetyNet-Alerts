package com.safetynet.alerts.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStation {
	
	@NotNull(message = "FireStation : address is null")
	private String address;
	
	@NotNull(message = "FireStation : station is null")
	private String station;


}
