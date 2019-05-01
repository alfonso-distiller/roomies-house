package com.roomies.house.repo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.hamcrest.beans.HasPropertyWithValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.roomies.house.model.Address;
import com.roomies.house.model.House;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Slf4j
public class HouseRepositoryTest {
	
	@Autowired
	HouseRepository repo;
	
	@Test
	public void givenHouseWithAName_expectOk() throws Exception {
		House basic = new House();
		
		basic.setName("basic house");
		
		House savedValue = repo.save(basic);
		
		assertThat(savedValue, not(equalTo(nullValue())));
		assertThat(savedValue, HasPropertyWithValue.hasProperty("id", equalTo(1l)));
		assertThat(savedValue, HasPropertyWithValue.hasProperty("name", equalTo("basic house")));
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void givenHouseWithoutAName_expectException() throws Exception {
		repo.save(new House());
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void givenHouseWithASuperLargeName_expectException() throws Exception {
		House basic = new House();
		
		basic.setName("public void givenHouseWithASuperLargeName_expectException() throws Exception");
		
		repo.save(basic);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void givenHouseWithAnEmptyName_expectException() throws Exception {
		House basic = new House();
		
		basic.setName("");
		
		repo.save(basic);
	}
	
	@Test
	public void givenHouseWithAdress_savedOk() throws Exception {
		House basic = new House();
		
		basic.setName("basic house");
		
		Address a = new Address();
		a.setCity("guadalajara");
		a.setCountry("MX");
		a.setHouse(basic);
		a.setLine1("line 1");
		a.setLine2("line 2");
		a.setState("jalisco");
		a.setZip("12345");
		
		basic.setAddress(a);
		
		House savedValue = repo.save(basic);
		
		assertThat(savedValue, not(equalTo(nullValue())));
		assertThat(savedValue, HasPropertyWithValue.hasProperty("id", not(equalTo(nullValue()))));
		assertThat(savedValue, HasPropertyWithValue.hasProperty("name", equalTo("basic house")));
		
		Address savedAddress = basic.getAddress();
		
		assertThat(savedAddress, not(equalTo(nullValue())));
		assertThat(savedAddress, HasPropertyWithValue.hasProperty("line1", 		equalTo("line 1")));
		assertThat(savedAddress, HasPropertyWithValue.hasProperty("line2", 		equalTo("line 2")));
		assertThat(savedAddress, HasPropertyWithValue.hasProperty("zip", 		equalTo("12345")));
		assertThat(savedAddress, HasPropertyWithValue.hasProperty("state", 		equalTo("jalisco")));
		assertThat(savedAddress, HasPropertyWithValue.hasProperty("city", 		equalTo("guadalajara")));
		assertThat(savedAddress, HasPropertyWithValue.hasProperty("country", 	equalTo("MX")));
		
		log.info(savedValue.toString());
	}
}
