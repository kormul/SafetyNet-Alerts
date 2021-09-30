package com.safetynet.alerts.service;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

//@SpringBootTest(classes = {DataLoader.class})
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
	} 
}
