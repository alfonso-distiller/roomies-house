package com.roomies.house.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.io.IOException;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomies.house.Application;
import com.roomies.house.model.Address;
import com.roomies.house.model.House;
import com.roomies.house.repo.HouseRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = "TEST")
@Slf4j
public class HouseControllerTest {
	
	@Autowired
	HouseRepository repo;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void givenNoHouses_expectEmptyArray() throws Exception {
		repo.deleteAll();
		
		String uri = "/houses";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		
		assertThat(status, equalTo(200));
		
		String content = mvcResult.getResponse().getContentAsString();
		House[] houses = this.mapFromJson(content, House[].class);
		
		assertThat(Arrays.asList(houses), hasSize(equalTo(0)));
	}
	
	@Test
	public void givenHouses_expectArray() throws Exception {
		final House h = repo.findById(1l).get();
		
		String uri = "/houses";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		
		assertThat(status, equalTo(200));
		
		String content = mvcResult.getResponse().getContentAsString();
		log.info(content);
		
		House[] houses = this.mapFromJson(content, House[].class);
		
		assertThat(Arrays.asList(houses), hasSize(equalTo(1)));
		
		House returned = (House) Arrays.asList(houses).stream().findFirst().get();
		
		assertThat(returned, equalTo(h));
	}
	
	@Test
	public void givenAnId_expectHouse() throws Exception {
		final House h = createHouse("Casa blanca");
		
		repo.save(h);
		
		String uri = "/houses/1";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		
		assertThat(status, equalTo(200));
		
		String content = mvcResult.getResponse().getContentAsString();
		log.info(content);
		
		House house = this.mapFromJson(content, House.class);
		
		House savedOne = repo.findById(1l).get();
		
		assertThat(house, equalTo(savedOne));
		
	}
	
	@Test
	public void givenAnNonExistingId_expect404() throws Exception {
		String uri = "/houses/999";
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		
		assertThat(status, equalTo(404));
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
	
	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	private <T> T mapFromJson(String json, Class<T> clazz) 
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
