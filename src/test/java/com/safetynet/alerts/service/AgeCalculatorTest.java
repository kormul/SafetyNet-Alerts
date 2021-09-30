package com.safetynet.alerts.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgeCalculatorTest {
	
	private LocalDate currentDate;
	private AgeCalculator ageCalculator= new AgeCalculator();;
	
	
	
	@BeforeEach
	public void initialize() {
		currentDate = LocalDate.now();
	}
	
	
	@Test
	public void ChildCalculateAge() {
		LocalDate birthday = currentDate.minusYears(15);
		assertEquals(15, ageCalculator.calculateAge(birthday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
		
	}


}
