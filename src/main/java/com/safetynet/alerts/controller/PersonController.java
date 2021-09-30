package com.safetynet.alerts.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void postPersonController(@RequestBody Person person, HttpServletResponse response) {
		boolean isCreate = personService.postPersonService(person);

		if(isCreate) {
			log.info("Person created");
			response.setStatus(201);
		}
		else {
			log.error("Person don't create"); 
			response.setStatus(404);// TODO
		}
	}
	
	@PutMapping("/person")
	public void putPersonController(@RequestBody Person person){
		boolean isUpdate = personService.putPersonService(person);
	}
	
	@DeleteMapping("/person")
	public void deletePersonController(@RequestBody String firstName, @RequestBody String lastName) {
		boolean isDelete = personService.deletePersonService(firstName, lastName);
	}
	
    @GetMapping("/personInfo")
    public List<PersonInfoDTO> personInfoController(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName) {

        List<PersonInfoDTO> personInfos = personService.personInfoService(firstName, lastName);

        return personInfos;
    }
    
	
	@GetMapping("/childAlert")
	public List<ChildAlertDTO> childAlertController(@RequestParam(value = "address") String address){
		
		List<ChildAlertDTO> childAlertDTOs = personService.childAlertService(address);
		
		return childAlertDTOs;
		
	}
	
	@GetMapping("/communityEmail")
	public List<String> communityEmailController(@RequestParam(value = "city") String city){
		
		List<String> childAlertDTOs = personService.communityEmailService(city);
		
		return childAlertDTOs;
		
	}
	
	
}