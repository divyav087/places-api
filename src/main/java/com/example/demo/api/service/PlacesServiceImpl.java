package com.example.demo.api.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.dto.PlaceDto;
import com.example.demo.api.entity.PlaceEntity;
import com.example.demo.api.repo.PlacesRepository;

@Service
public class PlacesServiceImpl implements PlacesService {

	@Autowired
	PlacesRepository placesRepository;

	@Override
	public long savePlaceWithPostalCode(PlaceDto placeDto) {
		PlaceEntity entity = preparePlaceEntity(placeDto);
		entity = placesRepository.save(entity);
		return entity.getId();
	}

	@Override
	public boolean savePlacesListWithPostalCodes(List<PlaceDto> placeDtos) {
		List<PlaceEntity> entities = placeDtos.stream().map(this::preparePlaceEntity).collect(Collectors.toList());
		entities = (List<PlaceEntity>) placesRepository.saveAll(entities);
		return entities.size() > 0;
	}

	@Override
	public List<PlaceDto> getPlacesNameByPostalRange(long rangeFrom, long rangeTo) {
		List<PlaceEntity> entities = placesRepository.findByPostalCodeBetween(rangeFrom, rangeTo);
		Comparator<PlaceDto> placeComparator = (e1, e2) -> e1.getPlaceName().compareTo(e2.getPlaceName());
		List<PlaceDto> placeDtos = entities.stream()
				.map(this::mapEntityToDto)
				.sorted(placeComparator)
				.collect(Collectors.toList());
		return placeDtos;
	}

	private PlaceEntity preparePlaceEntity(PlaceDto placeDto) {
		return PlaceEntity.builder().placeName(placeDto.getPlaceName()).postalCode(placeDto.getPostalCode()).build();
	}

	private PlaceDto mapEntityToDto(PlaceEntity entity) {
		return PlaceDto.builder().placeName(entity.getPlaceName()).postalCode(entity.getPostalCode()).id(entity.getId())
				.build();
	}
}
