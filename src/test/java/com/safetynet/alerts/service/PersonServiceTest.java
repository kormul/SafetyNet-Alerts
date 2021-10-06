package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@InjectMocks
	private PersonService personService;
	
	@Mock
	private Data data;
	
	@Mock
	private Validator validator;
	
	@Autowired
	private Validator validator2;
	
	@BeforeEach
	public void setupData() {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("Guillaume", "Aubert", "4 la ruelle", "Noisy-Rudignon", "77940", "0669120050", "Aubert2@gmx.fr"));
		persons.add(new Person("George", "Aubert", "4 la ruelle", "Noisy-Rudignon", "77940", "0695123165", "George4@gmx.fr"));
		persons.add(new Person("Michel", "Aubert", "4 la ruelle", "Noisy-Rudignon", "77940", "0653215894", "Michel9@gmx.fr"));
		persons.add(new Person("Margaux", "Ducouret", "8 rue grande", "Ville st Jacques", "77130", "0656513518", "Ducouret26@gmx.fr"));
		persons.add(new Person("Amandine", "Louvre", "4 avenue des champs élysée", "Paris", "75000", "0698465132", "Louvre4@gmx.fr"));
		
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords.add(new MedicalRecord("Guillaume", "Aubert", "01/13/2001",new ArrayList<>(),new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("George", "Aubert", "09/15/2015",new ArrayList<>(),new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Michel", "Aubert", "12/18/2008",new ArrayList<>(),new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Margaux", "Ducouret", "07/21/1995",new ArrayList<>(),new ArrayList<>()));
		medicalRecords.add(new MedicalRecord("Amandine", "Louvre", "02/20/1968",new ArrayList<>(),new ArrayList<>()));
		
		lenient().when(data.getPersons()).thenReturn(persons);
		lenient().when(data.getMedicalrecords()).thenReturn(medicalRecords);
		for(MedicalRecord medicalRecord : medicalRecords) {
			lenient().when(data.getMedicalRecordWithName(medicalRecord.getFirstName(), medicalRecord.getLastName())).thenReturn(medicalRecord);
			lenient().when(data.getBirthdayWithname(medicalRecord.getFirstName(), medicalRecord.getLastName())).thenReturn(medicalRecord.getBirthdate());
		}
		for(Person person : persons) {
			lenient().when(data.getPersonWithName(person.getFirstName(), person.getLastName())).thenReturn(person);
		}
		
	}
	
	@Test
	public void postPersonTest() {
		boolean res = personService.postPersonService(new Person("Christophe", "Alvis", "3 rue Grande", "Dormelles", "77940", "0645655425", "Christophe4@yahoo.fr"));
		assertTrue(res);
		assertThat(this.data.getPersons().size() == 6);
		assertThat(this.data.getPersons().get(4).getFirstName().equalsIgnoreCase("Christophe"));
		assertThat(this.data.getPersons().get(4).getLastName().equalsIgnoreCase("Alvis"));
		assertThat(this.data.getPersons().get(4).getAddress().equalsIgnoreCase("3 rue Grande"));
		assertThat(this.data.getPersons().get(4).getCity().equalsIgnoreCase("Dormelles"));
		assertThat(this.data.getPersons().get(4).getZip().equalsIgnoreCase("77940"));
		assertThat(this.data.getPersons().get(4).getPhone().equalsIgnoreCase("0645655425"));
		assertThat(this.data.getPersons().get(4).getEmail().equalsIgnoreCase("Christophe4@yahoo.fr"));
	}
	
	@Test
	public void postPersonTestWithValueNull() {
		Person person = new Person(null, null,null,null,null,null,null);
		lenient().when(validator.validate(person)).thenReturn(validator2.validate(person));
		boolean res = personService.postPersonService(person);
		assertFalse(res);
		assertThat(this.data.getPersons().size() == 5);
		assertThat(this.data.getPersons().get(0).getFirstName().equalsIgnoreCase("Guillaume"));
		assertThat(this.data.getPersons().get(1).getFirstName().equalsIgnoreCase("George"));
		assertThat(this.data.getPersons().get(2).getFirstName().equalsIgnoreCase("Michel"));
		assertThat(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
		assertThat(this.data.getPersons().get(4).getFirstName().equalsIgnoreCase("Amandine"));
	}
	
	@Test
	public void putPersonTestWithPersonDontFind() {
		boolean res = personService.putPersonService(new Person("Margaux", "Lassalle", null, null, null, null, null));
		assertFalse(res);
		assertThat(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
		assertThat(this.data.getPersons().get(3).getLastName().equalsIgnoreCase("Ducouret"));
		assertThat(this.data.getPersons().get(3).getAddress().equalsIgnoreCase("8 rue grande"));
		assertThat(this.data.getPersons().get(3).getCity().equalsIgnoreCase("Ville st Jacques"));
		assertThat(this.data.getPersons().get(3).getZip().equalsIgnoreCase("77130"));
		assertThat(this.data.getPersons().get(3).getPhone().equalsIgnoreCase("0656513518"));
		assertThat(this.data.getPersons().get(3).getEmail().equalsIgnoreCase("Ducouret26@gmx.fr"));
	}
	
	@Test
	public void putPersonTestWithNameNull() {
		boolean res = personService.putPersonService(new Person(null, null, null, null, null, null, null));
		assertFalse(res);
		assertThat(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
		assertThat(this.data.getPersons().get(3).getLastName().equalsIgnoreCase("Ducouret"));
		assertThat(this.data.getPersons().get(3).getAddress().equalsIgnoreCase("8 rue grande"));
		assertThat(this.data.getPersons().get(3).getCity().equalsIgnoreCase("Ville st Jacques"));
		assertThat(this.data.getPersons().get(3).getZip().equalsIgnoreCase("77130"));
		assertThat(this.data.getPersons().get(3).getPhone().equalsIgnoreCase("0656513518"));
		assertThat(this.data.getPersons().get(3).getEmail().equalsIgnoreCase("Ducouret26@gmx.fr"));
	}
	
	@Test
	public void putPersonTestWithNoModification() {
		boolean res = personService.putPersonService(new Person("Margaux", "Ducouret", null, null, null, null, null));
		assertTrue(res);
		assertThat(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
		assertThat(this.data.getPersons().get(3).getLastName().equalsIgnoreCase("Ducouret"));
		assertThat(this.data.getPersons().get(3).getAddress().equalsIgnoreCase("8 rue grande"));
		assertThat(this.data.getPersons().get(3).getCity().equalsIgnoreCase("Ville st Jacques"));
		assertThat(this.data.getPersons().get(3).getZip().equalsIgnoreCase("77130"));
		assertThat(this.data.getPersons().get(3).getPhone().equalsIgnoreCase("0656513518"));
		assertThat(this.data.getPersons().get(3).getEmail().equalsIgnoreCase("Ducouret26@gmx.fr"));
	}
	
	@Test
	public void putPersonTest() {
		boolean res = personService.putPersonService(new Person("Margaux", "Ducouret", "4 la ruelle", "Noisy-Rudignon", "77940", "0945052856", "Ducouret26@gmail.com"));
		assertTrue(res);
		assertTrue(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
		assertTrue(this.data.getPersons().get(3).getLastName().equalsIgnoreCase("Ducouret"));
		assertTrue(this.data.getPersons().get(3).getAddress().equalsIgnoreCase("4 la ruelle"));
		assertTrue(this.data.getPersons().get(3).getCity().equalsIgnoreCase("Noisy-Rudignon"));
		assertTrue(this.data.getPersons().get(3).getZip().equalsIgnoreCase("77940"));
		assertTrue(this.data.getPersons().get(3).getPhone().equalsIgnoreCase("0945052856"));
		assertTrue(this.data.getPersons().get(3).getEmail().equalsIgnoreCase("Ducouret26@gmail.com"));
	}
	
	@Test
	public void deletePersonTestDataNotTransmitted() {
		boolean res = personService.deletePersonService(null, null);
		assertFalse(res);
		assertTrue(this.data.getPersons().size() == 5);
		assertTrue(this.data.getPersons().get(0).getFirstName().equalsIgnoreCase("Guillaume"));
		assertTrue(this.data.getPersons().get(1).getFirstName().equalsIgnoreCase("George"));
		assertTrue(this.data.getPersons().get(2).getFirstName().equalsIgnoreCase("Michel"));
		assertTrue(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
		assertTrue(this.data.getPersons().get(4).getFirstName().equalsIgnoreCase("Amandine"));
	}
	
	@Test
	public void deletePersonTestPersonNotFind() {
		boolean res = personService.deletePersonService("Guillaume", "Louvre");
		assertFalse(res);
		assertTrue(this.data.getPersons().size() == 5);
		assertTrue(this.data.getPersons().get(0).getFirstName().equalsIgnoreCase("Guillaume"));
		assertTrue(this.data.getPersons().get(1).getFirstName().equalsIgnoreCase("George"));
		assertTrue(this.data.getPersons().get(2).getFirstName().equalsIgnoreCase("Michel"));
		assertTrue(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
		assertTrue(this.data.getPersons().get(4).getFirstName().equalsIgnoreCase("Amandine"));
	
	}
	
	@Test
	public void deletePersonTest() {
		boolean res = personService.deletePersonService("Amandine", "Louvre");
		assertTrue(res);
		assertTrue(this.data.getPersons().size() == 4);
		assertThat(this.data.getPersons().get(0).getFirstName().equalsIgnoreCase("Guillaume"));
		assertThat(this.data.getPersons().get(1).getFirstName().equalsIgnoreCase("George"));
		assertThat(this.data.getPersons().get(2).getFirstName().equalsIgnoreCase("Michel"));
		assertThat(this.data.getPersons().get(3).getFirstName().equalsIgnoreCase("Margaux"));
	
	}
	
	@Test
	public void personInfoTestWithMedicationAndAllergies() {
		this.data.getMedicalrecords().get(2).getAllergies().add("Gluten");
		this.data.getMedicalrecords().get(2).getMedications().add("Doliprane");
		List<PersonInfoDTO> res = personService.personInfoService("Michel", "Aubert");
		assertTrue(res.size() == 1);
		assertThat(res.get(0).getFirstName()).isEqualTo("Michel");
		assertThat(res.get(0).getLastName()).isEqualTo("Aubert");
		assertThat(res.get(0).getAddress()).isEqualTo("4 la ruelle");
		assertThat(res.get(0).getCity()).isEqualTo("Noisy-Rudignon");
		assertThat(res.get(0).getZip()).isEqualTo("77940");
		assertThat(res.get(0).getMedications().get(0)).isEqualTo("Doliprane");
		assertThat(res.get(0).getAllergies().get(0)).isEqualTo("Gluten");
	}
	
	@Test
	public void personInfoTestWithPersonNotFound() {
		List<PersonInfoDTO> res = personService.personInfoService("Michel", "Lassalle");
		assertTrue(res.isEmpty());
		assertTrue(res.size() == 0);
	}
	@Test
	public void personInfoTestNoMedicationAndNoAllergies() {
		List<PersonInfoDTO> res = personService.personInfoService("Michel", "Aubert");
		assertTrue(res.size() == 1);
		assertThat(res.get(0).getFirstName()).isEqualTo("Michel");
		assertThat(res.get(0).getLastName()).isEqualTo("Aubert");
		assertThat(res.get(0).getAddress()).isEqualTo("4 la ruelle");
		assertThat(res.get(0).getCity()).isEqualTo("Noisy-Rudignon");
		assertThat(res.get(0).getZip()).isEqualTo("77940");
	}
	
	@Test
	public void childAlertTestTwoChild() {
		List<ChildAlertDTO> res = personService.childAlertService("4 la ruelle");
		assertTrue(!res.isEmpty());
		assertTrue(res.size() == 3);
		assertThat(res.get(0).getFirstName()).isEqualTo("Guillaume");
		assertThat(res.get(0).getLastName()).isEqualTo("Aubert");
		assertThat(res.get(1).getFirstName()).isEqualTo("George");
		assertThat(res.get(1).getLastName()).isEqualTo("Aubert");
		assertThat(res.get(2).getFirstName()).isEqualTo("Michel");
		assertThat(res.get(2).getLastName()).isEqualTo("Aubert");

	}
	
	@Test
	public void childAlertTestNobodyChildWithAnRegisterAddress() {
		List<ChildAlertDTO> res = personService.childAlertService("5 la ruelle");
		assertTrue(res.isEmpty());
		assertTrue(res.size() == 0);
	}
	
	@Test
	public void childAlertTestNobodyChildWithAnUnknowAddress() {
		List<ChildAlertDTO> res = personService.childAlertService("Melun");
		assertTrue(res.isEmpty());
		assertTrue(res.size() == 0);
	}
	
	
	@Test
	public void communityEmailTestOnePerson() {
		List<String> res = personService.communityEmailService("Paris");
		assertTrue(!res.isEmpty());
		assertTrue(res.size() == 1);
		assertThat(res.get(0)).isEqualTo("Louvre4@gmx.fr");

	}
	
	@Test
	public void communityEmailTestThreePerson() {
		List<String> res = personService.communityEmailService("Noisy-Rudignon");
		assertTrue(!res.isEmpty());
		assertTrue(res.size() == 3);
		assertThat(res.get(0)).isEqualTo("Aubert2@gmx.fr");
		assertThat(res.get(1)).isEqualTo("George4@gmx.fr");
		assertThat(res.get(2)).isEqualTo("Michel9@gmx.fr");

	}
	
	@Test
	public void communityEmailTestnobodyPersonWithAnUnknowCity() {
		List<String> res = personService.communityEmailService("Ecuelles");
		assertTrue(res.isEmpty());
		assertTrue(res.size() == 0);
	}

}
