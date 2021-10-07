package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext WebContext;
    
    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }
    
	@Test
	public void postFireStationRecordTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
				.contentType(APPLICATION_JSON)
                .content("{\"address\":\"4 la ruelle\",\"station\":\"4\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
	}
	
	@Test
	public void postFireStationRecordTestWithError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
				.contentType(APPLICATION_JSON)
                .content("{\"address\":\"4 la ruelle\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void putFireStationRecordTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
				.contentType(APPLICATION_JSON)
                .content("{\"address\":\"4 la ruelle\",\"station\":\"5\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
	}
	
	@Test
	public void putFireStationRecordTestWithError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
				.contentType(APPLICATION_JSON)
                .content("{\"address\":\"4 ruelle\",\"station\":\"5\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
	}
	
	/*@Test
	public void deleteFireStationRecordTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
				.contentType(APPLICATION_JSON)
                .content("{\"address\":\"4 la ruelle\",\"station\":\"5\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
	}
	
	@Test
	public void deleteFireStationRecordTestWithError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
				.contentType(APPLICATION_JSON)
                .content("{\"address\":\"4 ruelle\",\"station\":\"5\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
	}*/
	
	/*	@GetMapping("/flood/stations")
	public List<FloodDTO> floodController(@RequestParam(value = "stations") List<String> stations, HttpServletResponse response) {

		List<FloodDTO> floodDTO = fireStationService.floodService(stations);
		
		if(floodDTO.isEmpty()) {
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		return floodDTO;
		
	}*/
	
	@Test
	public void floodTestUknownCity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .contentType(APPLICATION_JSON).param("stations", "8,9"))
                .andExpect(status().isOk());
    }
	
	@Test
	public void floodTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .contentType(APPLICATION_JSON).param("stations", "1,2"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"station\":\"1\",\"floodHousingList\":[{\"address\":\"644 Gershwin Cir\",\"city\":\"Culver\",\"zip\":\"97451\",\"personsList\":[{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"phone\":\"841-874-6512\",\"age\":21,\"medications\":[],\"allergies\":[\"shellfish\"]}]},{\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":\"97451\",\"personsList\":[{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"phone\":\"841-874-8547\",\"age\":42,\"medications\":[\"thradox:700mg\"],\"allergies\":[\"illisoxian\"]},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"phone\":\"841-874-7462\",\"age\":39,\"medications\":[],\"allergies\":[]}]},{\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"personsList\":[{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":45,\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":41,\"medications\":[],\"allergies\":[]},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"phone\":\"841-874-7784\",\"age\":7,\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"],\"allergies\":[]}]}]},{\"station\":\"2\",\"floodHousingList\":[{\"address\":\"29 15th St\",\"city\":\"Culver\",\"zip\":\"97451\",\"personsList\":[{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"phone\":\"841-874-6513\",\"age\":32,\"medications\":[],\"allergies\":[]}]},{\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"personsList\":[{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7878\",\"age\":33,\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7512\",\"age\":36,\"medications\":[],\"allergies\":[]},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"phone\":\"841-874-7512\",\"age\":4,\"medications\":[],\"allergies\":[]}]},{\"address\":\"951 LoneTree Rd\",\"city\":\"Culver\",\"zip\":\"97451\",\"personsList\":[{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"phone\":\"841-874-7458\",\"age\":76,\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]}]}]"))
                .andExpect(jsonPath("$.length()", is(2)));
    }
	
	
	@Test
	public void firetTestUknownCity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .contentType(APPLICATION_JSON).param("address", "4 la ruelle"))
                .andExpect(status().isNotFound());
    }
	
	@Test
	public void fireTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .contentType(APPLICATION_JSON).param("address", "892 Downing Ct"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"stationNumber\":\"2\",\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"age\":33,\"phoneNumber\":\"841-874-7878\",\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"stationNumber\":\"2\",\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"age\":36,\"phoneNumber\":\"841-874-7512\",\"medications\":[],\"allergies\":[]},{\"stationNumber\":\"2\",\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"age\":4,\"phoneNumber\":\"841-874-7512\",\"medications\":[],\"allergies\":[]}]"))
                .andExpect(jsonPath("$.length()", is(3)));
    }
	
	@Test
	public void firestationTestUknownCity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .contentType(APPLICATION_JSON).param("stationNumber", "8"))
                .andExpect(status().isNotFound());
    }
	
	@Test
	public void firestationEmailTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .contentType(APPLICATION_JSON).param("stationNumber", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"totalAdultsNumber\":4,\"totalChildrenNumber\":1,\"persons\":[{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"address\":\"29 15th St\",\"phoneNumber\":\"841-874-6513\"},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"phoneNumber\":\"841-874-7878\"},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"phoneNumber\":\"841-874-7512\"},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"phoneNumber\":\"841-874-7512\"},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"address\":\"951 LoneTree Rd\",\"phoneNumber\":\"841-874-7458\"}]}"))
                .andExpect(jsonPath("$.length()", is(3)));
    }
	
	@Test
	public void phoneAlertTestUknownCity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .contentType(APPLICATION_JSON).param("firestation", "8"))
                .andExpect(status().isNotFound());
    }
	
	@Test
	public void phoneAlertEmailTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .contentType(APPLICATION_JSON).param("firestation", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"841-874-6513\",\"841-874-7878\",\"841-874-7512\",\"841-874-7512\",\"841-874-7458\"]"))
                .andExpect(jsonPath("$.length()", is(5)));
    }

}
