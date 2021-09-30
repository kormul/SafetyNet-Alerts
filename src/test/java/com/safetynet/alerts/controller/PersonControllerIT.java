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
public class PersonControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext WebContext;
    
    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }
	
	@Test
	public void createPersonneOK() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Guillaume\",\"lastName\": \"Aubert\",\"address\": \"4 la ruelle\",\"city\": \"Noisy-Rudignon\",\"zip\": \"77940\",\"phone\": \"0669120050\",\"email\": \"aubert2@gmx.fr\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
	}
	
	
}
