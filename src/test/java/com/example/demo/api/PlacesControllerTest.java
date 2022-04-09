package com.example.demo.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.api.dto.PlaceDto;
import com.example.demo.api.service.PlacesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PlacesController.class)
class PlacesControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private PlacesService placesService;
	
	@Captor
	ArgumentCaptor<PlaceDto> argumentCaptor;

	@Test
	void testSavePlacesWithPostalCode() throws JsonProcessingException, Exception {
		
		PlaceDto placeDto = PlaceDto.builder().placeName("Chennai").postalCode(600001).build();
		
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
	void testSavePlacesListWithPostalCodes() {
		
		List<PlaceDto> placeDtos = new ArrayList<>();
	}

}
