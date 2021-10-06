package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class AgeCalculatorServiceTest {
	
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
	
	@Test
	public void ChildCalculateAgeDateAfterCurrentDate() {
		LocalDate birthday = currentDate.plusYears(15);
		assertThatIllegalArgumentException().isThrownBy(() -> {
			ageCalculator.calculateAge(birthday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        });	
		
	}


}
