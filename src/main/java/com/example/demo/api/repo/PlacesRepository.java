package com.example.demo.api.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.api.entity.PlaceEntity;

public interface PlacesRepository extends CrudRepository<PlaceEntity, Long> {

	
	
	List<PlaceEntity> findByPostalCodeBetween(long from, long to);
}
