package com.safetynet.alerts.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;


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
public class MedicalRecordControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext WebContext;
    
    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }
    
	@Test
	public void createMedicalRecordTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
				.contentType(APPLICATION_JSON)
                .content("{\"firstName\":\"Guillaume\",\"lastName\":\"Aubert\",\"birthdate\":\"01/13/2000\",\"medications\":[],\"allergies\":[]}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
	}
	
	@Test
	public void createMedicalRecordTestWithError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
				.contentType(APPLICATION_JSON)
                .content("{\"firstName\":\"Guillaume\",\"birthdate\":\"01/13/2001\",\"medications\":[],\"allergies\":[]}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
	}
	
	@Test
	public void putMedicalRecordTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
				.contentType(APPLICATION_JSON)
                .content("{\"firstName\":\"Guillaume\",\"lastName\":\"Aubert\",\"birthdate\":\"01/13/2001\",\"medications\":[],\"allergies\":[]}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
	}
	
	@Test
	public void putMedicalRecordTestWithError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
				.contentType(APPLICATION_JSON)
                .content("{\"firstName\":\"Lientenant\",\"lastName\":\"Columbo\",\"birthdate\":\"01/13/2000\",\"medications\":[],\"allergies\":[]}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteMedicalRecordTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
				.contentType(APPLICATION_JSON)
                .param("firstName", "John")
                .param("lastName", "Boyd")
				.accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
	}
	
	@Test
	public void deleteMedicalRecordTestWithError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
				.contentType(APPLICATION_JSON)
                .param("firstName", "Lientenant")
                .param("lastName", "Columbo")
				.accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
	}

}