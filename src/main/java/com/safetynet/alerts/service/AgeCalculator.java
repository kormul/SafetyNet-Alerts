package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgeCalculator {
	
	
	public int calculateAge(final String birthday) {
		log.debug("start AgeCalculator");
		try{
			LocalDate birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			LocalDate currentDate = LocalDate.now();
			if(birthDate.isAfter(currentDate)) {
				log.error("Birthday after the current date");
				throw new IllegalArgumentException("BirthDay Date format");
			}
			log.debug("end AgeCalculator");
		    return Period.between(birthDate, currentDate).getYears();
		}catch(Exception e) {
			log.error("Person format date invalid");
			throw new IllegalArgumentException("BirthDay Date format");
		}
	}
}
