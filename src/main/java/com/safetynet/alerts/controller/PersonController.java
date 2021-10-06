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

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@PostMapping("/person")
	public ResponseEntity<String>  postPersonController(@RequestBody Person person) {
		boolean isCreate = personService.postPersonService(person);

		if(isCreate) {
			log.info("Person created");
			return new ResponseEntity<>("Created!", HttpStatus.CREATED);
		}
		else {
			System.out.println("lama");
			log.error("Person don't create"); 
			return new ResponseEntity<>("Error Created!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/person")
	public ResponseEntity<String> putPersonController(@RequestBody Person person){
		boolean isUpdate = personService.putPersonService(person);
		if(isUpdate) {
			log.info("Person updated");
			return new ResponseEntity<>("Modified!", HttpStatus.OK);
		}
		else {
			log.error("Person don't find");
			return new ResponseEntity<>("Error modified!", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/person")
	public ResponseEntity<String> deletePersonController(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName) {
		boolean isDelete = personService.deletePersonService(firstName, lastName);
		if(isDelete) {
			log.info("Person deleted");
			return new ResponseEntity<>("Deleted!", HttpStatus.OK);
		}
		else {
			log.error("Person don't find"); 
			return new ResponseEntity<>("Error deleted!", HttpStatus.NOT_FOUND);
		}
	}
	
    @GetMapping("/personInfo")
    public List<PersonInfoDTO> personInfoController(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, HttpServletResponse response) {

        List<PersonInfoDTO> personInfos = personService.personInfoService(firstName, lastName);
        
		if(personInfos.isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		

        return personInfos;
    }
    
	
	@GetMapping("/childAlert")
	public List<ChildAlertDTO> childAlertController(@RequestParam(value = "address") String address, HttpServletResponse response){
		
		List<ChildAlertDTO> childAlertDTOs = personService.childAlertService(address);
		
		if(childAlertDTOs.isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		
		return childAlertDTOs;
		
	}
	
	@GetMapping("/communityEmail")
	public List<String> communityEmailController(@RequestParam(value = "city") String city, HttpServletResponse response){
		
		List<String> communityEmailList = personService.communityEmailService(city);
		if(communityEmailList.isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		return communityEmailList;
		
	}
	
	
}