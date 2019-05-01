package com.roomies.house.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.roomies.house.model.House;

@Repository
public interface HouseRepository extends CrudRepository<House, Long> {

}
