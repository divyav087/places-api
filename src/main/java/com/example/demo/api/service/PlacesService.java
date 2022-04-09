package com.example.demo.api.service;

import com.example.demo.api.dto.PlaceDto;

public interface PlacesService {

	long savePlaceWithPostalCode(PlaceDto placeDto);

}
