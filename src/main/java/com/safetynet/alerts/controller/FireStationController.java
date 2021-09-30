package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.flood.FloodDTO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FireStationService;


@RestController
public class FireStationController {

	@Autowired
	private FireStationService fireStationService;
	
	@PostMapping("/firestation")
	public void postfireStationController(@RequestBody FireStation fireStation) {
		boolean isCreate = fireStationService.postfireStationService(fireStation);
	}
	
	@PutMapping("/firestation")
	public void putfireStationController(@RequestBody FireStation fireStation){
		boolean isUpdate = fireStationService.putfireStationService(fireStation);
	}
	
	@DeleteMapping("/firestation")
	public void deletefireStationController(@RequestBody FireStation fireStation) {
		boolean isDelete = fireStationService.deletefireStationService(fireStation);
	}
	
	@GetMapping("/flood/stations")
	public List<FloodDTO> floodController(@RequestParam(value = "stations") List<String> stations) {

		List<FloodDTO> floodDTO = fireStationService.floodService(stations);
		
		return floodDTO;
		
	}
	
	@GetMapping("/fire")
	public List<FireDTO> fireAddressControler(@RequestParam(value = "address") String address) {

		List<FireDTO> fireDTOs = fireStationService.fireService(address);
		
		return fireDTOs;
		
	}
	
	@GetMapping("/firestation")
	public FirestationDTO firestationWithNumber(@RequestParam(value = "stationNumber") String stationNumber) {

		FirestationDTO fireStationByStationNumberDTO = fireStationService.firestationService(stationNumber);
		
		return fireStationByStationNumberDTO;
		
	}
	
	@GetMapping("/phoneAlert")
	public List<String> phoneAlert(@RequestParam(value = "firestation") String stationNumber) {

		List<String> phoneList = fireStationService.phoneAlertService(stationNumber);
		return phoneList;
		
	}
}
