package com.example.demo.api.service;

import java.util.List;

import com.example.demo.api.dto.PlaceDto;

public interface PlacesService {

	long savePlaceWithPostalCode(PlaceDto placeDto);

	List<PlaceDto> savePlacesListWithPostalCodes(List<PlaceDto> placeDtos);

}
