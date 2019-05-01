package com.roomies.house.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.roomies.house.model.Address;
import com.roomies.house.model.House;
import com.roomies.house.repo.HouseRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Profile(value = {"DEV"})
public class HouseDataLoader implements CommandLineRunner {

	private final HouseRepository houseRepository;
	
	@Override
	public void run(String... args) throws Exception {
		houseRepository.save(createHouse("Casa Blanca"));
	}
	
	private House createHouse(String name) {
		House newHouse = new House();
		newHouse.setName(name);
		
		Address a = new Address();
		
		a.setCity("Guadalajara");
		a.setCountry("Mexico");
		a.setLine1("Av. La Paz 2803");
		a.setLine2("Col. Arcos La Paz");
		a.setState("Jalisco");
		a.setZip("44000");
		a.setHouse(newHouse);
		
		newHouse.setAddress(a);
		
		return newHouse;
	}

}
