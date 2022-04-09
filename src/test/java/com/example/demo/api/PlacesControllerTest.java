package com.example.demo.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.api.dto.PlaceDto;
import com.example.demo.api.service.PlacesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(PlacesController.class)
class PlacesControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(PlacesControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private PlacesService placesService;
	
	@Captor
	ArgumentCaptor<PlaceDto> argumentCaptor;

	@Test
	@Disabled
	void testSavePlacesWithPostalCode() throws JsonProcessingException, Exception {
		
		PlaceDto placeDto = preparePlaceDetail("Chennai", 600001);
		
		when(placesService.savePlaceWithPostalCode(argumentCaptor.capture())).thenReturn(1L);
		
		this.mockMvc
		.perform(post("/api/places")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(placeDto)))
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(header().string("Location","http://localhost/api/places/1"));
	}
	
	@Test
	void testSavePlacesListWithPostalCodes() throws JsonProcessingException, Exception {
		
		List<PlaceDto> placeDtos = new ArrayList<>();
		
		placeDtos.add(preparePlaceDetail("Chennai", 600001)); 
		placeDtos.add(preparePlaceDetail("Madurai", 625001)); 
		placeDtos.add(preparePlaceDetail("Kovai", 641001)); 
		placeDtos.add(preparePlaceDetail("Trichy", 620001));
		
		when(placesService.savePlacesListWithPostalCodes(argumentCaptor.getAllValues())).thenReturn(placeDtos);
		
		logger.info("Post JSON values : {}", mapper.writeValueAsString(placeDtos));
		
		this.mockMvc
		.perform(post("/api/places")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(placeDtos)))
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(header().string("Location","http://localhost/api/places/"));
		
	}

	private PlaceDto preparePlaceDetail(String placeName, long postalCode) {
		return PlaceDto.builder().placeName(placeName).postalCode(postalCode).build();
	}

}
