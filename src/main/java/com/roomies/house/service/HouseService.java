package com.roomies.house.service;

import java.util.Collection;
import java.util.Optional;

import com.roomies.house.model.House;

public interface HouseService {
	Collection<House> findAll();
	Optional<House> findById(Long id);
	House save(House house);
}
