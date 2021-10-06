package com.safetynet.alerts.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.safetynet.alerts.service.FireStationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FireStationController {

	@Autowired
	private FireStationService fireStationService;
	
	@PostMapping("/firestation")
	public ResponseEntity<String> postfireStationController(@RequestBody FireStation fireStation) {
		boolean isCreate = fireStationService.postfireStationService(fireStation);
		if(isCreate) {
			log.info("FireStation created");
			return new ResponseEntity<>("Created!", HttpStatus.CREATED);
		}
		else {
			log.error("FireStation don't create"); 
			return new ResponseEntity<>("Error Created!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/firestation")
	public ResponseEntity<String> putfireStationController(@RequestBody FireStation fireStation){
		boolean isUpdate = fireStationService.putfireStationService(fireStation);
		if(isUpdate) {
			log.info("FireStation updated");
			return new ResponseEntity<>("Modified!", HttpStatus.OK);
		}
		else {
			log.error("FireStation don't find");
			return new ResponseEntity<>("Error modified!", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/firestation")
	public ResponseEntity<String> deletefireStationController(@RequestBody FireStation fireStation) {
		boolean isDelete = fireStationService.deletefireStationService(fireStation);
		if(isDelete) {
			log.info("FireStation deleted");
			return new ResponseEntity<>("Deleted!", HttpStatus.OK);
		}
		else {
			log.error("FireStation don't find"); 
			return new ResponseEntity<>("Error deleted!", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/flood/stations")
	public List<FloodDTO> floodController(@RequestParam(value = "stations") List<String> stations, HttpServletResponse response) {

		List<FloodDTO> floodDTO = fireStationService.floodService(stations);
		
		if(floodDTO.isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		return floodDTO;
		
	}
	
	@GetMapping("/fire")
	public List<FireDTO> fireAddressControler(@RequestParam(value = "address") String address, HttpServletResponse response) {

		List<FireDTO> fireDTOs = fireStationService.fireService(address);
		
		if(fireDTOs.isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		
		return fireDTOs;
		
	}
	
	@GetMapping("/firestation")
	public FirestationDTO firestationWithNumber(@RequestParam(value = "stationNumber") String stationNumber, HttpServletResponse response) {

		FirestationDTO fireStationByStationNumberDTO = fireStationService.firestationService(stationNumber);
		
		if(fireStationByStationNumberDTO.getPersons().isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		
		return fireStationByStationNumberDTO;
		
	}
	
	@GetMapping("/phoneAlert")
	public List<String> phoneAlert(@RequestParam(value = "firestation") String stationNumber, HttpServletResponse response) {

		List<String> phoneList = fireStationService.phoneAlertService(stationNumber);
		
		if(phoneList.isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		
		return phoneList;
		
	}
}
