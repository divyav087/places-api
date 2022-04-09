package com.example.demo.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="places")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String placeName;
	private long postalCode;
}
