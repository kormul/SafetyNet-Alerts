package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MedicalRecordController {

	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@PostMapping("/medicalRecord")
	public ResponseEntity<String> postMedicalRecordController(@RequestBody MedicalRecord medicalRecord) {
		boolean isCreate = medicalRecordService.postMedicalRecordService(medicalRecord);

		if(isCreate) {
			log.info("MedicalRecord created");
			return new ResponseEntity<>("Created!", HttpStatus.CREATED);
		}
		else {
			log.error("MedicalRecord don't create"); 
			return new ResponseEntity<>("Error Created!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/medicalRecord")
	public ResponseEntity<String> putMedicalRecordController(@RequestBody MedicalRecord medicalRecord){
		boolean isUpdate = medicalRecordService.putMedicalRecordService(medicalRecord);
		if(isUpdate) {
			log.info("MedicalRecord updated");
			return new ResponseEntity<>("Modified!", HttpStatus.OK);
		}
		else {
			log.error("MedicalRecord don't find");
			return new ResponseEntity<>("Error modified!", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/medicalRecord")
	public ResponseEntity<String> deleteMedicalRecordController(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName) {
		boolean isDelete = medicalRecordService.deleteMedicalRecordService(firstName, lastName);
		if(isDelete) {
			log.info("MedicalRecord deleted");
			return new ResponseEntity<>("Deleted!", HttpStatus.OK);
		}
		else {
			log.error("MedicalRecord don't find"); 
			return new ResponseEntity<>("Error deleted!", HttpStatus.NOT_FOUND);
		}
	}
}
