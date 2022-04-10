package com.example.demo.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlaceDto {

	private long id;
	private String placeName;
	private long postalCode;
}
