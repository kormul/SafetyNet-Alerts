package com.safetynet.alerts.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
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
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext WebContext;
    
    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
        
    }
	
	@Test
	public void createPersonneTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Guillaume\",\"lastName\": \"Aubert\",\"address\": \"4 la ruelle\",\"city\": \"Noisy-Rudignon\",\"zip\": \"77940\",\"phone\": \"0669120050\",\"email\": \"aubert2@gmx.fr\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
	}
	
	 @Test
	    void putPersonTestWithnull() throws Exception {
	        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person")
	                .contentType(APPLICATION_JSON)
	                .content("")
	                .accept(APPLICATION_JSON))
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(status().is(400));
	    }
	 
    @Test
    void putPersonTestWithNoModification() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Eric\",\"lastName\": \"Cadigan\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void putPersonTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Eric\",\"lastName\": \"Eric\",\"address\": \"4 la ruelle\",\"city\": \"Noisy-Rudignon\",\"zip\": \"77940\",\"phone\": \"0669120050\",\"email\": \"aubert2@gmx.fr\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
    
	
    @Test
    void deletePersonTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Eric\",\"lastName\": \"Cadigan\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void deletePersonTestWithPersonNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Guillaume\",\"lastName\": \"Aubert\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }
	
    @Test
    void personInfoTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .contentType(APPLICATION_JSON).param("firstName", "Eric")
                .param("lastName", "Cadigan")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(content().string(
                        "[{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"address\":\"951 LoneTree Rd\",\"city\":\"Culver\",\"zip\":\"97451\",\"age\":76,\"email\":\"gramps@email.com\",\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]"));
    }

    @Test
    void personInfoTestWithUnknowName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .contentType(APPLICATION_JSON).param("firstName", "Guillaume")
                .param("lastName", "Aubert")).andExpect(status().isNotFound());
    }
	
	 @Test
	    void childAlertTestWithoutChilds() throws Exception {
	        this.mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
	                .contentType(APPLICATION_JSON).param("address", "4 la ruelle"))
	                .andExpect(status().isNotFound());
	    }
	 
    @Test
    void childAlertTestWithChilds() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .contentType(APPLICATION_JSON)
                .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                "[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"age\":37},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"age\":32},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"age\":9},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"age\":4},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"age\":35}]"));
    }

	@Test
	public void communityEmailTestUknownCity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .contentType(APPLICATION_JSON).param("city", "Paris"))
                .andExpect(status().isNotFound());
    }
	
	@Test
	public void communityEmailTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .contentType(APPLICATION_JSON).param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                "[\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"tcoop@ymail.com\",\"lily@email.com\",\"soph@email.com\",\"ward@email.com\",\"zarc@email.com\",\"reg@email.com\",\"jpeter@email.com\",\"jpeter@email.com\",\"aly@imail.com\",\"bstel@email.com\",\"ssanw@email.com\",\"bstel@email.com\",\"clivfd@ymail.com\",\"gramps@email.com\"]"))
                .andExpect(jsonPath("$.length()", is(23)));
    }
	
	
}
