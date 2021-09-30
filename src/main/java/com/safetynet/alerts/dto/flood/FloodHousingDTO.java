package com.safetynet.alerts.dto.flood;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class FloodHousingDTO {
	
    private String address;
    private String city;
    private String zip;
    private List<FloodPersonDTO> personsList;

}
