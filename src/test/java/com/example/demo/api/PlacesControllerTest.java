package com.example.demo.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	@Disabled
	void testSavePlacesWithPostalCode() throws JsonProcessingException, Exception {
		
		PlaceDto placeDto = preparePlaceDetail(0,"Chennai", 600001);
		
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
		
		placeDtos.add(preparePlaceDetail(0,"Chennai", 600001)); 
		placeDtos.add(preparePlaceDetail(0,"Madurai", 625001)); 
		placeDtos.add(preparePlaceDetail(0,"Kovai", 641001)); 
		placeDtos.add(preparePlaceDetail(0,"Trichy", 620001));
		
		when(placesService.savePlacesListWithPostalCodes(argumentCaptor.getAllValues())).thenReturn(true);
		
		log.info("Post JSON values : {}", mapper.writeValueAsString(placeDtos));
		
		this.mockMvc
		.perform(post("/api/places")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(placeDtos)))
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(header().string("Location","http://localhost/api/places/"));
		
	}
	
	@Test
	void testGetPlacesByPostCodeSortByName() throws Exception {
		
		long rangeFrom=600001;
		long rangeTo=641100;
		List<PlaceDto> placeDtos = new ArrayList<>();
		placeDtos.add(preparePlaceDetail(1,"Chennai", 600001)); 
		placeDtos.add(preparePlaceDetail(2,"Madurai", 625001)); 
		placeDtos.add(preparePlaceDetail(3,"Kovai", 641001)); 
		placeDtos.add(preparePlaceDetail(4,"Trichy", 620001));
		when(placesService.getPlacesNameByPostalRange(rangeFrom, rangeTo)).thenReturn(placeDtos);
		
		this.mockMvc.perform(get("/api/places")
				.queryParam("rangeFrom", String.valueOf(rangeFrom))
				.queryParam("rangeTo",String.valueOf(rangeTo)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].placeName", is("Chennai")))
		.andExpect(jsonPath("$[0].postalCode", is(600001)))
		.andExpect(jsonPath("$[0].id", is(1)));
	}

	private PlaceDto preparePlaceDetail(long id, String placeName, long postalCode) {
		return PlaceDto.builder()
				.id(id)
				.placeName(placeName)
				.postalCode(postalCode)
				.build();
	}

}
