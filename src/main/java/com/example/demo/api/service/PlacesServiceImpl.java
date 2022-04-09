package com.example.demo.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.dto.PlaceDto;
import com.example.demo.api.entity.PlaceEntity;
import com.example.demo.api.repo.PlacesRepository;

@Service
public class PlacesServiceImpl implements PlacesService {

	@Autowired
	PlacesRepository placesRepository;

	public long savePlaceWithPostalCode(PlaceDto placeDto) {
		PlaceEntity entity = PlaceEntity.builder().placeName(placeDto.getPlaceName())
				.postalCode(placeDto.getPostalCode()).build();
		entity = placesRepository.save(entity);
		return entity.getId();
	}

}
