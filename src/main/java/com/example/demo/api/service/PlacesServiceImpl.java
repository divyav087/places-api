package com.example.demo.api.service;

import java.util.ArrayList;
import java.util.List;

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
		PlaceEntity entity = preparePlaceEntity(placeDto);
		entity = placesRepository.save(entity);
		return entity.getId();
	}

	

	@Override
	public List<PlaceDto> savePlacesListWithPostalCodes(List<PlaceDto> placeDtos) {
		List<PlaceEntity> entities = new ArrayList<>();
		for(PlaceDto placeDto: placeDtos) {
			PlaceEntity entity = preparePlaceEntity(placeDto);
			entities.add(entity);
		}
		entities = (List<PlaceEntity>) placesRepository.saveAll(entities);
		List<PlaceDto> placeLists=new ArrayList<>();
		for(PlaceEntity entity: entities) {
			placeLists.add(mapEntityToDto(entity));
		}
		return placeLists;
	}
	
	private PlaceEntity preparePlaceEntity(PlaceDto placeDto) {
		return PlaceEntity.builder().placeName(placeDto.getPlaceName())
				.postalCode(placeDto.getPostalCode()).build();
	}
	
	private PlaceDto mapEntityToDto(PlaceEntity entity) {
		return PlaceDto.builder()
				.placeName(entity.getPlaceName())
				.postalCode(entity.getPostalCode())
				.id(entity.getId())
				.build();
	}

}
