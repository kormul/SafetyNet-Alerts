package com.safetynet.alerts.service;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Data;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader {

	@Bean
	public static Data readJsonFile() {
		log.debug("start read JsonFile");
		Data data = null;
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			data = objectMapper.readValue(new File("src/main/resources/json/data.json"), Data.class);
		} catch (JsonParseException e) {
			log.debug(e.getMessage());
		} catch (JsonMappingException e) {
			log.debug(e.getMessage());
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
		log.debug("end read JsonFile");
		return data;
	}
	
}
