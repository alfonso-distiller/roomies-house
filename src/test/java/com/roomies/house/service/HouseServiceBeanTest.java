package com.roomies.house.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.roomies.house.model.Address;
import com.roomies.house.model.House;
import com.roomies.house.repo.HouseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HouseServiceBeanTest {
	
	@Mock
	HouseRepository repo;
	
	HouseService service;
	
	@Before
	public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new HouseServiceBean(repo);	
	}
	
	@Test
	public void givenAnEmptyDatabase_expectEmptyCollection() throws Exception {
		when(repo.findAll()).thenReturn(createSomeHouses(0));
		
		Collection<House> universe = service.findAll();

		assertThat(universe, IsCollectionWithSize.hasSize(0));
	}
	
	@Test
	public void givenANonEmptyDatabase_expectOkCollection() throws Exception {
		when(repo.findAll()).thenReturn(createSomeHouses(5));
		
		Collection<House> universe = service.findAll();

		assertThat(universe, IsCollectionWithSize.hasSize(5));
		
		log.info(universe.toString());
	}
	
	@Test
	public void givenAnUnexistingId_expectEmptyOptional() throws Exception {
		final Long id = 999l;
		
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		Optional<House> result = service.findById(id);
		
		assertThat(result.isPresent(), equalTo(false));
	}
	
	@Test
	public void givenAnUnexistingId_expectOkOptional() throws Exception {
		final Long id = 999l;
		final House h = createHouse(0);
		
		when(repo.findById(id)).thenReturn(Optional.of(h));
		
		Optional<House> result = service.findById(id);
		
		assertThat(result.isPresent(), equalTo(true));
		assertThat(result.get(), equalTo(h));
	}
	
	private Collection<House> createSomeHouses(int size) {
		Collection<House> universe = new ArrayList<>();
		
		for(int i = 0 ; i < size ; i++) {
			universe.add(createHouse(i));
		}
		
		return universe;
	}
	
	private House createHouse(int index) {
		House basic = new House();
		
		basic.setName("basic house " + index);
		
		Address a = new Address();
		a.setCity("guadalajara");
		a.setCountry("MX");
		a.setHouse(basic);
		a.setLine1("line 1");
		a.setLine2("line 2");
		a.setState("jalisco");
		a.setZip("12345");
		
		basic.setAddress(a);
		
		return basic;
	}
}
