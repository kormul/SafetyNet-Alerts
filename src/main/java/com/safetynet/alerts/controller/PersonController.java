package com.safetynet.alerts.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public void postPersonController(@RequestBody Person person, HttpServletResponse response) {
		boolean isCreate = personService.postPersonService(person);

		if(isCreate) {
			log.info("Person created");
			response.setStatus(201);
		}
		else {
			System.out.println("lama");
			log.error("Person don't create"); 
			response.setStatus(400);
		}
	}
	
	@PutMapping("/person")
	public void putPersonController(@RequestBody Person person, HttpServletResponse response){
		boolean isUpdate = personService.putPersonService(person);
		if(isUpdate) {
			log.info("Person created");
			response.setStatus(200);
		}
		else {
			log.error("Person don't find");
			response.setStatus(404);
		}
	}
	
	@DeleteMapping("/person")
	public void deletePersonController(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, HttpServletResponse response) {
		boolean isDelete = personService.deletePersonService(firstName, lastName);
		if(isDelete) {
			log.info("Person created");
			response.setStatus(200);
		}
		else {
			log.error("Person don't find"); 
			response.setStatus(404);
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