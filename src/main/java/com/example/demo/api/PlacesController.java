package com.example.demo.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.api.dto.PlaceDto;
import com.example.demo.api.service.PlacesService;

@RestController
@RequestMapping("/api/places")
public class PlacesController {

	@Autowired
	PlacesService placesService;

	@PostMapping
	public ResponseEntity<Void> savePlacesWithPostalCode(@Valid @RequestBody List<PlaceDto> placeDtos,
			UriComponentsBuilder uriComponentsBuilder) {
		boolean save = placesService.savePlacesListWithPostalCodes(placeDtos);
		UriComponents uriComponents = uriComponentsBuilder.path("/api/places/").buildAndExpand(save);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<List<PlaceDto>> getPlacesByPostalCode(@RequestParam("rangeFrom") long rangeFrom,
			@RequestParam("rangeTo") long rangeTo) {
		
		List<PlaceDto> placesDto = placesService.getPlacesNameByPostalRange(rangeFrom, rangeTo);
		return ResponseEntity.ok(placesDto);
	}
}
