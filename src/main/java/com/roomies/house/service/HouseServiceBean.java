package com.roomies.house.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.roomies.house.model.House;
import com.roomies.house.repo.HouseRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HouseServiceBean implements HouseService {

	private HouseRepository houseRepository;
	
	@Override
	public Collection<House> findAll() {
		return (Collection<House>) houseRepository.findAll();
	}

	@Override
	public Optional<House> findById(Long id) {
		return houseRepository.findById(id);
	}

	@Override
	public House save(House house) {
		return houseRepository.save(house);
	}
	
}
