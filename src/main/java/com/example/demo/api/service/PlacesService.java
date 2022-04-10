package com.example.demo.api.service;

import java.util.List;

import com.example.demo.api.dto.PlaceDto;

public interface PlacesService {

	long savePlaceWithPostalCode(PlaceDto placeDto);

	boolean savePlacesListWithPostalCodes(List<PlaceDto> placeDtos);

	List<PlaceDto> getPlacesNameByPostalRange(long rangeFrom, long rangeTo);

}
