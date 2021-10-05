package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.dto.flood.FloodDTO;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

@SpringBootTest(classes = {DataLoader.class})
@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
	
	@InjectMocks
	private FireStationService fireStationService;
	
	@Mock
	private Data data;

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
		
		List<FireStation> fireStations = new ArrayList<>();
		fireStations.add(new FireStation("4 la ruelle","1"));
		fireStations.add(new FireStation("8 rue grande","1"));
		fireStations.add(new FireStation("4 avenue des champs élysée","2"));
		
		lenient().when(data.getPersons()).thenReturn(persons);
		lenient().when(data.getMedicalrecords()).thenReturn(medicalRecords);
		lenient().when(data.getFirestations()).thenReturn(fireStations);

		for(MedicalRecord medicalRecord : medicalRecords) {
			lenient().when(data.getMedicalRecordWithName(medicalRecord.getFirstName(), medicalRecord.getLastName())).thenReturn(medicalRecord);
			lenient().when(data.getBirthdayWithname(medicalRecord.getFirstName(), medicalRecord.getLastName())).thenReturn(medicalRecord.getBirthdate());
		}
		for(Person person : persons) {
			lenient().when(data.getPersonWithName(person.getFirstName(), person.getLastName())).thenReturn(person);
		}
		for(FireStation fireStation : fireStations) {
			lenient().when(data.getFirestationsWithAddress(fireStation.getAddress())).thenReturn(fireStation.getStation());
		}
	} 
	
	/*@Test
	public void putFirestationTest() {
		boolean res = fireStationService.putfireStationService(new FireStation("2","4 la ruelle"));
		assertFalse(res);
		assertTrue(this.data.getFirestations().size() == 3);		
		assertThat(this.data.getFirestations().get(0).getStation()).isEqualTo("2");
		assertThat(this.data.getFirestations().get(0).getAddress()).isEqualTo("4 la ruelle");
		assertThat(this.data.getFirestations().get(1).getStation()).isEqualTo("1");
		assertThat(this.data.getFirestations().get(1).getAddress()).isEqualTo("8 rue grande");
		assertThat(this.data.getFirestations().get(2).getStation()).isEqualTo("2");
		assertThat(this.data.getFirestations().get(2).getAddress()).isEqualTo("4 avenue des champs élysée");
	}*/
	
	
	@Test
	public void deleteFirestationTestDataNotTransmitted() {
		boolean res = fireStationService.deletefireStationService(null);
		assertFalse(res);
		assertTrue(this.data.getFirestations().size() == 3);		
		assertThat(this.data.getFirestations().get(0).getStation()).isEqualTo("1");
		assertThat(this.data.getFirestations().get(0).getAddress()).isEqualTo("4 la ruelle");
		assertThat(this.data.getFirestations().get(1).getStation()).isEqualTo("1");
		assertThat(this.data.getFirestations().get(1).getAddress()).isEqualTo("8 rue grande");
		assertThat(this.data.getFirestations().get(2).getStation()).isEqualTo("2");
		assertThat(this.data.getFirestations().get(2).getAddress()).isEqualTo("4 avenue des champs élysée");
	}
	
	@Test
	public void deleteFirestationTestFirestationNotFind() {
		boolean res = fireStationService.deletefireStationService(new FireStation("8 la ruelle","4"));
		assertFalse(res);
		assertTrue(this.data.getFirestations().size() == 3);		
		assertThat(this.data.getFirestations().get(0).getStation()).isEqualTo("1");
		assertThat(this.data.getFirestations().get(0).getAddress()).isEqualTo("4 la ruelle");
		assertThat(this.data.getFirestations().get(1).getStation()).isEqualTo("1");
		assertThat(this.data.getFirestations().get(1).getAddress()).isEqualTo("8 rue grande");
		assertThat(this.data.getFirestations().get(2).getStation()).isEqualTo("2");
		assertThat(this.data.getFirestations().get(2).getAddress()).isEqualTo("4 avenue des champs élysée");
	
	}
	
	@Test
	public void deleteFirestationTestWithStationNumber() {
		boolean res = fireStationService.deletefireStationService(new FireStation(null,"1"));
		assertTrue(res);
		assertTrue(this.data.getFirestations().size() == 1);
		assertThat(this.data.getFirestations().get(0).getStation()).isEqualTo("2");
		assertThat(this.data.getFirestations().get(0).getAddress()).isEqualTo("4 avenue des champs élysée");
	
	}
	
	@Test
	public void deleteFirestationTestWithAddress() {
		boolean res = fireStationService.deletefireStationService(new FireStation("8 rue grande",null));
		assertTrue(res);
		assertTrue(this.data.getFirestations().size() == 2);
		assertThat(this.data.getFirestations().get(0).getStation()).isEqualTo("1");
		assertThat(this.data.getFirestations().get(0).getAddress()).isEqualTo("4 la ruelle");		
		assertThat(this.data.getFirestations().get(1).getStation()).isEqualTo("2");
		assertThat(this.data.getFirestations().get(1).getAddress()).isEqualTo("4 avenue des champs élysée");
	
	}
	
	@Test
	public void deleteFirestationTestWithAddressAndStationNumber() {
		boolean res = fireStationService.deletefireStationService(new FireStation("8 rue grande","1"));
		assertTrue(res);
		assertTrue(this.data.getFirestations().size() == 1);
		assertThat(this.data.getFirestations().get(0).getStation()).isEqualTo("2");
		assertThat(this.data.getFirestations().get(0).getAddress()).isEqualTo("4 avenue des champs élysée");
	
	}
	
	@Test
	public void floodTestnullFireStation() {
		List<FloodDTO> res = fireStationService.floodService(null);
		assertTrue(res != null);
		assertThat(res.size()).isEqualTo(0);

	}
	@Test
	public void floodTestUnknowFireStation() {
		List<String> stationNumber = new ArrayList<>();
		stationNumber.add("4");
		List<FloodDTO> res = fireStationService.floodService(stationNumber);
		assertTrue(res != null);
		assertThat(res.get(0).getStation()).isEqualTo("4");
		assertThat(res.get(0).getFloodHousingList().size()).isEqualTo(0);
	}
	
	@Test
	public void floodTestTwoAdress() {
		List<String> stationNumber = new ArrayList<>();
		stationNumber.add("1");
		List<FloodDTO> res = fireStationService.floodService(stationNumber);
		assertTrue(res != null);
		assertThat(res.size()).isEqualTo(1);
		assertThat(res.get(0).getStation()).isEqualTo("1");
		assertThat(res.get(0).getFloodHousingList().size()).isEqualTo(2);
		assertThat(res.get(0).getFloodHousingList().get(0).getAddress()).isEqualTo("4 la ruelle");
		assertThat(res.get(0).getFloodHousingList().get(0).getCity()).isEqualTo("Noisy-Rudignon");
		assertThat(res.get(0).getFloodHousingList().get(0).getZip()).isEqualTo("77940");
		assertThat(res.get(0).getFloodHousingList().get(0).getPersonsList().size()).isEqualTo(3);
		assertThat(res.get(0).getFloodHousingList().get(0).getPersonsList().get(1).getFirstName()).isEqualTo("George");
		assertThat(res.get(0).getFloodHousingList().get(0).getPersonsList().get(1).getLastName()).isEqualTo("Aubert");
		assertThat(res.get(0).getFloodHousingList().get(0).getPersonsList().get(1).getPhone()).isEqualTo("0695123165");
		assertThat(res.get(0).getFloodHousingList().get(0).getPersonsList().get(1).getAge()).isEqualTo(6);
		assertThat(res.get(0).getFloodHousingList().get(0).getPersonsList().get(1).getMedications().size()).isEqualTo(0);
		assertThat(res.get(0).getFloodHousingList().get(0).getPersonsList().get(1).getAllergies().size()).isEqualTo(0);

	}

	
	@Test
	public void fireTestNullAdress() {
		List<FireDTO> res = fireStationService.fireService(null);
		assertTrue(res != null);
		assertThat(res.size()).isEqualTo(0);
	}
	
	@Test
	public void fireTestUnknowAdress() {
		List<FireDTO> res = fireStationService.fireService("8 La ruelle");
		assertTrue(res != null);
		assertThat(res.size()).isEqualTo(0);
	}
	@Test
	public void fireTestThreePerson() {
		List<FireDTO> res = fireStationService.fireService("4 la ruelle");
		assertTrue(res != null);
		assertThat(res.size()).isEqualTo(3);
		assertThat(res.get(2).getStationNumber()).isEqualTo("1");
		assertThat(res.get(2).getFirstName()).isEqualTo("Michel");
		assertThat(res.get(2).getLastName()).isEqualTo("Aubert");
		assertThat(res.get(2).getAge()).isEqualTo(12);
		assertThat(res.get(2).getPhoneNumber()).isEqualTo("0653215894");
		assertThat(res.get(2).getMedications().size()).isEqualTo(0);
		assertThat(res.get(2).getAllergies().size()).isEqualTo(0);
	}
	
	@Test
	public void fireTestOnePerson() {
		List<FireDTO> res = fireStationService.fireService("4 avenue des champs élysée");
		assertTrue(res != null);
		assertThat(res.size()).isEqualTo(1);
		assertThat(res.get(0).getStationNumber()).isEqualTo("2");
		assertThat(res.get(0).getFirstName()).isEqualTo("Amandine");
		assertThat(res.get(0).getLastName()).isEqualTo("Louvre");
		assertThat(res.get(0).getAge()).isEqualTo(53);
		assertThat(res.get(0).getPhoneNumber()).isEqualTo("0698465132");
		assertThat(res.get(0).getMedications().size()).isEqualTo(0);
		assertThat(res.get(0).getAllergies().size()).isEqualTo(0);
	}
	
	@Test
	public void firestationTestWithFireStationNull() {
		FirestationDTO res = fireStationService.firestationService(null);
		assertTrue(res != null);
		assertThat(res.getPersons().size()).isEqualTo(0);
		assertThat(res.getTotalAdultsNumber()).isEqualTo(0);
		assertThat(res.getTotalChildrenNumber()).isEqualTo(0);
	}
	
	@Test
	public void firestationTestWithUnknowFireStation() {
		FirestationDTO res = fireStationService.firestationService("5");
		assertTrue(res != null);
		assertThat(res.getPersons().size()).isEqualTo(0);
		assertThat(res.getTotalAdultsNumber()).isEqualTo(0);
		assertThat(res.getTotalChildrenNumber()).isEqualTo(0);
	}
	
	@Test
	public void firestationTest() {
		FirestationDTO res = fireStationService.firestationService("1");
		assertTrue(res != null);
		assertThat(res.getPersons().size()).isEqualTo(4);
		for(int i = 0; i<4;i++) {
			assertThat(res.getPersons().get(i).getFirstName()).isEqualTo(data.getPersons().get(i).getFirstName());
			assertThat(res.getPersons().get(i).getLastName()).isEqualTo(data.getPersons().get(i).getLastName());
			assertThat(res.getPersons().get(i).getAddress()).isEqualTo(data.getPersons().get(i).getAddress());
			assertThat(res.getPersons().get(i).getPhoneNumber()).isEqualTo(data.getPersons().get(i).getPhone());

		}
		assertThat(res.getTotalAdultsNumber()).isEqualTo(2);
		assertThat(res.getTotalChildrenNumber()).isEqualTo(2);
	}
	
	
	
	@Test
	public void PhoneAlertTestWithaFireStationNull() {
		List<String> res = fireStationService.phoneAlertService(null);
		assertTrue(res.isEmpty());
		assertTrue(res.size() == 0);
	}
	
	@Test
	public void PhoneAlertTestWithaUknowFireStation() {
		List<String> res = fireStationService.phoneAlertService("5");
		assertTrue(res.isEmpty());
		assertTrue(res.size() == 0);
	}
	
	@Test
	public void PhoneAlertTestWithOnePhone() {
		List<String> res = fireStationService.phoneAlertService("2");
		assertFalse(res.isEmpty());
		assertTrue(res.size() == 1);
		assertThat(res.get(0)).isEqualTo("0698465132");
	}
	
	@Test
	public void PhoneAlertTestWithFourPhone() {
		List<String> res = fireStationService.phoneAlertService("1");
		assertFalse(res.isEmpty());
		assertTrue(res.size() == 4);
		assertThat(res.get(0)).isEqualTo("0669120050");
		assertThat(res.get(1)).isEqualTo("0695123165");
		assertThat(res.get(2)).isEqualTo("0653215894");
		assertThat(res.get(3)).isEqualTo("0656513518");
	}
}
