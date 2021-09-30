package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@RestController
public class medicalRecordController {

	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@PostMapping("/medicalRecord")
	public void postMedicalRecordController(@RequestBody MedicalRecord medicalRecord) {
		boolean isCreate = medicalRecordService.postMedicalRecordService(medicalRecord);
	}
	
	@PutMapping("/medicalRecord")
	public void putMedicalRecordController(@RequestBody MedicalRecord medicalRecord){
		boolean isUpdate = medicalRecordService.putMedicalRecordService(medicalRecord);
	}
	
	@DeleteMapping("/medicalRecord")
	public void deleteMedicalRecordController(@RequestBody String firstName, @RequestBody String lastName) {
		boolean isDelete = medicalRecordService.deleteMedicalRecordService(firstName, lastName);
	}
}
