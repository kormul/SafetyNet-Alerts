package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.PersonFireStationDTO;
import com.safetynet.alerts.dto.flood.FloodDTO;
import com.safetynet.alerts.dto.flood.FloodHousingDTO;
import com.safetynet.alerts.dto.flood.FloodPersonDTO;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FireStationService {

	@Autowired
	private Data data;
	
	@Autowired
	private Validator validator;
	
	public boolean postfireStationService(FireStation fireStation) {
		
		Set<ConstraintViolation<FireStation>> violations = validator.validate(fireStation);
		for (ConstraintViolation<FireStation> violation : violations) {
		    log.error(violation.getMessage()); 
		}
		
		if(violations.size() == 0) {
			data.getFirestations().add(fireStation);
			return true;
		}
		return false;
	}

	public boolean putfireStationService(FireStation fireStation) {
		
		Set<ConstraintViolation<FireStation>> violations = validator.validate(fireStation);
		for (ConstraintViolation<FireStation> violation : violations) {
		    log.error(violation.getMessage()); 
		}
		if(violations.size() == 0) {
			for(FireStation fireStationToEdit : data.getFirestations()) {
				if(fireStationToEdit.getAddress().equalsIgnoreCase(fireStation.getAddress())) {
						fireStationToEdit.setStation(fireStation.getStation());
						return true;
				}
			}
			log.error("Firsation don't find");
		}
		return false;
	}

	public boolean deletefireStationService(FireStation fireStation) {
		
		boolean isDelete = false;
		if(fireStation == null) {
			return false;
		}
		if(fireStation.getAddress() != null) {
			for(FireStation fireStationToEdit : data.getFirestations()) {
				if(fireStationToEdit.getAddress().equalsIgnoreCase(fireStation.getAddress())) {
						data.getFirestations().remove(fireStationToEdit);
						isDelete = true;
				}
			}
		}
		List<FireStation> fireStationsDelete = new ArrayList<>();
		if(fireStation.getStation() != null){
			for(FireStation fireStationToEdit : data.getFirestations()) {
				if(fireStationToEdit.getStation().equalsIgnoreCase(fireStation.getStation())) {
						fireStationsDelete.add(fireStationToEdit);
						isDelete = true;
				}
			}
		}
		for(FireStation fireStationToEdit : fireStationsDelete) {
			data.getFirestations().remove(fireStationToEdit);
		}
		return isDelete;
	}
	
	public List<FloodDTO> floodService(List<String> stationNumbers){
		log.debug("start floodService");
		AgeCalculator ageCalculator = new AgeCalculator();
		
		List<FloodDTO> floodList = new ArrayList<>();
		
		if(stationNumbers == null) {
			return new ArrayList<>();
		}
		for(String stationNumber : stationNumbers) {
			FloodDTO floodDTO = new FloodDTO(stationNumber,new ArrayList<>());
		
			for(FireStation fireStation : data.getFirestations()) {
				if(stationNumber.equalsIgnoreCase(fireStation.getStation())) {
					FloodHousingDTO floodHousingDTO = new FloodHousingDTO("", "", "", new ArrayList<>());
					for(Person person : data.getPersons()) {
						if(person.getAddress().equalsIgnoreCase(fireStation.getAddress())) {
							if(floodHousingDTO.getCity().equalsIgnoreCase("")) {
								floodHousingDTO.setAddress(person.getAddress());
								floodHousingDTO.setCity(person.getCity());
								floodHousingDTO.setZip(person.getZip());
							}
							MedicalRecord medicalRecord = data.getMedicalRecordWithName(person.getFirstName(), person.getLastName());
							if(medicalRecord != null) {
								FloodPersonDTO floodPersonDTO = new FloodPersonDTO(person.getFirstName(), person.getLastName(), person.getPhone(), ageCalculator.calculateAge(medicalRecord.getBirthdate()), medicalRecord.getMedications(), medicalRecord.getAllergies());
								floodHousingDTO.getPersonsList().add(floodPersonDTO);
							}
						}
					}
					floodDTO.getFloodHousingList().add(floodHousingDTO);
				}
			}
			floodList.add(floodDTO);
		}
		log.debug("end floodService");
		return floodList;
	}
	
	public List<FireDTO> fireService(String address){
		log.debug("start fireService");
		AgeCalculator ageCalculator = new AgeCalculator();

		List<FireDTO> fireDTOs = new ArrayList<>();
		for(Person person : data.getPersons()) {
			if(person.getAddress().equalsIgnoreCase(address)){
				FireDTO fireDTO = new FireDTO("", person.getFirstName(), person.getLastName(), 0, person.getPhone(), new ArrayList<>(), new ArrayList<>());
				
				for(FireStation fireStation : data.getFirestations()) {
					if(fireStation.getAddress().equalsIgnoreCase(address)) {
						fireDTO.setStationNumber(fireStation.getStation());
						break;
					}
				}
				for(MedicalRecord medicalRecord : data.getMedicalrecords()) {
					if(medicalRecord.getFirstName().equalsIgnoreCase(fireDTO.getFirstName()) && medicalRecord.getLastName().equalsIgnoreCase(fireDTO.getLastName())) {
						fireDTO.setAge(ageCalculator.calculateAge(medicalRecord.getBirthdate()));
						fireDTO.setMedications(medicalRecord.getMedications());
						fireDTO.setAllergies(medicalRecord.getAllergies());
						break;
					}
				}
				fireDTOs.add(fireDTO);
			}
		}
		log.debug("end fireService");
		return fireDTOs;
		
	}
	
	public FirestationDTO firestationService(String stationNumber){
		log.debug("start firestationService");
		AgeCalculator ageCalculator = new AgeCalculator();
		FirestationDTO firestation = new FirestationDTO(new ArrayList<>(), 0, 0);
		
		for(Person person : data.getPersons()) {
			if(data.getFirestationsWithAddress(person.getAddress()).equalsIgnoreCase(stationNumber)) {
				firestation.getPersons().add(new PersonFireStationDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()));
				if(ageCalculator.calculateAge(data.getBirthdayWithname(person.getFirstName(), person.getLastName()))<18) {
					firestation.setTotalChildrenNumber(firestation.getTotalChildrenNumber() +1);
				}
				else {
					firestation.setTotalAdultsNumber(firestation.getTotalAdultsNumber() +1);
				}
			}
		}
		log.debug("end firestationService");
		return firestation;
	}

	public List<String> phoneAlertService(String stationNumber){
		log.debug("start phoneAlertService");
		List<String> phoneNumber = new ArrayList<>();
		
		for(Person person : data.getPersons()) {
			if(data.getFirestationsWithAddress(person.getAddress()).equalsIgnoreCase(stationNumber)) {
				phoneNumber.add(person.getPhone());
			}
		}
		log.debug("end phoneAlertService");
		return phoneNumber;
	}
}