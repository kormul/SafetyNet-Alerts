package com.safetynet.alerts.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.MedicalRecord;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicalRecordService {
	
	@Autowired
	private Data data;
	
	@Autowired
	private Validator validator;

	public boolean postMedicalRecordService(MedicalRecord medicalRecord) {
		
		Set<ConstraintViolation<MedicalRecord>> violations = validator.validate(medicalRecord);
		for (ConstraintViolation<MedicalRecord> violation : violations) {
		    log.error(violation.getMessage()); 
		}
		
		if(violations.size() == 0) {
			data.getMedicalrecords().add(medicalRecord);
			return true;
		}
		return false;
	}

	public boolean putMedicalRecordService(MedicalRecord medicalRecord) {
		if(medicalRecord.getFirstName() == null || medicalRecord.getLastName() == null) {
			log.error("the data transmitted does not contain a fistname or lastname");;
			return false;
		}
		
		MedicalRecord medicalRecordToEdit = data.getMedicalRecordWithName(medicalRecord.getFirstName(), medicalRecord.getLastName());
		if(medicalRecordToEdit == null) {
			log.error("Person don't find");;
			return false;
		}
		
		if(medicalRecord.getBirthdate() != null)
			medicalRecordToEdit.setBirthdate(medicalRecord.getBirthdate());
		if(medicalRecord.getMedications() != null)
			medicalRecordToEdit.setMedications(medicalRecord.getMedications());
		if(medicalRecord.getAllergies() != null)
			medicalRecordToEdit.setAllergies(medicalRecord.getAllergies());
		return true;
	}

	public boolean deleteMedicalRecordService(String firstName, String lastName) {
		
		if(firstName == null || lastName == null) {
			log.error("the data transmitted does not contain a fistname or lastname");
			return false;
		}
		
		MedicalRecord medicalRecordToEdit = data.getMedicalRecordWithName(firstName, lastName);
		if(medicalRecordToEdit == null) {
			log.error("medical record don't find");
			return false;
		}
		data.getMedicalrecords().remove(medicalRecordToEdit);
		return true;
	}

}
