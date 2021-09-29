package com.safetynet.alerts.model;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class Data {
	
	private List<Person> persons;
	private List<FireStation>firestations;
	private List<MedicalRecord> medicalrecords;

	public Data() {
		this.persons = new ArrayList<>();
		this.firestations = new ArrayList<>();
		this.medicalrecords = new ArrayList<>();
	}
	
	public Person getPersonWithName(String firstName, String lastName) {
		
		for(Person person : persons) {
			if(person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName))
				return person;
		}
		return null;
		
	}
	
	public String getFirestationsWithAddress(String Address) {
		for(FireStation firestation : firestations) {
			if(Address.equalsIgnoreCase(firestation.getAddress()))
				return firestation.getStation();
		}
		return "";
	}
	
	public String getBirthdayWithname(String firstName, String lastName) {
		
		for(MedicalRecord medicalRecord : medicalrecords) {
			if(firstName.equalsIgnoreCase(medicalRecord.getFirstName())) {
				if(lastName.equalsIgnoreCase(medicalRecord.getLastName())) {
					return medicalRecord.getBirthdate();
				}
			}
		}
		return "";
	}
	
	public MedicalRecord getMedicalRecordWithName(String firstName, String lastName) {
		for(MedicalRecord medicalRecord : medicalrecords) {
			if(medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
				return medicalRecord;
			}
		}
		return null;
	}

}
