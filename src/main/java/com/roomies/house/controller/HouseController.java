package com.roomies.house.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomies.house.model.House;
import com.roomies.house.service.HouseService;
import com.roomies.house.util.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("houses")
@AllArgsConstructor
public class HouseController {
	
	private final HouseService service;
	
	@GetMapping(produces = {"application/json"})
	public Collection<House> findAll() {
		return service.findAll();
	}
	
	@GetMapping(path = "/{id}", produces = {"application/json"})
	public House findbyId(@PathVariable(name = "id") Long id) {
		Optional<House> response = service.findById(id);
		
		if(response.isPresent()) {
			return response.get();
		} else {
			throw new ResourceNotFoundException();
		}
	}
}
