package com.example;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationRepository {
	
	public List<Reservation> find();

}
