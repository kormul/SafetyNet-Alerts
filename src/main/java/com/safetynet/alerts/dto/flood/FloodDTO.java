package com.safetynet.alerts.dto.flood;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FloodDTO {
	
	private String station;
	private List<FloodHousingDTO> floodHousingList;

}
