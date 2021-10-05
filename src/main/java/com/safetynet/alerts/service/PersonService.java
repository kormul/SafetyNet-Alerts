package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonService {

	@Autowired
	private Data data;
	
	@Autowired
	private Validator validator;
	
	public boolean postPersonService(Person person) { 
		Set<ConstraintViolation<Person>> violations = validator.validate(person);
		for (ConstraintViolation<Person> violation : violations) {
		    log.error(violation.getMessage()); 
		}
		if(violations.size() == 0) {
			data.getPersons().add(person);
			return true;
		}
		
		return false;
	}
	
	public boolean putPersonService(Person person) {
		if(person.getFirstName() == null || person.getLastName() == null) {
			log.error("the data transmitted does not contain a fistname or lastname");;
			return false;
		}
		
		Person personToEdit = data.getPersonWithName(person.getFirstName(), person.getLastName());
		if(personToEdit == null) {
			log.error("Person don't find");;
			return false;
		}
		
		if(person.getAddress() != null)
			personToEdit.setAddress(person.getAddress());
		if(person.getCity() != null)
			personToEdit.setCity(person.getCity());
		if(person.getZip() != null)
			personToEdit.setZip(person.getZip());
		if(person.getPhone() != null)
			personToEdit.setPhone(person.getPhone());
		if(person.getEmail() != null)
			personToEdit.setEmail(person.getEmail());
		return true;
	}

	public boolean deletePersonService(String firstName, String lastName) {

		if(firstName == null || lastName == null) {
			log.error("the data transmitted does not contain a fistname or lastname");;
			return false;
		}
		
		Person personToEdit = data.getPersonWithName(firstName, lastName);
		if(personToEdit == null) {
			log.error("Person don't find");;
			return false;
		}
		
		data.getPersons().remove(personToEdit);
		
		return true;
	}
	
	public List<PersonInfoDTO> personInfoService(String firstName, String lastName){
		log.debug("start personInfoService");
		AgeCalculator ageCalculator = new AgeCalculator();
        List<PersonInfoDTO> personsInfosList = new ArrayList<>();
        for(Person person : data.getPersons()) {
        	if(person.getFirstName().equalsIgnoreCase(firstName)&& person.getLastName().equalsIgnoreCase(lastName)) {
        		MedicalRecord medicalRecord = data.getMedicalRecordWithName(firstName, lastName);
        		personsInfosList.add(new PersonInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(), person.getZip(), ageCalculator.calculateAge(medicalRecord.getBirthdate()), person.getEmail(), medicalRecord.getMedications(), medicalRecord.getAllergies()));
        	}
        }
		log.debug("end personInfoService");
		return personsInfosList;
	}
	
	public List<ChildAlertDTO> childAlertService(String address){
		log.debug("start childAlertService");
		AgeCalculator ageCalculator = new AgeCalculator();
		List<ChildAlertDTO> childAlertDTO = new ArrayList<ChildAlertDTO>();
		ArrayList<String> addressWithChildren = new ArrayList<>();
		
		for(MedicalRecord medicalRecord : data.getMedicalrecords()) {
			if(ageCalculator.calculateAge(medicalRecord.getBirthdate())<18) {
				for(Person person : data.getPersons()) {
					if(person.getFirstName().equalsIgnoreCase(medicalRecord.getFirstName()) && person.getLastName().equalsIgnoreCase(medicalRecord.getLastName()))
						addressWithChildren.add(person.getAddress());
				}
			}
		}
		
		for(Person person : data.getPersons()) {
			if(addressWithChildren.contains(person.getAddress() )&& person.getAddress().equalsIgnoreCase(address)) {
				childAlertDTO.add(new ChildAlertDTO(person.getFirstName(), person.getLastName(), ageCalculator.calculateAge((data.getBirthdayWithname(person.getFirstName(), person.getLastName())))));
			}
		}
		log.debug("end childAlertService");
		return childAlertDTO;
	}

	public List<String> communityEmailService(String City){
		log.debug("start communityEmailService");
		List<String> email = new ArrayList<>();
		
		for(Person person : data.getPersons()) {
			if(person.getCity().equalsIgnoreCase(City)) {
				email.add(person.getEmail());
			}
		}
		log.debug("end communityEmailService");
		return email;
	}
}
